package com.example.withearth;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class JJimActivity extends Fragment {
    private View view;
    private ListView jjim_product;
    ArrayList<JjimProduct> products;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_jjim, container, false);

        jjim_product = (ListView) view.findViewById(R.id.jjim_product);
        /*JjimAdapter adapter = new JjimAdapter();
        Resources res = getResources();

        products = new ArrayList<JjimProduct>();
        products.add(new JjimProduct(res.getDrawable(R.drawable.ic_environment) ,"7,000","나뭇잎"));

        jjim_product.setAdapter(adapter);*/

        return view;
    }


    public class JjimAdapter extends BaseAdapter {

        public void addItem(JjimProduct product){
            products.add(product);
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            JjimProductView productView = new JjimProductView(getContext());
            JjimProduct product = products.get(position);

            productView.setImage(product.getProductImage());
            productView.setName(product.getName());
            productView.setPrice(product.getPrice());

            return view;
        }
    }

}
