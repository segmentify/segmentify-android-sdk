package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

class CheckoutModel : SegmentifyObject() {
    var totalPrice:Double? = null
    var productList:ArrayList<ProductModel>? = null
    var orderNo:String? = null
    @SerializedName("step")
    var checkoutStep:String? = null
}