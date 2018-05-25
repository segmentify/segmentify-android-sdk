package com.segmentify.segmentifyandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ListView;
import android.widget.TextView;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.CheckoutModel;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;
import java.util.List;

public class PurchaseSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_success);

        TextView tvPurchase = (TextView) findViewById(R.id.tvTotalPrice);
        final ListView lvBottom = (ListView) findViewById(R.id.lvBottom);

        final String price = getIntent().getStringExtra("purchase");

        double totalPrice = 0.0;

        List<ProductRecommendationModel> productRecommendationModelList = MyApplication.getClientPreferences().getProductRecommendationModelList();
        if(productRecommendationModelList != null) {
            for (ProductRecommendationModel productRecommendationModel : productRecommendationModelList) {
                if(productRecommendationModel.getPrice() != null){
                    totalPrice = productRecommendationModel.getPrice() + totalPrice;
                }
            }
        }
        tvPurchase.setText("Total Price: " + totalPrice);




        SegmentifyManager.INSTANCE.sendPageView("Checkout Success Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> recommendationModels) {
                if(recommendationModels != null){

                }
            }
        });




        ArrayList<ProductModel> productList = new ArrayList<>();

        if(productRecommendationModelList != null) {
            for (ProductRecommendationModel productRecommendationModel : productRecommendationModelList) {

                ProductModel productModel = new ProductModel();
                productModel.setPrice(productRecommendationModel.getPrice());
                productModel.setQuantity(1);
                productModel.setProductId(productRecommendationModel.getProductId());

                productList.add(productModel);
            }
        }


        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setProductList(productList);
        checkoutModel.setTotalPrice(totalPrice);

        SegmentifyManager.INSTANCE.sendPurchase(checkoutModel, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                System.out.println(data);
                ListAdapter segmentifyBottomListAdapter = new ListAdapter(PurchaseSuccessActivity.this,data.get(0).getProducts(),true);
                lvBottom.setAdapter(segmentifyBottomListAdapter);
            }
        });











    }
}
