package com.example.withearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class StoreActivityCart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount;
    private int overTotalPrice = 0;
    private FirebaseAuth auth;
    private int orderNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_store_cart);

        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //장바구니 상품 목록 LinearLayout recyclerview 이용
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);

        // 사용자 ordernum 가져오기
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

    }

    @Override
    protected void onStart() {

        super.onStart();

        //장바구니 recyclerview 설정

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<StoreActivityCartProduct> options =
                new FirebaseRecyclerOptions.Builder<StoreActivityCartProduct>()
                        .setQuery(cartListRef.child("User View").child(auth.getCurrentUser().getUid()).child("Products"),
                                StoreActivityCartProduct.class).build();

        FirebaseRecyclerAdapter<StoreActivityCartProduct, StoreActivityCartViewHolder> adapter
                = new FirebaseRecyclerAdapter<StoreActivityCartProduct, StoreActivityCartViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull @NotNull StoreActivityCartViewHolder holder, int position, @NonNull @NotNull StoreActivityCartProduct model) {
                holder.txtProductName.setText(model.getName());
                holder.txtProductPrice.setText(model.getPrice() + "원");
                holder.txtProductQuantity.setText(model.getQuantity() + "개");
                Picasso.get().load(model.getImage()).into(holder.txtProductImage);

                int oneTyprProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTyprProductTPrice;
                txtTotalAmount.setText("총" + String.valueOf(overTotalPrice)+"원");

                // 주문하기 버튼 누르면 StoreActivityConfirmOrder로 이동
                // Cart List에 담긴 장바구니 목록을 모두 Orders -  회원 ID - order Num -  products로 이동
                NextProcessBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                                .child("Cart List").child("User View").child(auth.getCurrentUser().getUid()).child("Products");
                        orderRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                                    String name = postSnapshot.child("name").getValue(String.class);
                                    String price = postSnapshot.child("price").getValue(String.class);
                                    String quantity = postSnapshot.child("quantity").getValue(String.class);
                                    String image = postSnapshot.child("image").getValue(String.class);
                                    DatabaseReference orListRef = FirebaseDatabase.getInstance().getReference()
                                            .child("Orders").child(auth.getCurrentUser().getUid())
                                            .child(String.valueOf(orderNum)).child("products");
                                    HashMap<String, Object> orListMap = new HashMap<>();
                                    orListMap.put("name", name);
                                    orListMap.put("price", price);
                                    orListMap.put("quantity", quantity);
                                    orListMap.put("image", image);
                                    orListRef.child(name).updateChildren(orListMap);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference().child("Cart List")
                                .child("User View")
                                .child(auth.getCurrentUser().getUid())
                                .removeValue();

                        // 총 금액 계산 후 추가
                        DatabaseReference orderProductRef = FirebaseDatabase.getInstance().getReference()
                                .child("Orders").child(auth.getCurrentUser().getUid()).child(String.valueOf(orderNum));

                        HashMap<String, Object> totalPriceMap = new HashMap<>();
                        totalPriceMap.put("total", String.valueOf(overTotalPrice));
                        orderProductRef.updateChildren(totalPriceMap);

                        Intent intent = new Intent(StoreActivityCart.this, StoreActivityConfirmOrder.class);
                        intent.putExtra("total", String.valueOf(overTotalPrice));
                        startActivity(intent);
                        finish();
                    }
                });

                //상품 클릭시 장바구니에서 삭제 가능
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]{
                                "Remove"

                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(StoreActivityCart.this);
                        builder.setTitle("Cart Options: ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i == 0) {
                                    cartListRef.child("User View")
                                            .child(auth.getCurrentUser().getUid())
                                            .child("Products")
                                            .child(model.getName())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(StoreActivityCart.this, "장바구니에서 제거되었습니다.", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @NotNull
            @Override
            public StoreActivityCartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_store_cart_items, parent, false);
                StoreActivityCartViewHolder holder = new StoreActivityCartViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
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