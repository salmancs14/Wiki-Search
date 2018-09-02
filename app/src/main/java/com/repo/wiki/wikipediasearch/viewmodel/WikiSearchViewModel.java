package com.repo.wiki.wikipediasearch.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;

import com.repo.wiki.wikipediasearch.entity.WikiSearch;
import com.repo.wiki.wikipediasearch.repository.WikiSearchRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

public class WikiSearchViewModel extends ViewModel {

    private ObservableField<String> searchView = new ObservableField<>();
    public ObservableField<Boolean> progressState = new ObservableField<>(false);
    public ObservableField<Boolean> noResultState = new ObservableField<>(false);
    private PublishSubject<String> searchTextSubject = PublishSubject.create();

    public void setSearchView(String searchQuery) {
        searchView.set(searchQuery);
        searchTextSubject.onNext(searchQuery);
        Log.d("salman", searchQuery);
    }

    public String getSearchView() {
        return searchView.get();
    }

    public Observable<String> getSearchQueryObservable() {
        return searchTextSubject.debounce(300, TimeUnit.MILLISECONDS);
    }

    public void setProgressState(boolean state) {
        progressState.set(state);
    }

    public void setNoResultState(boolean state) {
        noResultState.set(state);
    }

    public Single<WikiSearch> loadData(WikiSearchRepository wikiSearchRepository, String query) {
        setProgressState(true);
        setNoResultState(false);
        return wikiSearchRepository.getWikiResult(10, 0, query);
    }
}
