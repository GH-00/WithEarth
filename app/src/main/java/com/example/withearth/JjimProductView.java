package com.example.withearth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class JjimProductView extends LinearLayout {
    ImageView iv_image;
    TextView tv_price;
    TextView tv_name;

    public JjimProductView(Context context) {
        super(context);
        init(context);
    }

    public JjimProductView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_jjim_product,this,true);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_name = (TextView) findViewById(R.id.tv_name);
    }

    public void setImage(Drawable image){
        iv_image.setImageDrawable(image);
    }

    public void setPrice(String price){
        tv_price.setText(price);
    }

    public void setName(String name){
        tv_name.setText(name);

    }
}
