package com.example.withearth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StoreActivityCategory extends AppCompatActivity {

    private Button cateCloth, cateFood, cateSun, cateCos, cateClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_category);

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
}