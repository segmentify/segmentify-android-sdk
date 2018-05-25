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
    ItemOnClick onClickListener = new ItemOnClick() {
        @Override
        public void onItemClicked(View v, int position) {
            Intent intent = new Intent(activity, ProductDetailActivity.class);
            intent.putExtra("id", productRecommendationModelArrayList.get(position).getProductId());
            intent.putExtra("name", productRecommendationModelArrayList.get(position).getName());
            intent.putExtra("price", productRecommendationModelArrayList.get(position).getPrice() + "");
            intent.putExtra("image", "https:" + productRecommendationModelArrayList.get(position).getImage());
            activity.startActivity(intent);
        }
    };

    public BottomRecyclerAdapter(ArrayList<ProductRecommendationModel> productRecommendationModelArrayList, Activity activity, ItemOnClick onClickListener) {
        this.productRecommendationModelArrayList = productRecommendationModelArrayList;
        this.activity = activity;
        //this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public BottomRecyclerAdapter.BottomProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_bottom_list,parent,false);
        return new BottomRecyclerAdapter.BottomProductViewHolder(v,onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomRecyclerAdapter.BottomProductViewHolder holder, final int position) {

        String fix_image ="";

        if(productRecommendationModelArrayList.get(position).getImage().startsWith("https:https://"))
        {
            fix_image =    productRecommendationModelArrayList.get(position).getImage().replace("https:https://","https://");
        }
        else if(productRecommendationModelArrayList.get(position).getImage().startsWith("//"))
        {
            fix_image = "https:" +  productRecommendationModelArrayList.get(position).getImage();
        }
        else if(productRecommendationModelArrayList.get(position).getImage().startsWith("https://"))
        {
            fix_image = productRecommendationModelArrayList.get(position).getImage();
        }


        Picasso.get().load(fix_image).into(holder.ivProduct);
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
