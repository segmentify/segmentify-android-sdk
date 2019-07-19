package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

class BannerOperationsModel : SegmentifyObject() {
    var title:String? = null
    var group:String? = null
    var order:Int? = null
    var productId:String? = null
    var category:ArrayList<String>? = null
    var brand:String? = null
    var label:String? = null
    @SerializedName("type")
    var bannerType:String? = null
    var async:Boolean? = false
}