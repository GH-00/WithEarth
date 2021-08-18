package com.example.withearth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

public class News1Activity extends AppCompatActivity {
    private WebView WebView1; // 웹뷰 선언
    private WebSettings WebSettings1; //웹뷰세팅


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news1);


        WebView1 = (WebView) findViewById(R.id.webView1);


        WebView1.setWebViewClient(new WebViewClient());
        WebSettings1 = WebView1.getSettings();
        WebSettings1.setJavaScriptEnabled(true);
        WebSettings1.setLoadWithOverviewMode(true);
        WebSettings1.setUseWideViewPort(true);
        WebSettings1.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WebSettings1.setCacheMode(WebSettings.LOAD_NO_CACHE);
        WebSettings1.setDomStorageEnabled(true);
        WebView1.loadUrl("https://www.eroun.net/news/articleView.html?idxno=24373");

    }
}