package com.repo.wiki.wikipediasearch.api;

import com.repo.wiki.wikipediasearch.entity.WikiSearch;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WikiSearchApi {

    @GET("/w/api.php")
    Single<WikiSearch> getWikiSearchResult(
            @Query("action") String action,
            @Query("format") String format,
            @Query("prop") String prop,
            @Query("generator") String generator,
            @Query("redirects") int redirects,
            @Query("formatversion") int formatversion,
            @Query("piprop") String piprop,
            @Query("pithumbsize") int pithumbsize,
            @Query("wbptterms") String wbPatterns,
            @Query("gpssearch") String query,
            @Query("gpslimit") int limit,
            @Query("gpsoffset") int offset);
}
