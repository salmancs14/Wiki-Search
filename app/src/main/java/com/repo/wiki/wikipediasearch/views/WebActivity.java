package com.repo.wiki.wikipediasearch.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.repo.wiki.wikipediasearch.R;

public class WebActivity extends AppCompatActivity {

    public static final String BUNDLE_URL = "url";

    private WebView mWebView;
    private View mProgressBar;

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, WebActivity.class);
        if (args != null) {
            intent.putExtra(BUNDLE_URL, args.getString(BUNDLE_URL));
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        if (getIntent() != null) {
            String url = getIntent().getStringExtra(BUNDLE_URL);
            mProgressBar = findViewById(R.id.progress_bar);
            mWebView = findViewById(R.id.web_view);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String urlLoaded) {
                    super.onPageFinished(view, urlLoaded);
                    mProgressBar.setVisibility(View.GONE);
                }
            });
            mWebView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
