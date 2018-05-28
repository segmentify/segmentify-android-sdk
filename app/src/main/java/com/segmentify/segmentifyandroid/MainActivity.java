package com.segmentify.segmentifyandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.BasketModel;
import com.segmentify.segmentifyandroidsdk.model.CheckoutModel;
import com.segmentify.segmentifyandroidsdk.model.InteractionModel;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.model.UserModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPurchase = findViewById(R.id.btnPurchase);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnPageView = findViewById(R.id.btnPageView);
        btnPurchase.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnPageView.setOnClickListener(this);
        SegmentifyManager.INSTANCE.logStatus(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnPurchase:
                sendPaymentInfo();
                break;
            case R.id.btnPageView:
                sendPageView();
                break;
        }
    }

    private void login(){
        UserModel userModel = new UserModel();
        userModel.setUsername("deneme");
        userModel.setEmail("a@a.com");

        SegmentifyManager.INSTANCE.sendUserLogin(userModel);

        Segment

//
//        InteractionModel model = new InteractionModel();
//        model.setImpressionId("ada");
//        model.setInstanceId("ada");
//        model.setType("impression");
//
//        SegmentifyManager.INSTANCE.sendWidgetView("ads","asda");


//        BasketModel aa = new BasketModel();
//        aa.setPrice(775.0);
//        aa.setQuantity(1);
//        aa.setProductId("25800412873");
//        aa.setStep("add");
//
//        SegmentifyManager.INSTANCE.sendAddOrRemoveBasket(aa);


    }

    private void sendPaymentInfo(){
        ArrayList<ProductModel> productList = new ArrayList<>();
        ProductModel productModel = new ProductModel();
        productModel.setPrice(775.0);
        productModel.setQuantity(1);
        productModel.setProductId("25800412873");


        ProductModel productModel2 = new ProductModel();
        productModel2.setPrice(678.0);
        productModel2.setQuantity(1);
        productModel2.setProductId("25801129801");

        productList.add(productModel);
        productList.add(productModel2);

        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setProductList(productList);
        checkoutModel.setTotalPrice(1453.0);



        SegmentifyManager.INSTANCE.sendViewBasket(checkoutModel, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if (data != null){
                    Toast.makeText(MainActivity.this,"Oldu",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendPageView(){
        startActivity(new Intent(this,PageViewActivity.class));
    }
}
