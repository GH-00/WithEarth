package com.example.withearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰 (= 하단 바)
    private FragmentManager fm;
    private FragmentTransaction ft;

    private HomeActivity activity_home;
    private StoreActivity activity_store;
    private EnvironmentActivity activity_environment;
    private JJimActivity activity_jjim;
    private MyPageActivity activity_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_store:
                        setFrag(1);
                        break;
                    case R.id.action_environment:
                        setFrag(2);
                        break;
                    case R.id.action_jjim:
                        setFrag(3);
                        break;
                    case R.id.action_mypage:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });

        activity_home = new HomeActivity();
        activity_store = new StoreActivity();
        activity_environment = new EnvironmentActivity();
        activity_jjim = new JJimActivity();
        activity_mypage = new MyPageActivity();

        setFrag(0); //첫 프래그먼트 화면은 홈화면 (= 0번 화면)으로 지정
    }

    //프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, activity_home);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, activity_store);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, activity_environment);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, activity_jjim);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, activity_mypage);
                ft.commit();
                break;
        }
    }
}