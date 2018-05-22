package com.segmentify.segmentifyandroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;

import java.util.ArrayList;
import java.util.List;

public class RecommendationListContainer implements Parcelable {

    public List<ProductRecommendationModel> getProductRecommendationModelList() {
        return productRecommendationModelList;
    }

    public void setProductRecommendationModelList(List<ProductRecommendationModel> productRecommendationModelList) {
        this.productRecommendationModelList = productRecommendationModelList;
    }

    List<ProductRecommendationModel> productRecommendationModelList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.productRecommendationModelList);
    }

    public RecommendationListContainer() {
    }

    protected RecommendationListContainer(Parcel in) {
        this.productRecommendationModelList = new ArrayList<ProductRecommendationModel>();
        in.readList(this.productRecommendationModelList, ProductRecommendationModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<RecommendationListContainer> CREATOR = new Parcelable.Creator<RecommendationListContainer>() {
        @Override
        public RecommendationListContainer createFromParcel(Parcel source) {
            return new RecommendationListContainer(source);
        }

        @Override
        public RecommendationListContainer[] newArray(int size) {
            return new RecommendationListContainer[size];
        }
    };
}
