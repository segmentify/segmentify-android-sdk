package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SegmentifyManager.INSTANCE.config(this,"93a9a7c4-dc2e-49f7-832e-7c7fc3dabbf9","https://dce-test.segmentify.com","ihalilaltun.me");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(100000);
        SegmentifyManager.INSTANCE.logStatus(true);
    }
}




