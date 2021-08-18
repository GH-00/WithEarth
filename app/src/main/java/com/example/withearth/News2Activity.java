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

public class News2Activity extends AppCompatActivity {
    private WebView WebView2; // 웹뷰 선언
    private WebSettings WebSettings2; //웹뷰세팅


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news2);


        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


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