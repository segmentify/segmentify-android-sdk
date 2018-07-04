package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SegmentifyManager.INSTANCE.config(this,"","","");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(100000);
    }
}




