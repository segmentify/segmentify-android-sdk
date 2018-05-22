package com.segmentify.segmentifyandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProductViewHolder> {

    ArrayList<ProductRecommendationModel> productRecommendationModelArrayList;
    Activity activity;
    ImageView ivProduct;
    Button btnAdd, btnDetail;
    TextView tvProductname, tvPrice;
    ItemOnClick onClickListener;

    public RecyclerAdapter(ArrayList<ProductRecommendationModel> productRecommendationModelArrayList, Activity activity, ItemOnClick onClickListener) {
        this.productRecommendationModelArrayList = productRecommendationModelArrayList;
        this.activity = activity;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_list,parent,false);
        return new ProductViewHolder(v,onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {

        Picasso.get().load("https:" + productRecommendationModelArrayList.get(position).getImage()).into(holder.ivProduct);
        holder.tvProductname.setText(productRecommendationModelArrayList.get(position).getName());
        holder.tvPrice.setText(productRecommendationModelArrayList.get(position).getPrice() + " TL");
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, BasketDetailActivity.class);
                intent.putExtra("productId",productRecommendationModelArrayList.get(position).getProductId());
                intent.putExtra("name",productRecommendationModelArrayList.get(position).getName());
                intent.putExtra("price",productRecommendationModelArrayList.get(position).getPrice());
                intent.putExtra("image","https:" + productRecommendationModelArrayList.get(position).getImage());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productRecommendationModelArrayList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProduct = itemView.findViewById(R.id.imgProduct);
        Button btnAdd = itemView.findViewById(R.id.btnAdd);
        Button btnDetail = itemView.findViewById(R.id.btnDetail);
        TextView tvProductname = itemView.findViewById(R.id.tvProductName);
        TextView tvPrice = itemView.findViewById(R.id.tvPrice);

        public ProductViewHolder(final View itemView, final ItemOnClick onClickListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClicked(itemView,getAdapterPosition());
                }
            });
        }
    }

}
