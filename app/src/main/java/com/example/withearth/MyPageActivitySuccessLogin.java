package com.example.withearth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MyPageActivitySuccessLogin extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_success_login);

        mFirebaseAuth = FirebaseAuth.getInstance();  //초기화

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃 하기
                mFirebaseAuth.signOut();

                //로그아웃 이후 LoginActivity로 이동
                Intent intent = new Intent(MyPageActivitySuccessLogin.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}