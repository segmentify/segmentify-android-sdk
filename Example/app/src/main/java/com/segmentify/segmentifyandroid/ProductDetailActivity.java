package com.segmentify.segmentifyandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.BasketModel;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.PortUnreachableException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    TextView tvProductname, tvPrice,tvProductId;
    ImageView ivProduct;
    Button btnAdd;
    ListView lvBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        final String productId = getIntent().getStringExtra("productId");
        final String name = getIntent().getStringExtra("name");
        final String price = getIntent().getStringExtra("price");
        final String image  = getIntent().getStringExtra("image");




        final String url = getIntent().getStringExtra("url");

        tvProductname = (TextView) findViewById(R.id.tvProductName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        ivProduct = (ImageView) findViewById(R.id.imgProduct);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        lvBottom = (ListView) findViewById(R.id.lvBottom);

        SegmentifyManager.INSTANCE.sendPageView("Product Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> recommendationModels) {
                if(recommendationModels != null){

                }
            }
        });




        ProductModel model = new ProductModel();
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Womenswear");

        model.setProductId(productId);
        model.setCategories(categories);
        model.setPrice(Double.parseDouble(price));
        model.setTitle(name);
        model.setImage(image);
        model.setUrl(url);

        SegmentifyManager.INSTANCE.sendProductView(model, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> data) {
                System.out.println(data);
                ListAdapter segmentifyBottomListAdapter = new ListAdapter(ProductDetailActivity.this,data.get(0).getProducts(),false);
                lvBottom.setAdapter(segmentifyBottomListAdapter);



            }
        });





        tvProductname.setText(name);
        tvPrice.setText(price  + " TL");


        String fix_image ="";

        if(image.startsWith("https:https://"))
        {
            fix_image =    image.replace("https:https://","https://");
        }
        else if(image.startsWith("//"))
        {
            fix_image = "https:" +  image;
        }
        else if(image.startsWith("https://"))
        {
            fix_image = image;
        }





        Picasso.get().load(fix_image).into(ivProduct);
        //new DownloadImage().execute(image);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ProductRecommendationModel> basketProductList = MyApplication.getClientPreferences().getProductRecommendationModelList();

                if(basketProductList == null){
                    basketProductList = new ArrayList<>();
                }


                BasketModel model = new BasketModel();
                model.setStep("add");
                model.setProductId(productId);
                model.setQuantity(1);
                model.setPrice(Double.parseDouble((price)));
                SegmentifyManager.INSTANCE.sendAddOrRemoveBasket(model);

                ProductRecommendationModel productRecommendationModel = new ProductRecommendationModel();
                productRecommendationModel.setProductId(productId);
                productRecommendationModel.setName(name);
                productRecommendationModel.setPrice(Double.parseDouble(price));
                productRecommendationModel.setImage(image);
                productRecommendationModel.setUrl(url);
                basketProductList.add(productRecommendationModel);
                MyApplication.getClientPreferences().setProductRecommendationModelList(basketProductList);
                Intent intent = new Intent(ProductDetailActivity.this, BasketDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
