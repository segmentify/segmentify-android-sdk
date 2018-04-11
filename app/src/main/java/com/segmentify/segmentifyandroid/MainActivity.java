package com.segmentify.segmentifyandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SegmentifyManager.INSTANCE.config(this,"","","");
        SegmentifyManager.INSTANCE.sendPageView("ayakkabi", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {

            }
        });

    }
}
