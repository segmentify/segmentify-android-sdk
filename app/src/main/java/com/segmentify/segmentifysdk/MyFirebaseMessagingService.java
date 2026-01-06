package com.segmentify.segmentifysdk;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        String deepLinkUrl = null;
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();
            if (data.containsKey("deeplink")) {
                deepLinkUrl = data.get("deeplink");
            }

            if (/* Check if data needs to be processed by long running job */ false) {
                scheduleJob();
            } else {
                handleNow();
            }
        }

        if (remoteMessage.getNotification() != null) {
            String messageBody = remoteMessage.getNotification().getBody();
            String messageTitle = remoteMessage.getNotification().getTitle();
            Log.d(TAG, "Message Notification Body: " + messageBody);

            sendNotification(messageBody, messageTitle, deepLinkUrl);

            NotificationModel model = new NotificationModel();
            model.setType(NotificationType.VIEW);
            if (remoteMessage.getData().containsKey("instanceId")) {
                model.setInstanceId(remoteMessage.getData().get("instanceId"));
            }
            SegmentifyManager.INSTANCE.sendNotificationInteraction(model);
        }

        else if (deepLinkUrl != null) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");

            if(title != null && body != null){
                sendNotification(body, title, deepLinkUrl);
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

    // 4. METOD GÜNCELLEMESİ: deepLink parametresi eklendi
    private void sendNotification(String messageBody, String title, String deepLink) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (deepLink != null && !deepLink.isEmpty()) {
            intent.putExtra("deeplink", deepLink);
        }
        int requestCode = (int) System.currentTimeMillis();

        int flags = PendingIntent.FLAG_ONE_SHOT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, flags);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Segmentify Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Segmentify push notification channel");
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(requestCode, notificationBuilder.build());
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