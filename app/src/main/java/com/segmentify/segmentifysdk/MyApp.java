package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // if you are using push module make sure to call SegmentifyManager.Instance.setPushConfig first
        SegmentifyManager.INSTANCE.setPushConfig("https://gimli-test.segmentify.com");
        SegmentifyManager.INSTANCE.config(this, "603a135e-9b7d-483f-a40d-7cbcf8f31e37","https://gandalf-test.segmentify.com","demo-test.segmentify.com");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(100000);
        SegmentifyManager.INSTANCE.logStatus(true);
    }
}




