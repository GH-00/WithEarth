package com.example.withearth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class StoreActivityProductDetails extends AppCompatActivity {
    private ImageView productImage;
    private TextView productPrice;
    private TextView productName;
    private TextView productDescription;

    private Intent intent;
    private FirebaseFirestore db;
    private StoreActivityMyAdapter storeActivityMyAdapter;
    private StoreActivityProduct products;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_product_details);

        String pName = getIntent().getStringExtra("name");
        String pPrice = getIntent().getStringExtra("price");
        String pDescription = getIntent().getStringExtra("description");
        String pImage = getIntent().getStringExtra("image");


        productImage = (ImageView) findViewById(R.id.product_image_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);

        productName.setText(pName);
        productPrice.setText(pPrice);
        productDescription.setText(pDescription);
        Picasso.get().load(pImage).into(productImage);


    }
}

