package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SegmentifyManager.INSTANCE.config(this,"3974676b-02e7-4507-bdef-350ef31429fb","https://dce1.segmentify.com","demo.segmentify.com");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(100000);
        SegmentifyManager.INSTANCE.logStatus(true);
        SegmentifyManager.INSTANCE.setPushConfig("https://dce1.segmentify.com");
    }
}




