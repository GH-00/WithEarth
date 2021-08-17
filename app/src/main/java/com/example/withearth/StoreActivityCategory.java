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

public class StoreActivityCategory extends AppCompatActivity {

    private Button cateCloth, cateFood, cateSun, cateCos, cateClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_category);


        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        cateCloth = (Button) findViewById(R.id.cate_cloth);
        cateFood = (Button) findViewById(R.id.cate_food);
        cateCos = (Button) findViewById(R.id.cate_cos);
        cateClean = (Button) findViewById(R.id.cate_clean);
        cateSun = (Button) findViewById(R.id.cate_sundries);

        cateCloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivityCategory.this, StoreActivityCloth.class);
                startActivity(intent);
                finish();
            }
        });

        cateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivityCategory.this, StoreActivityFood.class);
                startActivity(intent);
                finish();
            }
        });

        cateClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivityCategory.this, StoreActivityClean.class);
                startActivity(intent);
                finish();
            }
        });

        cateCos.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                Intent intent = new Intent(StoreActivityCategory.this, StoreActivityCosmetic.class);
                startActivity(intent);
                finish();
            }
        });

        cateSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivityCategory.this, StoreActivitySundries.class);
                startActivity(intent);
                finish();
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