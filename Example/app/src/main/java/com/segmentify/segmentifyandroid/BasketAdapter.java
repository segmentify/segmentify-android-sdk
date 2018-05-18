package com.segmentify.segmentifyandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class BasketAdapter extends BaseAdapter{
    String[] idsArr;
    String[] namesArr;
    String[] priceArr;
    String[] imagesArr;
    Context context;

    public BasketAdapter(String[] idsArr,String[] namesArr, String[] priceArr, String[] imagesArr, Context context) {
        this.idsArr = idsArr;
        this.namesArr = namesArr;
        this.priceArr = priceArr;
        this.imagesArr = imagesArr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return namesArr.length;
    }

    @Override
    public Object getItem(int position) {
        return namesArr[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_basket,null) ;
        }

        ImageView imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        TextView tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        TextView tvProductPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvProductName.setText(namesArr[position]);
        tvProductPrice.setText(priceArr[position]);
        new DownloadImage().execute("https:" + imagesArr[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", idsArr[position]);
                intent.putExtra("name", namesArr[position]);
                intent.putExtra("price", priceArr[position]);
                intent.putExtra("image", "https:" + imagesArr[position]);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private void setImage(Drawable drawable)
    {

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
