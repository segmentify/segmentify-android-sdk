package com.segmentify.segmentifysdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.SearchPageModel;
import com.segmentify.segmentifyandroidsdk.model.SearchResponseModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchPageModel searchPage = new SearchPageModel();
        searchPage.setQuery("");
        searchPage.setLang("EN");
        SegmentifyManager.INSTANCE.sendSearchPageView(searchPage, new SegmentifyCallback<SearchResponseModel>() {
                    @Override
                    public void onDataLoaded(SearchResponseModel data) {
                        if(data!=null){
                            System.out.println(data);
                        }
                    }
                });

        SegmentifyManager.INSTANCE.sendSearchClickView("product","21046");

        /*PageModel model = new PageModel();
        model.setCategory("Home Page");


        SegmentifyManager.INSTANCE.sendPageView(model, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                if(data!=null){
                    System.out.println(data);
                }
            }
        });

        ArrayList<ProductModel> productList = new ArrayList<>();
        ProductModel productModel = new ProductModel();
        productModel.setPrice(78.0);
        productModel.setQuantity(2);
        productModel.setProductId("25799809929");

        productList.add(productModel);

        CheckoutModel checkoutModel = new CheckoutModel();
        checkoutModel.setProductList(productList);
        checkoutModel.setTotalPrice(156.0);

        SegmentifyManager.INSTANCE.sendViewBasket(checkoutModel, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {

            }
        });




        Button subscribeButton = findViewById(R.id.button);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NotificationModel model = new NotificationModel();
                model.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
                model.setType(NotificationType.PERMISSION_INFO);
                SegmentifyManager.INSTANCE.sendNotification(model);


            }
        });
*/

    }
}