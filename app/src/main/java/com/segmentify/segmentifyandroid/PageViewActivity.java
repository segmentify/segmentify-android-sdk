package com.segmentify.segmentifyandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class PageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);


        SegmentifyManager.INSTANCE.sendPageView("Home Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if(data!=null){
                    ListView lvProducts = findViewById(R.id.lvProducts);
                    SegmentifyListAdapter segmentifyListAdapter = new SegmentifyListAdapter(PageViewActivity.this,data.get(0).getProducts());
                    lvProducts.setAdapter(segmentifyListAdapter);
                }
            }
        });
    }
}
