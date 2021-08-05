package com.example.withearth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class StoreActivityProductDetails extends AppCompatActivity {
    private ImageView productImage;
    private TextView productPrice;
    private TextView productName;
    private TextView productDescription;
    private Button addToCartButton;
    Context context;


    private Intent intent;

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

        addToCartButton = (Button) findViewById(R.id.add_to_cart_btn);

        productName.setText(pName);
        productPrice.setText(pPrice);
        productDescription.setText(pDescription);
        Picasso.get().load(pImage).into(productImage);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saveCurrentTime, saveCurrentDate;
                Calendar calForDate = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentDate.format(calForDate.getTime());

                final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("name", pName);
                cartMap.put("price", pPrice);
                cartMap.put("image", pImage);

                //수정필요 ID 혹은 폰번호 받아오기, 회원가입 완성 후 User랑 연동

                cartListRef.child("User View").child("01012345678").child("Products").child(pName)
                        .updateChildren(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    cartListRef.child("Admin View").child("01012345678")
                                            .child("Products").child(pName)
                                            .updateChildren(cartMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast myToast = Toast.makeText(StoreActivityProductDetails.this, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT);
                                                        myToast.show();

                                                    }


                                                }
                                            });

                                }
                            }
                        });
            }
        });


    }
}

