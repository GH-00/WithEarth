package com.example.withearth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StoreActivityFood extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<StoreActivityProduct> productArrayList;
    private StoreActivityMyAdapter storeActivityMyAdapter;
    private FirebaseFirestore db;
    static int orderNum;
    private Button searchBtn;
    private ImageButton categoryBtn;
    private EditText searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchWord = (EditText) findViewById(R.id.searchWord);
        categoryBtn = (ImageButton) findViewById(R.id.categoryBtn);

        //Grid recyclerview 사용
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //상품 데이터 저장 - firestore 사용
        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<StoreActivityProduct>();
        storeActivityMyAdapter = new StoreActivityMyAdapter(this, productArrayList);

        recyclerView.setAdapter(storeActivityMyAdapter);
        EventChangeListener();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(searchWord.getText().toString());

            }
        });

        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivityFood.this, StoreActivityCategory.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //검색 기능, 단어 포함된 항목 검색
    private void search(String searchWord) {
        db.collection("StoreProducts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                productArrayList.clear();
                for (DocumentChange dc : value.getDocumentChanges()) {

                    if (dc.getType() == DocumentChange.Type.ADDED){
                        if (dc.getDocument().getString("name").contains(searchWord)) {
                            productArrayList.add(dc.getDocument().toObject(StoreActivityProduct.class));
                        }

                    }

                    storeActivityMyAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void EventChangeListener() {
        db.collection("StoreProducts").orderBy("category", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            Log.e("error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED) {
                                if (dc.getDocument().getString("category").contains("food")) {

                                    productArrayList.add(dc.getDocument().toObject(StoreActivityProduct.class));

                                }
                            }

                            storeActivityMyAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }

}
