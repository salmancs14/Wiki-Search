package com.repo.wiki.wikipediasearch.repository;

import com.repo.wiki.wikipediasearch.api.WikiSearchApi;
import com.repo.wiki.wikipediasearch.entity.WikiSearch;

import io.reactivex.Single;

public class WikiSearchRepository {

    private WikiSearchApi wikiSearchApi;

    public WikiSearchRepository(WikiSearchApi wikiSearchApi) {
        this.wikiSearchApi = wikiSearchApi;
    }

    public Single<WikiSearch> getWikiResult(int limit, int offset, String query) {
        return wikiSearchApi.getWikiSearchResult("query", "json",
                "pageimages|pageterms", "prefixsearch", 1, 2,
                "thumbnail", 100, "description", query, limit, offset);
    }
}
