package com.repo.wiki.wikipediasearch.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;

public class WikiSearchViewModel extends ViewModel {

    private ObservableField<String> searchView = new ObservableField<>();

    public void setSearchView(String search) {
        Log.d("salman", search);
    }

    public String getSearchView() {
        return searchView.get();
    }
}
