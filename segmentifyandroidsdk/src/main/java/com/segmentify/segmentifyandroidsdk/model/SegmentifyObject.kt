package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName
import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import java.util.*

open class SegmentifyObject{
    var userId : String? = SegmentifyManager.clientPreferences?.getUserId()
    var sessionId : String? = SegmentifyManager.clientPreferences?.getSessionId()

    @SerializedName("name")
    var eventName : String? = null

    var pageUrl : String? = null
    var currency : String? = null
    var lang : String? = null
    var params : Map<String,Object>? = null
    var nextPage : Boolean? = null
    var advertisingIdentifier : String? = null
    var appVersion : String? = null
}