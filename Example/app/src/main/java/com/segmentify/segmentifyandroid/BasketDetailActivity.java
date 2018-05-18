package com.segmentify.segmentifyandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

import java.util.ArrayList;

public class BasketDetailActivity extends AppCompatActivity {

    TextView tvProductName, tvPrice, tvBasketAmount;
    ImageView ivProduct;
    Button btnPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_detail);

        final String productId = getIntent().getStringExtra("id");
        final String name = getIntent().getStringExtra("name");
        final String price = getIntent().getStringExtra("price");
        final String image = getIntent().getStringExtra("image");

        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvBasketAmount = (TextView) findViewById(R.id.tvBasketAmount);
        ivProduct = (ImageView) findViewById(R.id.imgProduct);
        btnPurchase = (Button) findViewById(R.id.btnPurchase);

        final ListView lvBasket = findViewById(R.id.lvBasket);
        final ListView lvBottom = findViewById(R.id.lvBottom);

        SegmentifyManager.INSTANCE.sendPageView("Home Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> recommendationModels) {
                if(recommendationModels != null){
                    System.out.println(recommendationModels);
                    ListAdapter segmentifyBottomListAdapter = new ListAdapter(BasketDetailActivity.this,recommendationModels.get(0).getProducts());
                    lvBottom.setAdapter(segmentifyBottomListAdapter);
                }
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
        lvBottom.setAdapter(BasketAdapter);

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
                intent.putExtra("image", "https:" + image);
                startActivity(intent);
            }
        });

        tvBasketAmount.setText("Total amount of items: " + price);
    }
}
