package com.example.withearth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class StoreActivityCartUnlogin extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_store_cart_unlogin);

        /*//로그인 했을 시
        if (auth.getCurrentUser() != null) {

            StoreActivityCart storeActivityCart = new StoreActivityCart();
            Intent intent = new Intent(StoreActivityCartUnlogin.this, StoreActivityCart.class);
            startActivity(intent);
            에바.......

        } else {*/

            Button btn_gologin = (Button) findViewById(R.id.btn_gologin);
            btn_gologin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StoreActivityCartUnlogin.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        //}

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
