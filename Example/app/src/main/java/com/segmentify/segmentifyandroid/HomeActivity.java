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
import android.widget.TextView;
import android.widget.Toast;

import com.segmentify.segmentifyandroidsdk.model.BasketModel;
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
            switch (v.getId()){
                case R.id.rvProducts:

                break;
            }

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
                    TextView tv =(TextView)findViewById(R.id.tvHome);
                    tv.setText(recommendationModels.get(0).getNotificationTitle());
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
                intent.putExtra("image",    recommendationModelArrayList.get(1).getProducts().get(position).getImage());
                intent.putExtra("url", "https:" + recommendationModelArrayList.get(1).getProducts().get(position).getUrl());


                System.out.println(recommendationModelArrayList.get(1).getProducts().get(position).getProductId());

                SegmentifyManager.INSTANCE.sendWidgetView(recommendationModelArrayList.get(1).getInstanceId(),recommendationModelArrayList.get(1).getActionId());
                SegmentifyManager.INSTANCE.sendClickView(recommendationModelArrayList.get(1).getInstanceId(),recommendationModelArrayList.get(1).getProducts().get(position).getProductId());


                BasketModel model = new BasketModel();
                model.setStep("add");
                model.setProductId(recommendationModelArrayList.get(1).getProducts().get(position).getProductId());
                model.setQuantity(1);
                model.setPrice(Double.parseDouble(recommendationModelArrayList.get(1).getProducts().get(position).getPrice().toString()));
                SegmentifyManager.INSTANCE.sendAddOrRemoveBasket(model);


                startActivity(intent);
            }
        };
    }
}
