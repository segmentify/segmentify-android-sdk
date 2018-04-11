package com.segmentify.segmentifyandroidsdk.model

import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import java.util.*

open class SegmentifyObject{
    var userID : String? = SegmentifyManager.clientPreferences?.getUserId()
    var sessionID : String? = SegmentifyManager.clientPreferences?.getSessionId()
    var eventName : String? = null
    var pageUrl : String? = null
    var currency : String? = null
    var lang : String? = null
    var params : Map<String,Object>? = null
    var nextPage : Boolean? = null
}