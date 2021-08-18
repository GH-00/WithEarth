package com.example.withearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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


        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


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


    //툴바 뒤로가기 버튼 클릭 시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}