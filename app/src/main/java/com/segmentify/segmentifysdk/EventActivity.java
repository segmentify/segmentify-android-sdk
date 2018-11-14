package com.segmentify.segmentifysdk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.NotificationModel;
import com.segmentify.segmentifyandroidsdk.model.NotificationType;
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






        Button subscribeButton = findViewById(R.id.button);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            NotificationModel model = new NotificationModel();
            model.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
            model.setType(NotificationType.PERMISSION_INFO);
            SegmentifyManager.INSTANCE.sendNotification(model);


            }
        });


    }
}