package com.segmentify.segmentifyandroid;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;

public class SegmentifyDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SegmentifyManager.INSTANCE.config(this,"8157d334-f8c9-4656-a6a4-afc8b1846e4c","https://dce1.segmentify.com","segmentify-shop.myshopify.com");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(100000);
    }
}
