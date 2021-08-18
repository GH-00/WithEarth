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

public class News2Activity extends AppCompatActivity {
    private WebView WebView2; // 웹뷰 선언
    private WebSettings WebSettings2; //웹뷰세팅


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news2);


        WebView2 = (WebView) findViewById(R.id.webView2);


        WebView2.setWebViewClient(new WebViewClient());
        WebSettings2 = WebView2.getSettings();
        WebSettings2.setJavaScriptEnabled(true);
        WebSettings2.setLoadWithOverviewMode(true);
        WebSettings2.setUseWideViewPort(true);
        WebSettings2.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WebSettings2.setCacheMode(WebSettings.LOAD_NO_CACHE);
        WebSettings2.setDomStorageEnabled(true);
        WebView2.loadUrl("https://www.yna.co.kr/view/AKR20180201130900797");


    }
}