package com.segmentify.segmentifyandroidsdk.network.Factories

import com.segmentify.segmentifyandroidsdk.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface PushFactory {

        @POST("/native/subscription/push")
        fun sendNotification(@Body notificationModel: NotificationModel,@Query("apiKey")apiKey : String): Call<Any>

        @POST("/native/interaction/notification")
        fun sendNotificationInteraction(@Body notificationModel: NotificationModel,@Query("apiKey")apiKey : String): Call<Any>


}
