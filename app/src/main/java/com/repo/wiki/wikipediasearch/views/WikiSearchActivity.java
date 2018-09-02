package com.repo.wiki.wikipediasearch.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.repo.wiki.wikipediasearch.R;
import com.repo.wiki.wikipediasearch.databinding.ActivityWikiSearchBinding;
import com.repo.wiki.wikipediasearch.viewmodel.WikiSearchViewModel;

public class WikiSearchActivity extends AppCompatActivity{

    private WikiSearchViewModel wikiSearchViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wikiSearchViewModel = ViewModelProviders.of(this).get(WikiSearchViewModel.class);
        ActivityWikiSearchBinding activityWikiSearchBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_wiki_search);
        activityWikiSearchBinding.setWikiSearchViewModel(wikiSearchViewModel);
    }
}
