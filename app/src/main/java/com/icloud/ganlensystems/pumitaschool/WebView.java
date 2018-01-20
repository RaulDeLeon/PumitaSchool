package com.icloud.ganlensystems.pumitaschool;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;

public class WebView extends AppCompatActivity {
    private Context mContext;
    private android.webkit.WebView mWebView;
    private String mUrl="https://www.unam.mx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        mContext = getApplicationContext();
        mWebView = (android.webkit.WebView) findViewById(R.id.web_view);
        renderWebPage(mUrl);
    }

    protected void renderWebPage(String urlToRender) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);

        //////////////////////////////////////// Specify the app cache path
        mWebView.getSettings().setAppCachePath(mContext.getCacheDir().getPath());
        /////////////////////////////////////////////////////////////////////

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.loadUrl(urlToRender);
    }
}