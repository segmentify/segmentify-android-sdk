package com.segmentify.segmentifyandroid;

import android.app.Application;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;

import java.util.ArrayList;

public class MyApplication extends Application {


    private static ClientPreferences clientPreferences;

    public static ClientPreferences getClientPreferences() {
        return clientPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        clientPreferences = new ClientPreferences(this);
        clientPreferences.setProductRecommendationModelList(new ArrayList<ProductRecommendationModel>());
        SegmentifyManager.INSTANCE.config(this,"8157d334-f8c9-4656-a6a4-afc8b1846e4c","https://dce1.segmentify.com","segmentify-shop.myshopify.com");

    }
}