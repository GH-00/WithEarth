package com.example.withearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.example.withearth.StoreActivity.orderNum;


public class StoreActivityOrderSuccess extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth auth;
    private int overTotalPrice = 0;
    private TextView txtTotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_store_order_success);

        recyclerView = findViewById(R.id.order_list_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalAmount = (TextView) findViewById(R.id.must_charge_tv);

        DatabaseReference orderListRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        FirebaseRecyclerOptions<StoreActivityOrderProduct> options =
                new FirebaseRecyclerOptions.Builder<StoreActivityOrderProduct>()
                        .setQuery(orderListRef.child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum)).child("product"),
                                StoreActivityOrderProduct.class).build();

        FirebaseRecyclerAdapter<StoreActivityOrderProduct, StoreActivityOrderViewHolder> adapter
                = new FirebaseRecyclerAdapter<StoreActivityOrderProduct, StoreActivityOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull StoreActivityOrderViewHolder holder, int position, @NonNull @NotNull StoreActivityOrderProduct model) {
                holder.txtProductName.setText(model.getName());
                holder.txtProductPrice.setText(model.getPrice());
                holder.txtProductQuantity.setText(model.getQuantity());
                Picasso.get().load(model.getImage()).into(holder.txtProductImage);

                int oneTyprProductPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTyprProductPrice;
                txtTotalAmount.setText(String.valueOf(overTotalPrice));

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

        orderNum++;
        //FirebaseDatabase database;
        //database = FirebaseDatabase.getInstance();
        //database.getReference().child("Orders").child(auth.getCurrentUser().getUid());
        DatabaseReference numRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(auth.getCurrentUser().getUid());
        HashMap<String, Object> numMap = new HashMap<>();
        numMap.put("ordernum", orderNum);
        numRef.updateChildren(numMap);


    }
}