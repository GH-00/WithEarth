package com.example.withearth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        mFirebaseAuth = FirebaseAuth.getInstance();

        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //탈퇴 버튼
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //정말 탈퇴할 것인지 묻는 팝업창
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                ad.setIcon(R.drawable.ic_environment);
                ad.setTitle("회원 탈퇴");
                ad.setMessage("        회원 탈퇴 시 계정과 관련된 정보는\n         복구가 불가하며 현재 보유 중인 \n            포인트는 모두 소멸됩니다.\n\n    계정을 영구적으로 삭제하시겠습니까?");

                //긍정 버튼
                ad.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //탈퇴 하기
                        mFirebaseAuth.getCurrentUser().delete();
                        Toast.makeText(v.getContext(), "탈퇴 처리가 완료되었습니다.", Toast.LENGTH_SHORT).show();

                        //다이얼로그 팝업창 닫기
                        dialog.dismiss();

                        //탈퇴 이후 LoginActivity로 이동
                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                //부정 버튼
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //다이얼로그 팝업창 닫기
                        dialog.dismiss();
                    }
                });
                ad.show();

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
