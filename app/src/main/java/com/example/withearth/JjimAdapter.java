package com.example.withearth;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class JjimAdapter extends BaseAdapter {
    private View view;
    private ArrayList<JjimProduct> products = new ArrayList<JjimProduct>();

    public JjimAdapter(){

    }

    public void addItem(JjimProduct product) {
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
        final int POS = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_jjim_product, parent, false);
        }

        ImageView iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);

        JjimProductView productView = new JjimProductView(parent.getContext());
        JjimProduct product = products.get(position);

        productView.setImage(product.getProductImage());
        productView.setName(product.getName());
        productView.setPrice(product.getPrice());


        return convertView;
    }

}