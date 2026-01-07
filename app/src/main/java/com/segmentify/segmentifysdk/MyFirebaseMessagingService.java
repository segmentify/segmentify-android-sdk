package com.segmentify.segmentifysdk;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.NotificationModel;
import com.segmentify.segmentifyandroidsdk.model.NotificationType;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();

            String title = data.get("title");
            String body = data.get("message");
            String deepLink = data.get("deeplink");
            String image = data.get("image");

            if (title != null || body != null) {
                sendNotification(body, title, deepLink, image);
            }

            if (data.containsKey("instanceId")) {
                NotificationModel model = new NotificationModel();
                model.setType(NotificationType.VIEW);
                model.setInstanceId(data.get("instanceId"));
                SegmentifyManager.INSTANCE.sendNotificationInteraction(model);
            }
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        WorkManager.getInstance(this).beginWith(work).enqueue();
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    private void sendNotification(String messageBody, String title, String deepLink, String image) {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            if (deepLink != null && !deepLink.isEmpty()) {
                intent.putExtra("deeplink", deepLink);
            }

            if (image != null && !image.isEmpty()) {
                intent.putExtra("pushimage", image);
            }

            int requestCode = (int) System.currentTimeMillis();

            int flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
            PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, flags);

            String channelId = "segmentify_push_channel_v2";

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title != null ? title : "Notification")
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent)
                            .setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigPicture(getBitmapFromUrl(image)))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "Segmentify Bildirimleri",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel.setDescription("Uygulama bildirimleri");
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(requestCode, notificationBuilder.build());
            Log.d("MyFirebaseMsgService", "Bildirim oluşturma komutu gönderildi. ID: " + requestCode);
        } catch (Exception e) {
            Log.e("MyFirebaseMsgService", "Bildirim oluşturulurken hata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class MyWorker extends Worker {
        public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            return Result.success();
        }
    }
}