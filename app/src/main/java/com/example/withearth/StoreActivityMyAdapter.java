package com.example.withearth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoreActivityMyAdapter extends RecyclerView.Adapter<StoreActivityMyAdapter.MyViewHolder> {
    Context context;
    ArrayList<StoreActivityProduct> productArrayList;

    public StoreActivityMyAdapter(Context context, ArrayList<StoreActivityProduct> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public StoreActivityMyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_store_product, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreActivityMyAdapter.MyViewHolder holder, int position) {

        StoreActivityProduct product = productArrayList.get(position);
        holder.name.setText(product.name);
        holder.price.setText(product.price);
        Picasso.get().load(product.getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //상품 클릭시 StoreActivityProductDetails로 이동, intent시 상품 정보 전달
                Intent intent;
                intent = new Intent(context, StoreActivityProductDetails.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("image", product.getImage());
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView category, name, price;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //category = itemView.findViewById(R.id.product_category);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            imageView = itemView.findViewById(R.id.product_image);
        }
    }
}
