package com.example.withearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class StoreActivityConfirmOrder extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, usePointEditText;
    private Button confirmOrderBtn, usePointBtn;
    private FirebaseAuth auth;
    private String point;
    private int orderNum;
    private TextView totalPricetv, remainPointtv, final_discount_price, final_product_price;
    String stOrderNum;
    String currentPoint;
    String usePoint;
    String finalRemainPoint;
    String totalPrice;

    int intCurrentPoint;
    double realPoint;
    int intUsePoint, intFinalRemainPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_store_confirm_order);


        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        totalPrice = getIntent().getStringExtra("total");
        stOrderNum = getIntent().getStringExtra("ordernum");
        orderNum = Integer.parseInt(stOrderNum);

        usePointBtn = (Button) findViewById(R.id.use_point_btn);
        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shippment_name);
        phoneEditText = (EditText) findViewById(R.id.shippment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shippment_address);

        usePointEditText = (EditText) findViewById(R.id.use_point_et);

        totalPricetv = (TextView) findViewById(R.id.total_price_tv);
        remainPointtv = (TextView) findViewById(R.id.remain_point_tv);
        totalPricetv.setText(totalPrice);

        final_discount_price = (TextView) findViewById(R.id.final_discount_price);
        final_product_price = (TextView) findViewById(R.id.final_product_price);
        final_product_price.setText(totalPrice + "원");
        final_discount_price.setText("0원");


        DatabaseReference pointLoadRef = FirebaseDatabase.getInstance().getReference().child("Point")
                .child(auth.getCurrentUser().getUid());
        pointLoadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                currentPoint = snapshot.child("point").getValue(String.class);
                remainPointtv.setText(currentPoint);
                intCurrentPoint = Integer.parseInt(currentPoint);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        usePointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usePoint = usePointEditText.getText().toString();
                intUsePoint = Integer.parseInt(usePoint);
                intFinalRemainPoint = intCurrentPoint - intUsePoint ;
                finalRemainPoint = String.valueOf(intFinalRemainPoint);

                if (usePoint != null){

                    if (intUsePoint > intCurrentPoint){
                        Toast.makeText(StoreActivityConfirmOrder.this, "입력한 포인트가 잔액을 초과하였습니다.", Toast.LENGTH_SHORT).show();

                    }

                    else {

                        int intTotalPrice = Integer.parseInt(totalPrice);
                        int finalPrice = intTotalPrice - intUsePoint;

                        totalPricetv.setText(String.valueOf((int) finalPrice) +"원");
                        final_discount_price.setText(usePoint + "원");


                        DatabaseReference newtotalRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                        newtotalRef = newtotalRef.child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum));
                        HashMap<String, Object> newtotalMap = new HashMap<>();
                        newtotalMap.put("total", String.valueOf((int) finalPrice));
                        newtotalRef.updateChildren(newtotalMap);

                        DatabaseReference newPointRef = FirebaseDatabase.getInstance().getReference().child("Point")
                                .child(auth.getCurrentUser().getUid());
                        newPointRef.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (snapshot.hasChild("point")){

                                    HashMap<String, Object> newPointMap = new HashMap<>();
                                    newPointMap.put("point", finalRemainPoint);
                                    newPointRef.updateChildren(newPointMap);

                                }

                                else {


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                    }


                }

                else {
                    Toast.makeText(StoreActivityConfirmOrder.this, "사용할 포인트를 입력하세요", Toast.LENGTH_SHORT).show();
                }


            }
        });



        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();

                //구매 금액의 5% 포인트 적립
                DatabaseReference totalRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                totalRef = totalRef.child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum)).child("total");
                totalRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        point = snapshot.getValue(String.class);
                        int totalint = Integer.parseInt(point);
                        double finalpoint = Math.floor(totalint * 0.05);


                        DatabaseReference pointRef = FirebaseDatabase.getInstance().getReference().child("Point")
                                .child(auth.getCurrentUser().getUid());
                        pointRef.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (snapshot.hasChild("point")){
                                    String totalPoint = snapshot.child("point").getValue(String.class);
                                    double doubletotalPoint = Double.parseDouble(totalPoint);

                                    realPoint = doubletotalPoint + finalpoint;
                                    point = String.valueOf((int) realPoint);

                                    HashMap<String, Object> pointMap = new HashMap<>();
                                    pointMap.put("point", point);
                                    pointRef.updateChildren(pointMap);

                                }

                                else {
                                    point = String.valueOf((int)finalpoint);
                                    HashMap<String, Object> pointMap = new HashMap<>();
                                    pointMap.put("point", point);
                                    pointRef.updateChildren(pointMap);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });



            }
        });

    }

    //배송지 정보 입력, 빈칸일 경우 toast 출력
    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "주소를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else
            ConfirmOrder();


    }

    //주문 정보 저장 realtime database 이용, Orders 밑에 회원 ID로 저장
    private void ConfirmOrder() {

        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum));
        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){

                    Intent intent = new Intent(StoreActivityConfirmOrder.this, StoreActivityOrderSuccess.class);
                    intent.putExtra("ordernum", orderNum);
                    startActivity(intent);
                    finish();


                }
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