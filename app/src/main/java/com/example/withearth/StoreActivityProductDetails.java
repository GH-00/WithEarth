package com.example.withearth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class StoreActivityProductDetails extends AppCompatActivity {
    private ImageView productImage;
    private TextView productPrice;
    private TextView productName;
    private TextView productDescription;
    private Button addToCartButton;
    private Button purchaseButton;
    private ElegantNumberButton numberButton;
    Context context;

    private String pName;
    private String pPrice;
    private String pDescription;
    private String pImage;
    private String pTime;

    private FirebaseAuth auth;
    private int orderNum;


    private Intent intent;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_store_product_details);

        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);

        //intent로 전달된 상품 정보 받아오기
        pName = getIntent().getStringExtra("name");
        pPrice = getIntent().getStringExtra("price");
        pDescription = getIntent().getStringExtra("description");
        pImage = getIntent().getStringExtra("image");

        productImage = (ImageView) findViewById(R.id.product_image_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);

        addToCartButton = (Button) findViewById(R.id.add_to_cart_btn);
        purchaseButton = (Button) findViewById(R.id.purchase_btn);

        productName.setText(pName);
        productPrice.setText(pPrice);
        productDescription.setText(pDescription);
        Picasso.get().load(pImage).into(productImage);

        DatabaseReference numListRef = FirebaseDatabase.getInstance().getReference();
        numListRef.child("Orders").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("ordernum")){
                            int value = snapshot.child("ordernum").getValue(int.class);
                            orderNum = value;

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        //장바구니를 거치지 않고 바로 주문하기

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pQuantity = numberButton.getNumber();

                // 상품 구매 시 장바구니 거치지 않고 바로 StoreActivityConfirmOrder로 이동, Orders에 정보 저장

                DatabaseReference orderProductRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                        .child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum)).child("products");

                HashMap<String, Object> orderProductMap = new HashMap<>();
                orderProductMap.put("name", pName);
                orderProductMap.put("price", pPrice);
                orderProductMap.put("image", pImage);
                orderProductMap.put("quantity", pQuantity);
                DatabaseReference totalPriceRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                        .child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum));
                HashMap<String, Object> totalPriceMap = new HashMap<>();


                //총 금액 계산
                int iQuantity = Integer.valueOf(pQuantity).intValue();
                int iPrice = Integer.valueOf(pPrice).intValue();
                int tPrice = iQuantity * iPrice;
                String sPrice = String.valueOf(tPrice);
                totalPriceMap.put("total", sPrice);

                orderProductRef.child(pName).updateChildren(orderProductMap);
                totalPriceRef.updateChildren(totalPriceMap);

                //confirm order로 이동
                Intent intent = new Intent(StoreActivityProductDetails.this, StoreActivityConfirmOrder.class);
                intent.putExtra("total", sPrice);
                startActivity(intent);
                finish();
            }
        });

        //장바구니를 거쳐서 주문하기, 바로 페이지 넘어가지 않고 뒤로가기 -> 상품목록에서 장바구니 버튼 눌러야함

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cart list - user view에 회원 id로 현재 물품과 수량 저장
                final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("name", pName);
                cartMap.put("price", pPrice);
                cartMap.put("image", pImage);
                cartMap.put("quantity", numberButton.getNumber());

                cartListRef.child("User View").child(auth.getCurrentUser().getUid()).child("Products").child(pName)
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
        });

        //찜 버튼 누를 시 찜 기능
        ImageButton btn_jjim = findViewById(R.id.btn_jjim);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Jjim Menu")
                .child(auth.getCurrentUser().getUid()).child("Jjim Lists");
        databaseReference.child(pName).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //파이어 검색 후 상품이 찜에 존재하면
                String value = snapshot.getValue(String.class);
                if (value != null) { //상품이 찜에 존재할 시
                    //버튼 색
                    btn_jjim.setImageResource(R.drawable.ic_jjim_selected);
                    btn_jjim.setColorFilter(Color.parseColor("#FFFF1F53"));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w("getFirebaseDatabase", "Failed to read value-jjimcolor", error.toException());
            }
        });

        btn_jjim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //시간 역순 정렬 위한 time 구하기
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                pTime = simpleDateFormat.format(mDate);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Jjim Menu")
                        .child(auth.getCurrentUser().getUid()).child("Jjim Lists");
                //databaseReference = databaseReference.child(auth.getCurrentUser().getUid()).child("Jjim Lists").child(pName);
                databaseReference.child(pName).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);

                        if (value != null) {
                            btn_jjim.setImageResource(R.drawable.ic_favorite);
                            btn_jjim.setColorFilter(Color.parseColor("#FFFF1F53"));
                            Toast.makeText(StoreActivityProductDetails.this, "\'찜\'을 해제했어요.", Toast.LENGTH_SHORT).show();
                            postFirebaseDatabase(false, databaseReference);
                        }

                        else {
                            btn_jjim.setImageResource(R.drawable.ic_jjim_selected);
                            btn_jjim.setColorFilter(Color.parseColor("#FFFF1F53"));
                            Toast.makeText(StoreActivityProductDetails.this, "\'찜\'을 설정했어요.", Toast.LENGTH_SHORT).show();
                            postFirebaseDatabase(true, databaseReference);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Log.w("getFirebaseDatabase", "Failed to read value-jjimbutton", error.toException());
                    }
                });

            }
        });

    }

    //상품 삭제, 추가 쉽게 하기
    public void postFirebaseDatabase(boolean add, DatabaseReference mPostReference) {
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if (add) {
            JjimFirebasePost post = new JjimFirebasePost(pImage, pName, pPrice, pTime);
            postValues = post.toMap();
        }
        childUpdates.put(pName, postValues);
        mPostReference.updateChildren(childUpdates);
    }


    //툴바 뒤로가기 버튼 클릭 시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    //백 버튼 누를 시 애니메이션 없애기
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }


}

