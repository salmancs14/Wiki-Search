package com.repo.wiki.wikipediasearch;

import android.app.Application;
import android.content.Context;


import com.repo.wiki.wikipediasearch.utils.ConnectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WikiSearchApplication extends Application {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://en.wikipedia.org/";

    private static OkHttpClient getCacheClient(final Context context) {
        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
            Response originalResponse = chain.proceed(chain.request());
            if (ConnectionUtils.isNetConnected(context)) {
                // Internet available; read from cache for 1 day
                // Why? Reduce server load, better UX
                int maxAge = 60 * 60 * 24;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                // No internet; tolerate cached data for 1 week
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };

        File httpCacheDirectory = new File(context.getCacheDir(), "cachedir");
        int size = 5 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, size);

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();
    }


    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getCacheClient(context))
                    .build();
        }
        return retrofit;
    }

}
