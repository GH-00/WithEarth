package com.example.withearth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JjimActivityEdit extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<JjimProduct> products = new ArrayList<JjimProduct>();
    private JjimAdapterEdit adapter;
    private FirebaseAuth auth;

    public static Toast mToast;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_jjim_edit);

        //커스텀 툴바 생성
        Toolbar base_toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(base_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //recyclerview - adapter 연결
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.jjim_product_edit);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setHasFixedSize(true);
        adapter = new JjimAdapterEdit(getApplicationContext(), products);
        recyclerView.setAdapter(adapter);
        adapter.setData(products);

        //firebase 불러오기
        databaseReference = database.getReference("Jjim Menu").child(auth.getCurrentUser().getUid()).child("Jjim Lists");
        loadData(products, adapter, databaseReference);

        //바텀 네비게이션바 버튼
        BottomNavigationView bottom_edit = findViewById(R.id.bottom_edit);
        bottom_edit.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                SparseBooleanArray selected = adapter.getSelectedItems();
                JjimProduct selectedItem;
                switch (item.getItemId()) {
                    case R.id.action_up:
                        if (selected.size() != 0) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

                            for (int i = (selected.size() - 1); i >= 0; i--)
                                if (selected.valueAt(i)) {
                                    //현재 시간
                                    long now = System.currentTimeMillis();
                                    Date mDate = new Date(now);
                                    String pTime = simpleDateFormat.format(mDate);

                                    selectedItem = products.get(selected.keyAt(i));
                                    databaseReference.child(selectedItem.getName()).child("time").setValue(pTime);
                                }
                            adapter.removeAllItem();
                            loadData(products, adapter, databaseReference);
                            adapter.clearSelectedItem();
                            Toast.makeText(JjimActivityEdit.this, "선택한 상품의 위치를 변경했어요.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(JjimActivityEdit.this, "선택한 상품이 없어요.", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_nocheck:
                        adapter.clearSelectedItem();
                        break;

                    case R.id.action_allcheck:
                        adapter.selectAllItem();
                        break;

                    case R.id.action_delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(JjimActivityEdit.this);
                        builder.setTitle("상품 삭제").setMessage("정말 선택하신 상품을 삭제하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (selected.size() != 0) {
                                    for (int i = (selected.size() - 1); i >= 0; i--)
                                        if (selected.valueAt(i)) {
                                            JjimProduct selectedItem = products.get(selected.keyAt(i));
                                            JjimProduct removedItem = adapter.removeItem(selectedItem);
                                            databaseReference.child(removedItem.getName()).removeValue();
                                        }
                                    adapter.clearSelectedItem();
                                    Toast.makeText(JjimActivityEdit.this, "찜한 목록에서 선택한 상품을 삭제했어요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(JjimActivityEdit.this, "선택한 상품이 없어요.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).show();

                        break;
                }
                return true;
            }
        });


        //최상단 이동 버튼
        FloatingActionButton btn_up = (FloatingActionButton) findViewById(R.id.btn_up);
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });


    }

    //firebase 불러오기
    public void loadData(ArrayList<JjimProduct> products, JjimAdapterEdit adapter, DatabaseReference databaseReference) {
        Query sortbyTime = databaseReference.orderByChild("time");

        sortbyTime.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    JjimFirebasePost value = postSnapshot.getValue(JjimFirebasePost.class);
                    products.add(0, new JjimProduct(value.getImage(), value.getPrice(), value.getName()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w("getFirebaseDatabase", "Failed to read value", error.toException());
            }
        });
    }

    public void checkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(JjimActivityEdit.this);
        builder.setTitle("상품 삭제").setMessage("선택하신 상품을 삭제하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }

    //툴바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_jjim_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:
                finish();
                overridePendingTransition(0, 0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }


}
