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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.example.withearth.StoreActivity.orderNum;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;       //파이어베이스 인증
    private DatabaseReference mDatabaseRef;   //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;        //로그인 입력필드


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("withEarth");

        mEtEmail = findViewById(R.id.et_email);  //연동
        mEtPwd = findViewById(R.id.et_pwd);      //연동



        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 요청
                String strEmail = mEtEmail.getText().toString();    //사용자가 입력한 값을 가져옴
                String strPwd = mEtPwd.getText().toString();        // "

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //로그인 성공
                            //MyPageActivitySuccessLogin으로 이동
                            Intent intent = new Intent(LoginActivity.this, MyPageActivitySuccessLogin.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show();
                            // 사용자 ordernum 생성
                            DatabaseReference numListRef = FirebaseDatabase.getInstance().getReference();
                            numListRef.child("Orders").child(mFirebaseAuth.getCurrentUser().getUid())
                                    .addValueEventListener(new ValueEventListener(){
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            if (snapshot.hasChild("ordernum")){
                                                int value = snapshot.child("ordernum").getValue(int.class);
                                                orderNum = value;

                                            }
                                            else{
                                                HashMap<String, Object> numMap = new HashMap<>();
                                                numMap.put("ordernum", 1);
                                                numListRef.child("Orders").child(mFirebaseAuth.getCurrentUser().getUid()).updateChildren(numMap);
                                                orderNum = 1;

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });

                            finish(); //현재 액티비티(=LoginActivity) 파괴
                        }
                        else {
                            //로그인 실패
                            Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });



        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 버튼을 눌렀을 때의 처리 (= 회원가입 화면으로 이동)
                //Intent : 화면을 이동할 때 사용
                //첫 번째 인자 : 현재 액티비티, 두 번째 인자 : 이동할 액티비티
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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

