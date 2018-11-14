package com.segmentify.segmentifyandroidsdk.model

import com.segmentify.segmentifyandroidsdk.SegmentifyManager

class UtmModel {
    var utm_source:String? = null
    var utm_medium:String? = null
    var utm_campaign:String? = null
    var utm_content:String? = null


    init {

        utm_source = "segmentify"
        utm_medium = "push"

        var instanceId = SegmentifyManager.clientPreferences?.getString("PUSH_CAMPAIGN_ID")
        var productId = SegmentifyManager.clientPreferences?.getString("PUSH_CAMPAIGN_PRODUCT_ID")

        utm_campaign = instanceId
        utm_content = productId

    }
}

