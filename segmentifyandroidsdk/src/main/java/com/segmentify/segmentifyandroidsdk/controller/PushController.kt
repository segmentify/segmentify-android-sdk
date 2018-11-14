package com.segmentify.segmentifyandroidsdk.controller

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.segmentify.segmentifyandroidsdk.model.*
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger
import com.google.gson.reflect.TypeToken
import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.utils.Constant
import org.json.JSONArray
import org.json.JSONObject
import com.segmentify.segmentifyandroidsdk.utils.ClientPreferences


internal object PushController {

    var clientPreferences : ClientPreferences? = null

    fun sendNotification(notificationModel: NotificationModel){


        ConnectionManager.getPushFactory().sendNotification(notificationModel,SegmentifyManager.configModel.apiKey!!)
                .enqueue(object : NetworkCallback<Any>(){
                    override fun onSuccess(response: Any) {
                    }
                })

    }

    fun sendNotificationInteraction(notificationModel: NotificationModel){

        ConnectionManager.getPushFactory().sendNotificationInteraction(notificationModel,SegmentifyManager.configModel.apiKey!!)
                .enqueue(object : NetworkCallback<Any>(){
                    override fun onSuccess(response: Any) {
                    }
                })

    }


}