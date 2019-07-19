package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName
import java.util.*

class BasketModel : SegmentifyObject() {
    @SerializedName("step")
    var step:String? = null

    var price:Double? = null
    var quantity:Int? = null
    var productId:String? = null
    var activeBanners:LinkedList<ClickedBannerObject> = LinkedList()
}