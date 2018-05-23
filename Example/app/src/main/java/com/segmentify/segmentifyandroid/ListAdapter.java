package com.segmentify.segmentifyandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{

    ArrayList<ProductRecommendationModel> productRecommendationModelArrayList;
    Activity activity;
    ImageView ivProduct;
    Button btnAdd, btnDetail;
    SharedPreferences sharedPref;
    boolean isAfterPurchase;


    public ListAdapter(Activity activity, ArrayList<ProductRecommendationModel> productRecommendationModelArrayList, boolean isAfterPurchase) {

        this.activity = activity;
        this.productRecommendationModelArrayList = productRecommendationModelArrayList;
        this.isAfterPurchase = isAfterPurchase;

    }

    public int getCount() {
        return productRecommendationModelArrayList.size();
    }

    public Object getItem(int arg0) {
        return productRecommendationModelArrayList.get(arg0);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = activity.getLayoutInflater();
        View row;
        row = inflater.inflate(R.layout.item_list, parent, false);
        TextView tvProductname, tvPrice;
        tvProductname = (TextView) row.findViewById(R.id.tvProductName);
        tvPrice = (TextView) row.findViewById(R.id.tvPrice);
        ivProduct=(ImageView)row.findViewById(R.id.imgProduct);
        btnAdd = row.findViewById(R.id.btnAdd);
        btnDetail = row.findViewById(R.id.btnDetail);
        Picasso.get().load("https:" + productRecommendationModelArrayList.get(position).getImage()).into(ivProduct);
        tvProductname.setText(productRecommendationModelArrayList.get(position).getName());
        tvPrice.setText(productRecommendationModelArrayList.get(position).getPrice() + " TL");

        if(isAfterPurchase){
            btnAdd.setVisibility(View.GONE);
        }
        else{
            btnAdd.setVisibility(View.VISIBLE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, BasketDetailActivity.class);
                intent.putExtra("productId", productRecommendationModelArrayList.get(position).getProductId());
                intent.putExtra("name", productRecommendationModelArrayList.get(position).getName());
                intent.putExtra("price", productRecommendationModelArrayList.get(position).getPrice().toString());
                intent.putExtra("image", "https:" + productRecommendationModelArrayList.get(position).getImage());
                activity.startActivity(intent);
            }
        });

        /*btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra("productId", productRecommendationModelArrayList.get(position).getProductId());
                intent.putExtra("name", productRecommendationModelArrayList.get(position).getName());
                intent.putExtra("price", productRecommendationModelArrayList.get(position).getPrice().toString());
                intent.putExtra("image", "https:" + productRecommendationModelArrayList.get(position).getImage());
                activity.startActivity(intent);
            }
        });

        return (row);
    }

}
