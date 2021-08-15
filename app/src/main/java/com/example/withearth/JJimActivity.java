package com.example.withearth;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class JJimActivity extends Fragment {
    private View view;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth auth;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_jjim, container, false);

        ArrayList<JjimProduct> products = new ArrayList<JjimProduct>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.jjim_product);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);

        JjimAdapter adapter = new JjimAdapter(getContext(), products);
        recyclerView.setAdapter(adapter);
        //auth.getCurrentUser().getUid()

        databaseReference = database.getReference("Jjim Menu").child("User1").child("Jjim Lists");
        Query sortbyTime = databaseReference.orderByChild("time");

        sortbyTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    JjimFirebasePost value = postSnapshot.getValue(JjimFirebasePost.class);
                    products.add(0, new JjimProduct(value.getImage(), value.getPrice(), value.getName()));
                }
                //찜 상품 수 표시
                TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
                String count = String.valueOf(adapter.getItemCount());
                tv_count.setText(count);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w("getFirebaseDatabase", "Failed to read value", error.toException());
            }
        });

        //최상단 이동 버튼
        FloatingActionButton btn_up = (FloatingActionButton) view.findViewById(R.id.btn_up);
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_jjim_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_cart:
                intent = new Intent(getActivity(), StoreActivityCart.class);
                startActivity(intent);
                break;

            case R.id.action_crop:
                intent = new Intent(view.getContext(), JjimActivityEdit.class);
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

}
