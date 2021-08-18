package com.example.withearth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PwdCheckActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_check);

        mFirebaseAuth = FirebaseAuth.getInstance();

        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText et_pwd = (EditText) findViewById(R.id.et_pwd);
        databaseReference = database.getReference("Users").child(mFirebaseAuth.getCurrentUser().getUid());


        Button btn_check = (Button) findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strEt_pwd = et_pwd.getText().toString().trim();
                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                mFirebaseAuth.signInWithEmailAndPassword(user.getEmail(), strEt_pwd).addOnCompleteListener(PwdCheckActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //확인 후 UserInfoActivity로 이동
                            Intent intent = new Intent(v.getContext(), UserInfoActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0, 0);


                        }
                        else{
                            Toast.makeText(PwdCheckActivity.this,"잘못된 비밀번호에요.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
