package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.SearchResponseModel;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // if you are using push module make sure to call SegmentifyManager.Instance.setPushConfig first
        SegmentifyManager.INSTANCE.setPushConfig("https://psh2.segmentify.com");
        SegmentifyManager.INSTANCE.config(this, "92e4211c-33dc-46af-bbff-2b734a92099d","https://per2.segmentify.com","sportive.com.tr");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(86400);
        SegmentifyManager.INSTANCE.logStatus(true);
    }
}




