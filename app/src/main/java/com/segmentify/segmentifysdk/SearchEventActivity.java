package com.segmentify.segmentifysdk;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.SearchPageModel;

public class SearchEventActivity extends AppCompatActivity {
    private static final String TAG = "SearchEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchPageModel searchPage = new SearchPageModel();
        searchPage.setQuery("");
        searchPage.setLang("TR");
        SegmentifyManager.INSTANCE.sendSearchPageView(searchPage, data -> Log.d(TAG, String.valueOf(data)));
        SegmentifyManager.INSTANCE.sendSearchClickView("product", "21046");

    }
}