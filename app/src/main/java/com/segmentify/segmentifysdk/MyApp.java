package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SegmentifyManager.INSTANCE.config(this,"277ae43c-1ed5-4d64-922d-b4e704de5d2e","https://gandalf-dev.segmentify.com","ihalilaltun-dev.me");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(100000);
        SegmentifyManager.INSTANCE.logStatus(true);
    }
}




