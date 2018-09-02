package com.repo.wiki.wikipediasearch.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pages {
    @SerializedName("pageid")
    public int pageId;

    @SerializedName("title")
    public String title;

    @SerializedName("thumbnail")
    public PageThumbnail thumbnail = new PageThumbnail();

    @SerializedName("terms")
    public Terms terms = new Terms();

    public class PageThumbnail {
        @SerializedName("source")
        public String source;

        @SerializedName("width")
        public int width;

        @SerializedName("height")
        public int height;

    }

    public class Terms {
        @SerializedName("description")
        public ArrayList<String> description = new ArrayList<>();
    }

}
