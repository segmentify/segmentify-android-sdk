package com.segmentify.segmentifysdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//import com.google.firebase.iid.FirebaseInstanceId;
import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.BannerGroupViewModel;
import com.segmentify.segmentifyandroidsdk.model.BannerOperationsModel;
import com.segmentify.segmentifyandroidsdk.model.CheckoutModel;
import com.segmentify.segmentifyandroidsdk.model.InternalBannerModel;
import com.segmentify.segmentifyandroidsdk.model.NotificationModel;
import com.segmentify.segmentifyandroidsdk.model.NotificationType;
import com.segmentify.segmentifyandroidsdk.model.PageModel;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;
import java.util.Arrays;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PageModel model = new PageModel();
        model.setCategory("Home Page");


        SegmentifyManager.INSTANCE.sendPageView(model, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if (data != null) {
                    System.out.println(data);
                }
            }
        });

        PageModel pageModel = new PageModel();
        pageModel.setCategory("Product Page");
        pageModel.setLang("EN");
        pageModel.setRegion("EU");

        SegmentifyManager.INSTANCE.sendPageView(pageModel, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if (data != null) {
                    System.out.println(data);
                }
            }
        });

        ProductModel productModel = new ProductModel();
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Shop");
        categories.add("Toys");

        productModel.setProductId("30000-1");
        productModel.setTitle("Toys Forky");
        productModel.setUrl("https://ihalilaltun.me/toys-forky/");
        productModel.setImage("https://cdn11.bigcommerce.com/s-5ylnei6or5/images/stencil/500x500/products/1982/5015/2929_Forky_TS4_23_wModel__61743.1559248389.jpg?c=2");
        productModel.setCategories(categories);
        productModel.setPrice(45.75);

        SegmentifyManager.INSTANCE.sendProductView(productModel, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if (data != null) {
                    System.out.println(data);
                }
            }
        });


        ArrayList<ProductModel> productList = new ArrayList<>();
        ProductModel productPurchaseModel = new ProductModel();
        productPurchaseModel.setPrice(45.75);
        productPurchaseModel.setQuantity(2.3);
        productPurchaseModel.setProductId("30000-1");

        productList.add(productPurchaseModel);

        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setProductList(productList);
        checkoutModel.setTotalPrice(156.0);

        SegmentifyManager.INSTANCE.sendViewBasket(checkoutModel, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if (data != null) {
                    System.out.println(data);
                }
            }
        });

        SegmentifyManager.INSTANCE.sendPageView("Checkout Success Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if (data != null) {
                    System.out.println(data);
                }
            }
        });

        ArrayList<ProductModel> checkOutProductList = new ArrayList<>();
        ProductModel checkOutProductModel = new ProductModel();
        checkOutProductModel.setPrice(78.0);
        checkOutProductModel.setQuantity(2.75);
        checkOutProductModel.setProductId("30000-1");

        checkOutProductList.add(productModel);

        CheckoutModel checkoutModel1 = new CheckoutModel();
        checkoutModel1.setProductList(productList);
        checkoutModel1.setTotalPrice(156.0);
        checkoutModel1.setOrderNo("order1");

        SegmentifyManager.INSTANCE.sendPurchase(checkoutModel1, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if (data != null) {
                    System.out.println(data);
                }
            }
        });

        BannerOperationsModel bannerImpressionOperationModel = new BannerOperationsModel();
        bannerImpressionOperationModel.setBannerType("impression");
        bannerImpressionOperationModel.setTitle("New Season Men Shoes");
        bannerImpressionOperationModel.setGroup("Home Page Slider");
        bannerImpressionOperationModel.setOrder(1);
        SegmentifyManager.INSTANCE.sendBannerImpressionEvent(bannerImpressionOperationModel);

        BannerOperationsModel bannerClickOperationModel = new BannerOperationsModel();
        bannerClickOperationModel.setBannerType("click");
        bannerClickOperationModel.setTitle("New Season Women Shoes");
        bannerClickOperationModel.setGroup("Home Page Slider");
        bannerClickOperationModel.setOrder(2);
        SegmentifyManager.INSTANCE.sendBannerClickEvent(bannerClickOperationModel);

        BannerOperationsModel bannerUpdateOperationModel = new BannerOperationsModel();
        bannerUpdateOperationModel.setBannerType("update");
        bannerUpdateOperationModel.setTitle("New Season Women Shoes");
        bannerUpdateOperationModel.setGroup("Home Page Slider");
        bannerUpdateOperationModel.setOrder(3);
        SegmentifyManager.INSTANCE.sendBannerUpdateEvent(bannerUpdateOperationModel);

        BannerGroupViewModel bannerGroupViewModel = new BannerGroupViewModel();
        bannerGroupViewModel.setGroup("Home Page Slider");
        ArrayList<InternalBannerModel> internalBannerModels = new ArrayList<>();
        InternalBannerModel internalBannerModel = new InternalBannerModel();
        internalBannerModel.setTitle("Gorgeous Duo T-Shirt & Trousers");
        internalBannerModel.setOrder(1);
        internalBannerModel.setImage("https://www.example.com/gorgeous-duo-tshirt-trousers.jpg");
        internalBannerModel.setUrls(new ArrayList<>(Arrays.asList("https://www.example.com/gorgeous-duo-tshirt-trousers")));
        internalBannerModels.add(internalBannerModel);

        internalBannerModel = new InternalBannerModel();
        internalBannerModel.setTitle("Ready to Renew");
        internalBannerModel.setOrder(2);
        internalBannerModel.setImage("https://www.example.com/ready-to-renew.jpg");
        internalBannerModel.setUrls(new ArrayList<>(Arrays.asList("https://www.example.com/ready-to-renew")));
        internalBannerModels.add(internalBannerModel);

        SegmentifyManager.INSTANCE.sendBannerGroupViewEvent(bannerGroupViewModel);



        Button subscribeButton = findViewById(R.id.button);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageModel model = new PageModel();
                model.setCategory("Home Page");


                SegmentifyManager.INSTANCE.sendPageView(model, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
                    @Override
                    public void onDataLoaded(ArrayList<RecommendationModel> data) {
                        if (data != null) {
                            System.out.println(data);
                        }
                    }
                });

                //NotificationModel nModel = new NotificationModel();
                //nModel.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
                //nModel.setType(NotificationType.PERMISSION_INFO);
                //SegmentifyManager.INSTANCE.sendNotification(nModel);
            }
        });
    }
}