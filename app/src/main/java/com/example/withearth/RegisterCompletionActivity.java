package com.example.withearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RegisterCompletionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_completion);

        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //로그인하러가기 버튼
        Button btn_gologin = findViewById(R.id.btn_gologin);
        btn_gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 화면으로 이동
                Intent intent = new Intent(RegisterCompletionActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    //툴바 뒤로가기 버튼 클릭 시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}