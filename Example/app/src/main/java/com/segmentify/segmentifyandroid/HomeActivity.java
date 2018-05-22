package com.segmentify.segmentifyandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ItemOnClick onClickListener = new ItemOnClick() {
        @Override
        public void onItemClicked(View v, int position) {

        }
    };
    ArrayList<RecommendationModel> recommendationModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final RecyclerView rvProduct = (RecyclerView) findViewById(R.id.rvProducts);
        final RecyclerView rvBottom = (RecyclerView) findViewById(R.id.rvBottom);

        initListeners();

        SegmentifyManager.INSTANCE.sendPageView("Home Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> recommendationModels) {
                if(recommendationModels != null){
                    System.out.println(recommendationModels);
                    recommendationModelArrayList = recommendationModels;
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(recommendationModelArrayList.get(1).getProducts(),HomeActivity.this,onClickListener);
                    rvProduct.setAdapter(recyclerAdapter);
                    rvProduct.setLayoutManager(new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.VERTICAL,false));
                    BottomRecyclerAdapter bottomRecyclerAdapter = new BottomRecyclerAdapter(recommendationModels.get(0).getProducts(),HomeActivity.this,onClickListener);
                    rvBottom.setAdapter(bottomRecyclerAdapter);
                    rvBottom.setLayoutManager(new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false));
                }
            }
        });
    }


    private void initListeners()
    {
       onClickListener = new ItemOnClick() {
            @Override
            public void onItemClicked(View v, int position) {
                Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                intent.putExtra("productId", recommendationModelArrayList.get(1).getProducts().get(position).getProductId());
                intent.putExtra("name", recommendationModelArrayList.get(1).getProducts().get(position).getName());
                intent.putExtra("price", recommendationModelArrayList.get(1).getProducts().get(position).getPrice().toString());
                intent.putExtra("image", "https:" + recommendationModelArrayList.get(1).getProducts().get(position).getImage());
                startActivity(intent);
            }
        };
    }
}
