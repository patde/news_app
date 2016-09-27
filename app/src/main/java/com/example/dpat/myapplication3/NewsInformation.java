package com.example.dpat.myapplication3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class NewsInformation extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_information);

        webView = (WebView) findViewById(R.id.webview);

        Bundle bundle = getIntent().getExtras();
        webView.loadUrl(bundle.getString("Link"));
    }

}
