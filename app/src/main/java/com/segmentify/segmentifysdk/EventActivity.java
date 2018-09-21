package com.segmentify.segmentifysdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.PageModel;
import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PageModel model = new PageModel();
        model.setCategory("Home Page");

        SegmentifyManager.INSTANCE.sendPageView(model, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if(data!=null){
                    System.out.println(data);
                }
            }
        });
    }
}