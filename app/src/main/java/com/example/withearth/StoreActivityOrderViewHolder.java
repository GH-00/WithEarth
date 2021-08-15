package com.example.withearth;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class StoreActivityOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    public ImageView txtProductImage;

    private ItemClickListner itemClickListner;


    public StoreActivityOrderViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        txtProductName = itemView.findViewById(R.id.order_product_name);
        txtProductPrice = itemView.findViewById(R.id.order_product_price);
        txtProductQuantity = itemView.findViewById(R.id.order_product_quantity);
        txtProductImage = itemView.findViewById(R.id.order_product_image);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(), false);

    }
    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner = itemClickListner;
    }
}
