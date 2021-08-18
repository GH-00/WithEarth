package com.example.withearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class StoreActivityOrderSuccess extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth auth;
    private TextView order_total_price, must_charge_tv;
    private TextView dateTime, orderTime;
    private int orderNum, newNum;
    private String total;
    private Button keepShopBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_store_order_success);

        // 주문 완료 목록 리사이클러뷰 생성, 주문완료 목록 보여줌
        recyclerView = findViewById(R.id.order_list_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        dateTime = (TextView) findViewById(R.id.date_tv);
        orderTime = (TextView) findViewById(R.id.order_time_tv);
        keepShopBtn = (Button) findViewById(R.id.keep_shop);
        keepShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivityOrderSuccess.this, MainActivity.class);
                startActivity(intent);
            }
        });

        must_charge_tv = (TextView) findViewById(R.id.must_charge_tv);
        order_total_price = (TextView) findViewById(R.id.order_total_price);

        orderNum = getIntent().getIntExtra("ordernum", orderNum);

        DatabaseReference getPriceRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum));
        getPriceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                total = snapshot.child("total").getValue(String.class);
                must_charge_tv.setText(total);
                order_total_price.setText(total);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        Calendar currentCal = Calendar.getInstance();
        int currentYear = currentCal.get(Calendar.YEAR);
        int currentMonth = currentCal.get(Calendar.MONTH) + 1;
        int currentDay = currentCal.get(Calendar.DAY_OF_MONTH);
        int currentHour = currentCal.get(Calendar.HOUR);
        int currentMin = currentCal.get(Calendar.MINUTE);
        orderTime.setText(currentYear + "년 "+currentMonth+"월 "+currentDay+"일 "+currentHour+"시 "+currentMin+"분");



        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dateTime.setText(year + "년 " + month + "월 " + day + "일 23시 59분까지");


        DatabaseReference orderListRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        FirebaseRecyclerOptions<StoreActivityOrderProduct> options =
                new FirebaseRecyclerOptions.Builder<StoreActivityOrderProduct>()
                        .setQuery(orderListRef.child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum)).child("products"),
                                StoreActivityOrderProduct.class).build();

        FirebaseRecyclerAdapter<StoreActivityOrderProduct, StoreActivityOrderViewHolder> adapter
                = new FirebaseRecyclerAdapter<StoreActivityOrderProduct, StoreActivityOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull StoreActivityOrderViewHolder holder, int position, @NonNull @NotNull StoreActivityOrderProduct model) {
                holder.txtProductName.setText(model.getName());
                holder.txtProductPrice.setText(model.getPrice());
                holder.txtProductQuantity.setText(model.getQuantity());
                Picasso.get().load(model.getImage()).into(holder.txtProductImage);

            }

            @NonNull
            @NotNull
            @Override
            public StoreActivityOrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_store_order_success_items, parent, false);
                StoreActivityOrderViewHolder holder = new StoreActivityOrderViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //주문 완료시 ordernum 1 증가, Orders 밑에 회원 ID로 저장
        newNum = orderNum + 1;
        DatabaseReference nextnumRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(auth.getCurrentUser().getUid());
        HashMap<String, Object> nextnumMap = new HashMap<>();
        nextnumMap.put("ordernum", newNum);
        nextnumRef.updateChildren(nextnumMap);


    }

    @Override
    protected void onResume() {
        super.onResume();




    }
}