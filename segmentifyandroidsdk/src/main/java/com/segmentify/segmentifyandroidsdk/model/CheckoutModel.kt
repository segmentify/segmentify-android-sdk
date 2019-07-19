package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class CheckoutModel : SegmentifyObject() {
    var totalPrice:Double? = null
    var productList:ArrayList<ProductModel>? = null
    var orderNo:String? = null
    var activeBanners:LinkedList<ClickedBannerObject> = LinkedList()
    @SerializedName("step")
    var checkoutStep:String? = null
}