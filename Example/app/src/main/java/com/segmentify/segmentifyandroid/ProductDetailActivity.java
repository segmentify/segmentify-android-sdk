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

    TextView tvProductname, tvPrice;
    ImageView ivProduct;
    Button btnAdd;
    ListView lvBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        final String productId = getIntent().getStringExtra("id");
        final String name = getIntent().getStringExtra("name");
        final String price = getIntent().getStringExtra("price");
        final String image = getIntent().getStringExtra("image");

        tvProductname = (TextView) findViewById(R.id.tvProductName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        ivProduct = (ImageView) findViewById(R.id.imgProduct);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        lvBottom = (ListView) findViewById(R.id.lvBottom);

        SegmentifyManager.INSTANCE.sendPageView("Home Page", null, new SegmentifyCallback<ArrayList<RecommendationModel>>() {
            @Override
            public void onDataLoaded(ArrayList<RecommendationModel> recommendationModels) {
                if(recommendationModels != null){
                    System.out.println(recommendationModels);
                    ListAdapter segmentifyBottomListAdapter = new ListAdapter(ProductDetailActivity.this,recommendationModels.get(0).getProducts());
                    lvBottom.setAdapter(segmentifyBottomListAdapter);
                }
            }
        });

        tvProductname.setText(name);
        tvPrice.setText(price  + " TL");
        Picasso.get().load(image).into(ivProduct);
        //new DownloadImage().execute(image);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ProductRecommendationModel> basketProductList = MyApplication.getClientPreferences().getProductRecommendationModelList();

                if(basketProductList == null){
                    basketProductList = new ArrayList<>();
                }


                ProductRecommendationModel productRecommendationModel = new ProductRecommendationModel();
                productRecommendationModel.setProductId(productId);
                productRecommendationModel.setName(name);
                productRecommendationModel.setPrice(Double.parseDouble(price));
                productRecommendationModel.setImage(image);

                basketProductList.add(productRecommendationModel);
                MyApplication.getClientPreferences().setProductRecommendationModelList(basketProductList);
                Intent intent = new Intent(ProductDetailActivity.this, BasketDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setImage(Drawable drawable)
    {
        ivProduct.setBackgroundDrawable(drawable);
    }

    public class DownloadImage extends AsyncTask<String, Integer, Drawable> {

        @Override
        protected Drawable doInBackground(String... arg0) {
            // This is done in a background thread
            return downloadImage(arg0[0]);
        }

        /**
         * Called after the image has been downloaded
         * -> this calls a function on the main thread again
         */
        protected void onPostExecute(Drawable image) {
            setImage(image);
        }


        /**
         * Actually download the Image from the _url
         *
         * @param _url
         * @return
         */
        private Drawable downloadImage(String _url) {
            //Prepare to download image
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;

            //BufferedInputStream buf;
            try {
                url = new URL(_url);
                in = url.openStream();

            /*
             * THIS IS NOT NEEDED
             *
             * YOU TRY TO CREATE AN ACTUAL IMAGE HERE, BY WRITING
             * TO A NEW FILE
             * YOU ONLY NEED TO READ THE INPUTSTREAM
             * AND CONVERT THAT TO A BITMAP
            out = new BufferedOutputStream(new FileOutputStream("testImage.jpg"));
            int i;
             while ((i = in.read()) != -1) {
                 out.write(i);
             }
             out.close();
             in.close();
             */

                // Read the inputstream
                buf = new BufferedInputStream(in);

                // Convert the BufferedInputStream to a Bitmap
                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                return new BitmapDrawable(bMap);

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

            return null;
        }
    }
}
