package com.repo.wiki.wikipediasearch.entity;

import com.google.gson.annotations.SerializedName;

public class Pages {
    @SerializedName("pageid")
    public int pageId;

    @SerializedName("title")
    public int title;

    @SerializedName("thumbnail")
    public PageThumbnail thumbnail = new PageThumbnail();


    @SerializedName("terms")
    public Terms terms = new Terms();

    public static class PageThumbnail {
        @SerializedName("source")
        public String source;
    }

    public static class Terms {
        @SerializedName("description")
        public String description;
    }

}
