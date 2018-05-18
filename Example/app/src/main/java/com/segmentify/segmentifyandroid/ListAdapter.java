package com.segmentify.segmentifyandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{

    ArrayList<ProductRecommendationModel> productRecommendationModelArrayList;
    Activity activity;
    ImageView ivProduct;
    Button btnAdd, btnDetail;


    public ListAdapter(Activity activity, ArrayList<ProductRecommendationModel> productRecommendationModelArrayList) {

        this.activity = activity;
        this.productRecommendationModelArrayList = productRecommendationModelArrayList;

    }

    public int getCount() {
        return productRecommendationModelArrayList.size();
    }

    public Object getItem(int arg0) {
        return productRecommendationModelArrayList.get(arg0);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = activity.getLayoutInflater();
        View row;
        row = inflater.inflate(R.layout.item_list, parent, false);
        TextView tvProductname, tvPrice;
        tvProductname = (TextView) row.findViewById(R.id.tvProductName);
        tvPrice = (TextView) row.findViewById(R.id.tvPrice);
        ivProduct=(ImageView)row.findViewById(R.id.imgProduct);
        btnAdd = row.findViewById(R.id.btnAdd);
        btnDetail = row.findViewById(R.id.btnDetail);
        new DownloadImage().execute("https:" + productRecommendationModelArrayList.get(position).getImage());
        tvProductname.setText(productRecommendationModelArrayList.get(position).getName());
        tvPrice.setText(productRecommendationModelArrayList.get(position).getPrice() + " TL");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, BasketDetailActivity.class);
                intent.putExtra("productId", productRecommendationModelArrayList.get(position).getProductId());
                intent.putExtra("name", productRecommendationModelArrayList.get(position).getName());
                intent.putExtra("price", productRecommendationModelArrayList.get(position).getPrice().toString());
                intent.putExtra("image", "https:" + productRecommendationModelArrayList.get(position).getImage());
                activity.startActivity(intent);
            }
        });

        /*btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra("productId", productRecommendationModelArrayList.get(position).getProductId());
                intent.putExtra("name", productRecommendationModelArrayList.get(position).getName());
                intent.putExtra("price", productRecommendationModelArrayList.get(position).getPrice().toString());
                intent.putExtra("image", "https:" + productRecommendationModelArrayList.get(position).getImage());
                activity.startActivity(intent);
            }
        });

        return (row);
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
