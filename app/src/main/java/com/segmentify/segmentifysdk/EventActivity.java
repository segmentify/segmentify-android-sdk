package com.segmentify.segmentifysdk;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.BannerGroupViewModel;
import com.segmentify.segmentifyandroidsdk.model.BannerOperationsModel;
import com.segmentify.segmentifyandroidsdk.model.CheckoutModel;
import com.segmentify.segmentifyandroidsdk.model.InternalBannerModel;
import com.segmentify.segmentifyandroidsdk.model.NotificationModel;
import com.segmentify.segmentifyandroidsdk.model.NotificationType;
import com.segmentify.segmentifyandroidsdk.model.PageModel;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private static final String TAG = "EventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PageModel model = new PageModel();
        model.setCategory("Home Page");
        model.setLang("TR");


        SegmentifyManager.INSTANCE.sendPageView(model, data -> Log.d(TAG, String.valueOf(data)));

        PageModel pageModel = new PageModel();
        pageModel.setCategory("Product Page");
        pageModel.setLang("TR");

        SegmentifyManager.INSTANCE.sendPageView(pageModel, data -> Log.d(TAG, String.valueOf(data)));

        ProductModel productModel = new ProductModel();
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Shop");
        categories.add("Toys");

        productModel.setProductId("30000-1");
        productModel.setTitle("Toys Forky");
        productModel.setUrl("https://demo.segmentify.com/toys-forky/");
        productModel.setImage("https://cdn11.bigcommerce.com/s-5ylnei6or5/images/stencil/500x500/products/1982/5015/2929_Forky_TS4_23_wModel__61743.1559248389.jpg?c=2");
        productModel.setCategories(categories);
        productModel.setPrice(45.75);
        productModel.setLang("TR");

        SegmentifyManager.INSTANCE.sendProductView(productModel, data -> Log.d(TAG, String.valueOf(data)));


        ArrayList<ProductModel> productList = new ArrayList<>();
        ProductModel productPurchaseModel = new ProductModel();
        productPurchaseModel.setPrice(45.75);
        productPurchaseModel.setQuantity(2.3);
        productPurchaseModel.setProductId("30000-1");
        productPurchaseModel.setLang("TR");

        productList.add(productPurchaseModel);

        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setProductList(productList);
        checkoutModel.setTotalPrice(156.0);
        checkoutModel.setLang("TR");

        SegmentifyManager.INSTANCE.sendViewBasket(checkoutModel, data -> Log.d(TAG, String.valueOf(data)));

        ArrayList<ProductModel> checkOutProductList = new ArrayList<>();
        ProductModel checkOutProductModel = new ProductModel();
        checkOutProductModel.setPrice(78.0);
        checkOutProductModel.setQuantity(2.75);
        checkOutProductModel.setProductId("30000-1");
        checkOutProductModel.setLang("TR");

        checkOutProductList.add(productModel);

        CheckoutModel checkoutModel1 = new CheckoutModel();
        checkoutModel1.setProductList(productList);
        checkoutModel1.setTotalPrice(156.0);
        checkoutModel1.setOrderNo("order1");
        checkoutModel1.setLang("TR");

        SegmentifyManager.INSTANCE.sendPurchase(checkoutModel1, data -> Log.d(TAG, String.valueOf(data)));

        BannerOperationsModel bannerImpressionOperationModel = new BannerOperationsModel();
        bannerImpressionOperationModel.setBannerType("impression");
        bannerImpressionOperationModel.setTitle("New Season Men Shoes");
        bannerImpressionOperationModel.setGroup("Home Page Slider");
        bannerImpressionOperationModel.setOrder(1);
        bannerImpressionOperationModel.setLang("TR");
        SegmentifyManager.INSTANCE.sendBannerImpressionEvent(bannerImpressionOperationModel);

        BannerOperationsModel bannerClickOperationModel = new BannerOperationsModel();
        bannerClickOperationModel.setBannerType("click");
        bannerClickOperationModel.setTitle("New Season Women Shoes");
        bannerClickOperationModel.setGroup("Home Page Slider");
        bannerClickOperationModel.setOrder(2);
        bannerClickOperationModel.setLang("TR");
        SegmentifyManager.INSTANCE.sendBannerClickEvent(bannerClickOperationModel);

        BannerOperationsModel bannerUpdateOperationModel = new BannerOperationsModel();
        bannerUpdateOperationModel.setBannerType("update");
        bannerUpdateOperationModel.setTitle("New Season Women Shoes");
        bannerUpdateOperationModel.setGroup("Home Page Slider");
        bannerUpdateOperationModel.setOrder(3);
        bannerUpdateOperationModel.setLang("TR");
        SegmentifyManager.INSTANCE.sendBannerUpdateEvent(bannerUpdateOperationModel);

        BannerGroupViewModel bannerGroupViewModel = new BannerGroupViewModel();
        bannerGroupViewModel.setGroup("Home Page Slider");
        ArrayList<InternalBannerModel> internalBannerModels = new ArrayList<>();
        InternalBannerModel internalBannerModel = new InternalBannerModel();
        internalBannerModel.setTitle("Gorgeous Duo T-Shirt & Trousers");
        internalBannerModel.setOrder(1);
        internalBannerModel.setImage("https://demo.segmentify.com/gorgeous-duo-tshirt-trousers.jpg");
        internalBannerModel.setLang("TR");
        internalBannerModel.setUrls(new ArrayList<>(List.of("https://demo.segmentify.com/gorgeous-duo-tshirt-trousers")));
        internalBannerModels.add(internalBannerModel);

        internalBannerModel = new InternalBannerModel();
        internalBannerModel.setTitle("Ready to Renew");
        internalBannerModel.setOrder(2);
        internalBannerModel.setImage("https://demo.segmentify.com/ready-to-renew.jpg");
        internalBannerModel.setLang("TR");
        internalBannerModel.setUrls(new ArrayList<>(List.of("https://demo.segmentify.com/ready-to-renew")));
        internalBannerModels.add(internalBannerModel);

        SegmentifyManager.INSTANCE.sendBannerGroupViewEvent(bannerGroupViewModel);


        Button subscribeButton = findViewById(R.id.button);
        subscribeButton.setOnClickListener(v -> {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        NotificationModel nModel = new NotificationModel();
                        nModel.setDeviceToken(token);
                        nModel.setType(NotificationType.PERMISSION_INFO);
                        SegmentifyManager.INSTANCE.sendNotification(nModel);
                        // Log and toast
                        String msg = "FCM Registration token: " + token;
                        Log.d(TAG, msg);
                    });
            // [END log_reg_token]
        });
    }

    // [START ask_post_notifications]
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });
}