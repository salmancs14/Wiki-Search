package com.repo.wiki.wikipediasearch.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.repo.wiki.wikipediasearch.R;
import com.repo.wiki.wikipediasearch.api.WikiSearchApi;
import com.repo.wiki.wikipediasearch.databinding.ActivityWikiSearchBinding;
import com.repo.wiki.wikipediasearch.entity.WikiSearch;
import com.repo.wiki.wikipediasearch.interfaces.WikiSearchAdapterListener;
import com.repo.wiki.wikipediasearch.repository.WikiSearchRepository;
import com.repo.wiki.wikipediasearch.utils.RetrofitClientInstance;
import com.repo.wiki.wikipediasearch.viewmodel.WikiSearchViewModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WikiSearchActivity extends AppCompatActivity implements WikiSearchAdapterListener {

    private WikiSearchViewModel wikiSearchViewModel;
    private WikiSearchRepository wikiSearchRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private WikiSearchAdapter wikiSearchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wikiSearchViewModel = ViewModelProviders.of(this).get(WikiSearchViewModel.class);
        ActivityWikiSearchBinding activityWikiSearchBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_wiki_search);
        activityWikiSearchBinding.setWikiSearchViewModel(wikiSearchViewModel);
        wikiSearchViewModel.getSearchQueryObservable().subscribe(new SearchTextObserver());
        wikiSearchRepository = new WikiSearchRepository(
                RetrofitClientInstance.getRetrofitInstance().create(WikiSearchApi.class));
        wikiSearchAdapter = new WikiSearchAdapter(this);
        activityWikiSearchBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        activityWikiSearchBinding.recyclerView.setAdapter(wikiSearchAdapter);
    }

    private void loadData(String query) {

        disposable.add(wikiSearchViewModel.loadData(wikiSearchRepository, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<WikiSearch>() {
                            @Override
                            public void onSuccess(WikiSearch wikiSearch) {
                                if(wikiSearch.query.pages.size() == 0) {
                                    wikiSearchViewModel.setNoResultState(true);
                                }
                                wikiSearchViewModel.setProgressState(false);
                                wikiSearchAdapter.setItems(wikiSearch.query.pages);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
    }

    @Override
    public void onWikiItemClick(String title) {
        try {
            String query = URLEncoder.encode(title, "utf-8");
            String url = "https://en.wikipedia.org/wiki/" + query;
            Bundle bundle = new Bundle();
            bundle.putString(WebActivity.BUNDLE_URL, url);
            WebActivity.start(this, bundle);
        } catch (UnsupportedEncodingException e) {
            Log.d(this.getLocalClassName(), e.getLocalizedMessage());
        }
    }

    private class SearchTextObserver implements Observer<String> {


        @Override
        public void onSubscribe(Disposable d) {
            disposable.add(d);
        }

        @Override
        public void onNext(String query) {
            loadData(query);
        }

        @Override
        public void onError(Throwable e) {
            Log.d("error", e.getLocalizedMessage());
        }

        @Override
        public void onComplete() {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
