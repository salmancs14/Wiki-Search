package com.repo.wiki.wikipediasearch.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

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
    public ObservableField<String> noResultString = new ObservableField<>();
    private PublishSubject<String> searchTextSubject = PublishSubject.create();

    public void setSearchView(String searchQuery) {
        searchView.set(searchQuery);
        searchTextSubject.onNext(searchQuery);
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


    public void setNoResultState(boolean state, boolean isNetConnected) {
        noResultState.set(state);
        if(isNetConnected) {
            noResultString.set("No Result Found");
        } else {
            noResultString.set("Connect to internet");
        }
    }

    public Single<WikiSearch> loadData(WikiSearchRepository wikiSearchRepository, String query) {
        setProgressState(true);
        noResultState.set(false);
        return wikiSearchRepository.getWikiResult(10, 0, query);
    }
}
