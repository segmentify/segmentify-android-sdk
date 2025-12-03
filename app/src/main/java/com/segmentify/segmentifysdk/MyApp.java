package com.segmentify.segmentifysdk;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.SearchResponseModel;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // if you are using push module make sure to call SegmentifyManager.Instance.setPushConfig first
        SegmentifyManager.INSTANCE.setPushConfig("https://gimli-qa.segmentify.com");
        SegmentifyManager.INSTANCE.config(this, "3c9e211a-d049-43d5-aa4a-f98b7e66e482","https://gandalf-qa.segmentify.com","demosfy.com");
        SegmentifyManager.INSTANCE.setSessionKeepSecond(86400);
        SegmentifyManager.INSTANCE.logStatus(true);
    }
}




