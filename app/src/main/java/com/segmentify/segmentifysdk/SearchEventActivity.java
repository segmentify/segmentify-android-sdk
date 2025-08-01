package com.segmentify.segmentifysdk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.SearchPageModel;
import com.segmentify.segmentifyandroidsdk.model.SearchResponseModel;
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback;

public class SearchEventActivity extends AppCompatActivity {

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

    }
}