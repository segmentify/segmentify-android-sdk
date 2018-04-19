package com.segmentify.segmentifyandroidsdk.model

class CheckoutModel : SegmentifyObject() {
    var totalPrice:Double? = null
    var productList:ArrayList<ProductModel>? = null
    var orderNo:String? = null
    var checkoutStep:String? = null
}