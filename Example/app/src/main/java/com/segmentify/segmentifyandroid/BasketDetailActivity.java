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
import com.segmentify.segmentifyandroidsdk.model.CheckoutModel;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class BasketDetailActivity extends AppCompatActivity {

    TextView tvProductName, tvPrice, tvBasketAmount;
    ImageView ivProduct;
    Button btnPurchase;

    String productId;
    String name;
    String price;
    String image;

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
                BottomRecyclerAdapter bottomRecyclerAdapter = new BottomRecyclerAdapter(data.get(0).getProducts(),BasketDetailActivity.this,onClickListener);
                rvBottom.setAdapter(bottomRecyclerAdapter);
                rvBottom.setLayoutManager(new LinearLayoutManager(BasketDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
            }
        });
















        String[] idsArr = {};
        String newItemId = productId;
        int currentSizeId = idsArr.length;
        int newSizeId = currentSizeId + 1;
        String[] tempArrayId = new String[ newSizeId ];
        for (int i=0; i < currentSizeId; i++)
        {
            tempArrayId[i] = idsArr [i];
        }
        tempArrayId[newSizeId- 1] = newItemId;
        idsArr = tempArrayId;

        String[] namesArr = {};
        String newItemName = name;
        int currentSizeName = namesArr.length;
        int newSizeName = currentSizeName + 1;
        String[] tempArrayName = new String[ newSizeName ];
        for (int i=0; i < currentSizeName; i++)
        {
            tempArrayName[i] = namesArr [i];
        }
        tempArrayName[newSizeName- 1] = newItemName;
        namesArr = tempArrayName;

        String[] priceArr = {};
        String newItemPrice = price;
        int currentSizePrice = priceArr.length;
        int newSizePrice = currentSizePrice + 1;
        String[] tempArrayPrice = new String[ newSizePrice ];
        for (int i=0; i < currentSizePrice; i++)
        {
            tempArrayPrice[i] = priceArr [i];
        }
        tempArrayPrice[newSizePrice- 1] = newItemPrice;
        priceArr = tempArrayPrice;

        String[] imagesArr = {};
        String newItemImg = image;
        int currentSizeImg = imagesArr.length;
        int newSizeImg = currentSizeImg + 1;
        String[] tempArrayImg = new String[ newSizeImg ];
        for (int i=0; i < currentSizeImg; i++)
        {
            tempArrayImg[i] = imagesArr [i];
        }
        tempArrayImg[newSizeImg- 1] = newItemImg;
        imagesArr = tempArrayImg;

        BasketAdapter BasketAdapter = new BasketAdapter(idsArr,namesArr,priceArr,imagesArr,BasketDetailActivity.this);
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
                startActivity(intent);
            }
        });

        tvBasketAmount.setText("Total amount of items: " + price);
    }
}
