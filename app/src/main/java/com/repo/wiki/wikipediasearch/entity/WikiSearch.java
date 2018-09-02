package com.repo.wiki.wikipediasearch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WikiSearch {
    @SerializedName("query")
    public Query query = new Query();

    public class Query {
        @SerializedName("pages")
        public ArrayList<Pages> pages = new ArrayList<>();

    }
}
