package com.example.withearth;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserInfoActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

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

        //로그인 한 이메일 출력
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        TextView emailIdData = (TextView) findViewById(R.id.emailIdData);
        emailIdData.setText(user.getEmail());


        //이름 출력
        TextView nameData = (TextView) findViewById(R.id.nameData);
        databaseReference = database.getReference("Users").child(mFirebaseAuth.getCurrentUser().getUid());
        databaseReference.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                nameData.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //핸드폰 번호, 비밀번호 추가
        EditText et_phonenumber = (EditText) findViewById(R.id.et_phonenumber);
        EditText et_pwd = (EditText) findViewById(R.id.et_pwd);
        EditText et_pwd_check = (EditText) findViewById(R.id.et_pwd_check);


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


        //수정 버튼
        Button btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEt_phonenumber = et_phonenumber.getText().toString().trim();
                String strPwd = et_pwd.getText().toString().trim();
                String strPwdcheck = et_pwd_check.getText().toString().trim();

                databaseReference = database.getReference("Users").child(mFirebaseAuth.getCurrentUser().getUid());

                //사용자가 비밀번호를 수정하려 했을 때
                if ((!strPwd.isEmpty()) && (!strPwdcheck.isEmpty())) {
                    //비밀번호가 같을 때
                    if (strPwd.equals(strPwdcheck)) {
                        //비밀번호 업데이트
                        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        mFirebaseUser.updatePassword(strPwd)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //비밀번호 넣기
                                            databaseReference.child("password").setValue(strPwd);
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(UserInfoActivity.this, "비밀번호가 일치하지 않아요.", Toast.LENGTH_SHORT).show();
                    }

                }


                //사용자가 핸드폰 번호를 추가정보로 제공하려 했을 때
                if (!strEt_phonenumber.isEmpty()) {
                    //핸드폰 번호 넣기
                    databaseReference.child("phone number").setValue(strEt_phonenumber);
                }

                //완료 시 안내 문구 출력 후 이동
                Toast.makeText(UserInfoActivity.this, "회원정보 수정이 완료되었어요.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        //취소 버튼
        Button btn_editcancel = (Button) findViewById(R.id.btn_editcancel);
        btn_editcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
