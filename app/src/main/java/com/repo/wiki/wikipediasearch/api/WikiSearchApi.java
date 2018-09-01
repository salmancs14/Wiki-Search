package com.repo.wiki.wikipediasearch.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Query;

interface WikiSearchApi {
    public Single<List<String>> getWikiSearchResult(
            @Query("action") String action,
            @Query("format") String format,
            @Query("prop") String prop,
            @Query("redirects") String redirects,
            @Query("formatversion") String formatversion,
            @Query("piprop") String piprop,
            @Query("pithumbsize") int pithumbsize,
            @Query("pilimit") int piLimit,
            @Query("wbptterms") int wbPatterns,
            @Query("gpssearch") String practiceId,
            @Query("gpslimit") int limit,
            @Query("gpsoffset") int offset);
}
