package com.segmentify.segmentifyandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ListView lvProducts = findViewById(R.id.lvProducts);
        final ListView lvBottom = findViewById(R.id.lvBottom);

        SegmentifyManager.INSTANCE.sendPageView("Home Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> recommendationModels) {
                if(recommendationModels != null){
                    System.out.println(recommendationModels);
                    ListAdapter segmentifyListAdapter = new ListAdapter(HomeActivity.this,recommendationModels.get(1).getProducts());
                    lvProducts.setAdapter(segmentifyListAdapter);
//                    ListAdapter segmentifyBottomListAdapter = new ListAdapter(HomeActivity.this,recommendationModels.get(0).getProducts());
//                    lvBottom.setAdapter(segmentifyBottomListAdapter);
                }
            }
        });
    }
}
