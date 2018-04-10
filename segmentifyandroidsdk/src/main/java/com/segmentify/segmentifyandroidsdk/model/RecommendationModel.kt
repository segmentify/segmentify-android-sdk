package com.segmentify.segmentifyandroidsdk.model

class RecommendationModel {
    var notificationTitle:String? = null
    var products:ArrayList<ProductRecommendationModel>? = null
    var errorString:String? = null
    var instanceId:String? = null
    var interactionId:String? = null

    constructor(notificationTitle: String, products: ArrayList<ProductRecommendationModel>, errorString: String?, instanceId: String?, interactionId: String?) {
        this.notificationTitle = notificationTitle
        this.products = products
        this.errorString = errorString
        this.instanceId = instanceId
        this.interactionId = interactionId
    }
}