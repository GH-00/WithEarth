package com.example.withearth;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class JjimAdapter extends RecyclerView.Adapter<JjimAdapter.ViewHolder> {
    private ArrayList<JjimProduct> jjimProducts;
    private Context context;

    JjimAdapter(Context context, ArrayList<JjimProduct> jjimProducts) {
        this.context = context;
        this.jjimProducts = jjimProducts;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_jjim_product, parent, false);
        JjimAdapter.ViewHolder viewHolder = new JjimAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JjimAdapter.ViewHolder holder, int position) {
        JjimProduct product = jjimProducts.get(position);

        Picasso.get().load(product.getProductImage()).into(holder.iv_image);
        holder.tv_name.setText(product.getName());
        holder.tv_price.setText(product.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoreActivityProductDetails.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image", product.getProductImage());
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return jjimProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_name;
        TextView tv_price;

        ViewHolder(View view) {
            super(view);

            iv_image = view.findViewById(R.id.iv_image);
            tv_name = view.findViewById(R.id.tv_name);
            tv_price = view.findViewById(R.id.tv_price);

        }
    }
}