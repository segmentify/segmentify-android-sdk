package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // if you are using push module make sure to call SegmentifyManager.Instance.setPushConfig first
        SegmentifyManager.INSTANCE.setPushConfig("https://gimli-dev.segmentify.com");
        SegmentifyManager.INSTANCE.config(this, "ae272bfb-214b-4cdd-b5c4-1dddde09e95e","https://gandalf-dev.segmentify.com","ihalilaltun.github.io");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(86400);
        SegmentifyManager.INSTANCE.logStatus(true);
    }
}




