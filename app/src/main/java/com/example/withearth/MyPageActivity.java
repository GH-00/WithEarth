package com.example.withearth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPageActivity extends Fragment {
    private View view;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth;



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        setHasOptionsMenu(true);

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage, container, false);

        //로그인 했을 시
        if(mFirebaseAuth.getCurrentUser() != null){

            mFirebaseAuth = FirebaseAuth.getInstance();  //FirebaseAuth 객체 초기화

            //로그인 한 이메일 출력
            final FirebaseUser user = mFirebaseAuth.getCurrentUser();
            TextView emailIdData = view.findViewById(R.id.emailIdData);
            emailIdData.setText(user.getEmail());

            //이름 출력
            TextView nameData = view.findViewById(R.id.nameData);
            databaseReference = database.getReference("Users").child(mFirebaseAuth.getCurrentUser().getUid());
            databaseReference.child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.getValue(String.class);
                    nameData.setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });

            //포인트 출력
            TextView tv_point = view.findViewById(R.id.tv_point);
            databaseReference = database.getReference("Point").child(mFirebaseAuth.getCurrentUser().getUid());
            databaseReference.child("point").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String point = snapshot.getValue(String.class);
                    tv_point.setText(point);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            //로그아웃 버튼
            Button btn_logout = (Button) view.findViewById(R.id.btn_logout);
            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //로그아웃 하기
                    mFirebaseAuth.signOut();
                    Toast.makeText(v.getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                    //로그아웃 이후 LoginActivity로 이동
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });


            //탈퇴 버튼
            Button btn_delete = (Button) view.findViewById(R.id.btn_delete);
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


            //개인정보 수집 및 이용 버튼
            Button btn_collecting_using = (Button) view.findViewById(R.id.btn_collecting_using);
            btn_collecting_using.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //MyPageActivityCollectingUsing으로 이동
                    Intent intent = new Intent(v.getContext(), MyPageActivityCollectingUsing.class);
                    startActivity(intent);
                }
            });


        }
        //로그인 하지 않았을 시
        else{
            MyPageActivityUnlogin myPageActivityUnlogin = new MyPageActivityUnlogin();
            ((MainActivity)getActivity()).replaceFragment(myPageActivityUnlogin);
        }



        return view;
    }


    //툴바 생성
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_base_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(getActivity(), StoreActivityCart.class);
                startActivity(intent);
                return true;

            default :
                return super.onOptionsItemSelected(item) ;
        }
    }

}
