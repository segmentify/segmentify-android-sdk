package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SegmentifyManager.INSTANCE.config(this,"8f6b9ae8-7d0e-455d-be6d-bdf7b74efcf7","https://gandalf-dev.segmentify.com","ihalilaltun-dev.me");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(100000);
        SegmentifyManager.INSTANCE.logStatus(true);
    }
}




