package com.example.withearth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class JjimAdapterEdit extends RecyclerView.Adapter<JjimAdapterEdit.ViewHolder> {
    private SparseBooleanArray selectedItems = new SparseBooleanArray(0);

    private ArrayList<JjimProduct> jjimProducts;
    private Context context;

    JjimAdapterEdit(Context context, ArrayList<JjimProduct> jjimProducts) {
        this.context = context;
        this.jjimProducts = jjimProducts;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_jjim_product_edit, parent, false);
        JjimAdapterEdit.ViewHolder viewHolder = new JjimAdapterEdit.ViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull @NotNull JjimAdapterEdit.ViewHolder holder, int position) {
        JjimProduct product = jjimProducts.get(position);

        Picasso.get().load(product.getProductImage()).into(holder.iv_image);
        holder.tv_name.setText(product.getName());
        holder.tv_price.setText(product.getPrice());


        if (isItemSelected(position)) {
            holder.itemView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.edge_round_green));
            holder.iv_check.setImageResource(R.drawable.checked_white);
            holder.iv_image.setColorFilter(Color.parseColor("#FFe3ffec"), PorterDuff.Mode.MULTIPLY);
        } else {
            holder.itemView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.edge_round));
            holder.iv_check.setImageResource(R.drawable.ic_edit_unchecked);
            holder.iv_image.setColorFilter(null);
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        ImageView iv_check;
        TextView tv_name;
        TextView tv_price;

        ViewHolder(View view) {
            super(view);

            iv_image = view.findViewById(R.id.iv_image);
            iv_check = view.findViewById(R.id.iv_check);
            tv_name = view.findViewById(R.id.tv_name);
            tv_price = view.findViewById(R.id.tv_price);

            view.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    toggleItemSelected(position);
                }
            });


        }
    }

    private void toggleItemSelected(int position) {

        if (selectedItems.get(position, false) == true) {
            selectedItems.delete(position);
            notifyItemChanged(position);
        } else {
            selectedItems.put(position, true);
            notifyItemChanged(position);
        }
    }


    private boolean isItemSelected(int position) {
        return selectedItems.get(position, false);
    }

    public void setData(ArrayList<JjimProduct> data) {
        this.jjimProducts = data;
        notifyDataSetChanged();
    }

    public JjimProduct removeItem(JjimProduct object) {
        jjimProducts.remove(object);
        notifyDataSetChanged();
        return object;
    }

    public void removeAllItem() {
        jjimProducts.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return jjimProducts.size();
    }

    public int getSelectedCount() {
        return selectedItems.size();
    }

    public SparseBooleanArray getSelectedItems() {
        return selectedItems;
    }

    public void selectAllItem() {
        for (int i = 0; i < jjimProducts.size(); i++) {
            selectedItems.put(i, true);
            notifyItemChanged(i);
        }
    }

    public void clearSelectedItem() {
        int position;

        for (int i = 0; i < selectedItems.size(); i++) {
            position = selectedItems.keyAt(i);
            selectedItems.put(position, false);
            notifyItemChanged(position);
        }
        selectedItems.clear();
    }


}