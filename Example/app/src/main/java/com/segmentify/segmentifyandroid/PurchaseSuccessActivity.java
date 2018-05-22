package com.segmentify.segmentifyandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class PurchaseSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_success);

        TextView tvPurchase = (TextView) findViewById(R.id.tvTotalPrice);
        final ListView lvBottom = (ListView) findViewById(R.id.lvBottom);

        final String price = getIntent().getStringExtra("purchase");
        tvPurchase.setText("Total Price: " + price);

        SegmentifyManager.INSTANCE.sendPageView("Home Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> recommendationModels) {
                if(recommendationModels != null){
                    System.out.println(recommendationModels);
                    ListAdapter segmentifyBottomListAdapter = new ListAdapter(PurchaseSuccessActivity.this,recommendationModels.get(1).getProducts());
                    lvBottom.setAdapter(segmentifyBottomListAdapter);
                }
            }
        });
    }
}
