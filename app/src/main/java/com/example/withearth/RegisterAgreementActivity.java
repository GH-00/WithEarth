package com.example.withearth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisterAgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_agreement);

        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //전체동의 체크박스
        CheckBox checkbox_all = (CheckBox)findViewById(R.id.checkbox_all);

        //하위 체크박스 3개
        CheckBox checkbox_one = (CheckBox)findViewById(R.id.checkbox_one);
        CheckBox checkbox_two = (CheckBox)findViewById(R.id.checkbox_two);
        CheckBox checkbox_three = (CheckBox)findViewById(R.id.checkbox_three);


        //전체동의 체크박스가 눌렸을 때
        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_all.isChecked()) {
                    checkbox_one.setChecked(true);
                    checkbox_two.setChecked(true);
                    checkbox_three.setChecked(true);
                }
                else {
                    checkbox_one.setChecked(false);
                    checkbox_two.setChecked(false);
                    checkbox_three.setChecked(false);
                }
            }
        });



        //ok버튼
        Button btn_agree = findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //동의 필수 체크박스를 모두 체크했을 때
                if (checkbox_one.isChecked() && checkbox_two.isChecked()) {
                    //회원가입 화면으로 이동
                    Intent intent = new Intent(RegisterAgreementActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
                //아닐 때
                else {
                    Toast.makeText(RegisterAgreementActivity.this, "동의 필수 항목을 체크해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //no버튼
        Button btn_disagree = findViewById(R.id.btn_disagree);
        btn_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 화면으로 이동
                Intent intent = new Intent(RegisterAgreementActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


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
