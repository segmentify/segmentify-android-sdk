package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // if you are using push module make sure to call SegmentifyManager.Instance.setPushConfig first
        SegmentifyManager.INSTANCE.setPushConfig("https://gimli-qa.segmentify.com");
        SegmentifyManager.INSTANCE.config(this, "e4dfb71f-eac8-44b7-8938-ce9cce910cb2","https://gandalf-qa.segmentify.com","segmentify-search-showcase.mybigcommerce.com");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(86400);
        SegmentifyManager.INSTANCE.logStatus(true);
    }
}




