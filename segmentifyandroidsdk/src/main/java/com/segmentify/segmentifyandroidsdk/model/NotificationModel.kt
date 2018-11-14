package com.segmentify.segmentifyandroidsdk.model

import android.content.Context
import android.net.Uri
import android.os.Build
import com.google.gson.annotations.SerializedName
import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.utils.ClientPreferences

class NotificationModel{

    var deviceToken:String? = null
    var type:NotificationType? = null
    var instanceId:String? = null
    var productId:String? = null
    var topic:String? = null
    var params:Map<String,String>? = null
    private var email:String? = null
    private  var userName:String? = null
    private  var userId : String?=null
    private  var osVersion:String?=null
    private  var os:String?=null
    private  var providerType:String?=null


    init {

        if(type != NotificationType.PERMISSION_INFO){deviceToken = null }
        osVersion = Build.VERSION.SDK_INT.toString()
        os = "ANDROID"
        providerType = "FIREBASE"
        var userId_ = SegmentifyManager.clientPreferences?.getUserId()
        var email_ = SegmentifyManager.clientPreferences?.getString("EMAIL")
        var userName_ = SegmentifyManager.clientPreferences?.getString("USER_NAME")
        if(!email_.isNullOrEmpty()){  email = email_  }
        if(!userName_.isNullOrEmpty()){ userName = userName_}
        if(!userId_.isNullOrEmpty()){ userId = userId_}

    }

}