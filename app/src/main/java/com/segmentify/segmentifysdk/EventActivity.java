package com.segmentify.segmentifysdk;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.BannerGroupViewModel;
import com.segmentify.segmentifyandroidsdk.model.BannerOperationsModel;
import com.segmentify.segmentifyandroidsdk.model.CheckoutModel;
import com.segmentify.segmentifyandroidsdk.model.InternalBannerModel;
import com.segmentify.segmentifyandroidsdk.model.NotificationModel;
import com.segmentify.segmentifyandroidsdk.model.NotificationType;
import com.segmentify.segmentifyandroidsdk.model.PageModel;
import com.segmentify.segmentifyandroidsdk.model.ProductModel;
import com.segmentify.segmentifyandroidsdk.model.SearchPageModel;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private static final String TAG = "EventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button subscribeButton = findViewById(R.id.button);
        subscribeButton.setOnClickListener(v -> {

            SearchPageModel searchPage = new SearchPageModel();
            searchPage.setQuery("");
            searchPage.setLang("TR");
            SegmentifyManager.INSTANCE.sendSearchPageView(searchPage, data -> Log.d(TAG, String.valueOf(data)));
        });
    }

    // [START ask_post_notifications]
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });
}