package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

class InteractionModel: SegmentifyObject() {
    @SerializedName("step")
    var widgetViewStep:String? = null

    var interactionId:String? = null
    var impressionId:String? = null
    var instanceId:String? = null
    var type:String? = null
}