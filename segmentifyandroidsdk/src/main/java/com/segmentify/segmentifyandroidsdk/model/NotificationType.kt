package com.segmentify.segmentifyandroidsdk.model

import android.content.Context
import android.net.Uri
import android.os.Build
import com.google.gson.annotations.SerializedName
import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.utils.ClientPreferences

enum class NotificationType{
    SUBSCRIBE_TOPIC,UNSUBSCRIBE_TOPIC,VIEW,CLICK,PERMISSION_INFO
}