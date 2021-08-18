package com.example.withearth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.withearth.R.color.design_default_color_primary_dark;


import org.jetbrains.annotations.NotNull;

public class HomeActivity extends Fragment {

    private View view;
    private ImageButton btn;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth;

    private ImageView tree1;



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_home, container, false);

        btn = (ImageButton) view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomePointInfo.class);
                startActivity(intent);
            }
        });

        //포인트 출력하기
        TextView point = view.findViewById(R.id.point);
        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseReference = database.getReference("Point").child(mFirebaseAuth.getCurrentUser().getUid());
        databaseReference.child("point").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String point_show = snapshot.getValue(String.class);
                point.setText(point_show);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });

        //나무
        tree1 = (ImageView) view.findViewById(R.id.tree1);


        return view;
    }


    //툴바 생성
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_base_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(getActivity(), StoreActivityCart.class);
                startActivity(intent);
                return true;

            default :
                return super.onOptionsItemSelected(item) ;
        }
    }
}