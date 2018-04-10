package com.segmentify.segmentifyandroidsdk.model

import java.util.*

open class SegmentifyObject{
     var sessionID : String? = null
    var pageUrl : String? = null
    var currency : String? = null
    var lang : String? = null
    var params : Map<String,Object>? = null
    var nextPage : Boolean? = null
}