package com.segmentify.segmentifyandroid;

import android.content.Context;

import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;

import java.util.ArrayList;
import java.util.List;

public class ClientPreferences extends PreferencesManager {

    public ClientPreferences(Context targetContext) {
        super(targetContext);
    }

    public List<ProductRecommendationModel> getProductRecommendationModelList() {
        RecommendationListContainer recommendationListContainer = getObject("ReqProductList", RecommendationListContainer.class);
        if(recommendationListContainer != null){
            return recommendationListContainer.getProductRecommendationModelList();
        }
        else{
            return new ArrayList<>();
        }
    }

    public void setProductRecommendationModelList(List<ProductRecommendationModel> productRecommendationModelList) {
        RecommendationListContainer recommendationListContainer = new RecommendationListContainer();
        recommendationListContainer.setProductRecommendationModelList(productRecommendationModelList);
        putObject("ReqProductList",recommendationListContainer);
    }


}
