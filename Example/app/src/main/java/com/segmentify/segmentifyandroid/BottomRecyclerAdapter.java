package com.segmentify.segmentifyandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BottomRecyclerAdapter  extends RecyclerView.Adapter<BottomRecyclerAdapter.BottomProductViewHolder> {

    ArrayList<ProductRecommendationModel> productRecommendationModelArrayList;
    Activity activity;
    ImageView ivProduct;
    TextView tvProductname, tvPrice;
    ItemOnClick onClickListener;

    public BottomRecyclerAdapter(ArrayList<ProductRecommendationModel> productRecommendationModelArrayList, Activity activity, ItemOnClick onClickListener) {
        this.productRecommendationModelArrayList = productRecommendationModelArrayList;
        this.activity = activity;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public BottomRecyclerAdapter.BottomProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_bottom_list,parent,false);
        return new BottomRecyclerAdapter.BottomProductViewHolder(v,onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomRecyclerAdapter.BottomProductViewHolder holder, final int position) {

        Picasso.get().load("https:" + productRecommendationModelArrayList.get(position).getImage()).into(holder.ivProduct);
        holder.tvProductname.setText(productRecommendationModelArrayList.get(position).getName());
        holder.tvPrice.setText(productRecommendationModelArrayList.get(position).getPrice() + " TL");
    }

    @Override
    public int getItemCount() {
        return productRecommendationModelArrayList.size();
    }

    static class BottomProductViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProduct = itemView.findViewById(R.id.imgProduct);
        TextView tvProductname = itemView.findViewById(R.id.tvProductName);
        TextView tvPrice = itemView.findViewById(R.id.tvPrice);

        public BottomProductViewHolder(final View itemView, final ItemOnClick onClickListener) {
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
