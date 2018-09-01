package com.repo.wiki.wikipediasearch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WikiSearch {
    @SerializedName("query")
    public ArrayList pages = new ArrayList<Pages>();
}
