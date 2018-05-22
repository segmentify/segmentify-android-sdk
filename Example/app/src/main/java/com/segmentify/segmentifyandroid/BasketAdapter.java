package com.segmentify.segmentifyandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class BasketAdapter extends BaseAdapter{
    String[] idsArr;
    String[] namesArr;
    String[] priceArr;
    String[] imagesArr;
    Context context;
    ImageView ivProduct;

    public BasketAdapter(String[] idsArr,String[] namesArr, String[] priceArr, String[] imagesArr, Context context) {
        this.idsArr = idsArr;
        this.namesArr = namesArr;
        this.priceArr = priceArr;
        this.imagesArr = imagesArr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return namesArr.length;
    }

    @Override
    public Object getItem(int position) {
        return namesArr[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_basket,null) ;
        }

        ivProduct = (ImageView) view.findViewById(R.id.imgProduct);
        TextView tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        TextView tvProductPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvProductName.setText(namesArr[position]);
        tvProductPrice.setText(priceArr[position]);
        Picasso.get().load(imagesArr[position]).into(ivProduct);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", idsArr[position]);
                intent.putExtra("name", namesArr[position]);
                intent.putExtra("price", priceArr[position]);
                intent.putExtra("image", imagesArr[position]);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
