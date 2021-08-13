package com.example.withearth;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class JJimActivity extends Fragment {
    private View view;
    private ListView jjim_product;
    private JjimAdapter adapter;
    private ArrayList<JjimProduct> products;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_jjim, container, false);



        jjim_product = (ListView) view.findViewById(R.id.jjim_product);
        adapter = new JjimAdapter();
        Resources res = getResources();

        products = new ArrayList<JjimProduct>();
        adapter.addItem(new JjimProduct(res.getDrawable(R.drawable.ic_home), "7,000", "나뭇잎"));

        jjim_product.setAdapter(adapter);

        jjim_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String selectedItem = (String) view.findViewById(R.id.tv_name).getTag().toString();
                //Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }




}
