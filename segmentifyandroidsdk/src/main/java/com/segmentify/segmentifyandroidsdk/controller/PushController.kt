package com.segmentify.segmentifyandroidsdk.controller

import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.model.NotificationModel
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import com.segmentify.segmentifyandroidsdk.utils.ClientPreferences
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger
import java.lang.Exception


internal object PushController {

    var clientPreferences: ClientPreferences? = null

    fun sendNotification(notificationModel: NotificationModel) {

        try {
            ConnectionManager.getPushFactory().sendNotification(notificationModel, SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>() {
                        override fun onSuccess(response: Any) {
                        }
                    })
        } catch (e: Exception) {
            SegmentifyLogger.printErrorLog("Error occurred when sending notification model, error: $e")
        }
    }

    fun sendNotificationInteraction(notificationModel: NotificationModel) {

        try {
            ConnectionManager.getPushFactory().sendNotificationInteraction(notificationModel, SegmentifyManager.configModel.apiKey!!)
                    .enqueue(object : NetworkCallback<Any>() {
                        override fun onSuccess(response: Any) {
                        }
                    })
        } catch (e: Exception) {
            SegmentifyLogger.printErrorLog("Error occurred when sending notification interaction model, error: $e")
        }
    }
}