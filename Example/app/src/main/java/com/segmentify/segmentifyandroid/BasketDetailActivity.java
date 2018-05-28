package com.segmentify.segmentifyandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.BasketModel;
import com.segmentify.segmentifyandroidsdk.model.CheckoutModel;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;
import java.util.List;

public class BasketDetailActivity extends AppCompatActivity {

    TextView tvProductName, tvPrice, tvBasketAmount;
    ImageView ivProduct;
    Button btnPurchase;

    String productId;
    String name;
    String price;
    String image;
    double totalPrice = 0.0;

    ItemOnClick onClickListener = new ItemOnClick() {
        @Override
        public void onItemClicked(View v, int position) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_detail);

        //productId = getIntent().getStringExtra("id");
        //name = getIntent().getStringExtra("name");
        //price = getIntent().getStringExtra("price");
        //image = getIntent().getStringExtra("image");



        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvBasketAmount = (TextView) findViewById(R.id.tvBasketAmount);
        ivProduct = (ImageView) findViewById(R.id.imgProduct);
        btnPurchase = (Button) findViewById(R.id.btnPurchase);

        final ListView lvBasket = findViewById(R.id.lvBasket);
        final RecyclerView rvBottom = findViewById(R.id.rvBottom);


        BasketAdapter BasketAdapter = new BasketAdapter(BasketDetailActivity.this);
        lvBasket.setAdapter(BasketAdapter);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasketDetailActivity.this, PurchaseSuccessActivity.class);
                intent.putExtra("purchase",price);
                startActivity(intent);
            }
        });

        lvBasket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BasketDetailActivity.this, ProductDetailActivity.class);
                intent.putExtra("id", productId);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("image", image);



                BasketModel model = new BasketModel();
                model.setStep("add");
                model.setProductId(productId);
                model.setQuantity(1);
                model.setPrice(Double.parseDouble((price)));
                SegmentifyManager.INSTANCE.sendAddOrRemoveBasket(model);


                startActivity(intent);
            }
        });


        List<ProductRecommendationModel> productRecommendationModelList = MyApplication.getClientPreferences().getProductRecommendationModelList();
        if(productRecommendationModelList != null) {
            for (ProductRecommendationModel productRecommendationModel : productRecommendationModelList) {
                if(productRecommendationModel.getPrice() != null){
                    totalPrice = productRecommendationModel.getPrice() + totalPrice;
                }
            }
        }



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

        SegmentifyManager.INSTANCE.sendViewBasket(checkoutModel, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                BottomRecyclerAdapter bottomRecyclerAdapter = new BottomRecyclerAdapter(data.get(0).getProducts(),BasketDetailActivity.this,onClickListener);

                TextView tv =(TextView)findViewById(R.id.tvBasket);
                tv.setText(data.get(0).getNotificationTitle());
                rvBottom.setAdapter(bottomRecyclerAdapter);
                rvBottom.setLayoutManager(new LinearLayoutManager(BasketDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
            }
        });



        tvBasketAmount.setText("Total amount of items: " + totalPrice);
    }
}
