package com.example.withearth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StoreActivity extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<StoreActivityProduct> productArrayList;
    private StoreActivityMyAdapter storeActivityMyAdapter;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    //StoreActivity 화면 구성
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_store, container, false);

        // 장바구니 버튼
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(getActivity(), StoreActivityCart.class);
                                       startActivity(intent);
                                   }
                               });

        //로딩 다이얼로그
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //Grid recyclerview 사용
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        //상품 데이터 저장 - firestore 사용
        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<StoreActivityProduct>();
        storeActivityMyAdapter = new StoreActivityMyAdapter(getActivity(), productArrayList);

        recyclerView.setAdapter(storeActivityMyAdapter);
        EventChangeListener();


        return rootView;
    }
    //firestore에서 상품 데이터 불러오기 (어댑터 = StoreActivityMyAdapter)
    private void EventChangeListener() {
        db.collection("StoreProducts").orderBy("category", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){

                                productArrayList.add(dc.getDocument().toObject(StoreActivityProduct.class));

                            }

                            storeActivityMyAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }

}
