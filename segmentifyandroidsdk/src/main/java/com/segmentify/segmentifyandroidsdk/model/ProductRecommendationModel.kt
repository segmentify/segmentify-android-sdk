package com.segmentify.segmentifyandroidsdk.model

import java.util.Date

class ProductRecommendationModel {

    var productId: String? = null
    var name: String? = null
    var inStock: Boolean? = null
    var url: String? = null
    var mUrl: String? = null
    var image: String? = null
    var imageXS: String? = null
    var imageS: String? = null
    var imageM: String? = null
    var imageL: String? = null
    var imageXL: String? = null
    var additionalImages: ArrayList<String>? = null
    var category: ArrayList<String>? = null
    var categories: ArrayList<String>? = null
    var mainCategory: String? = null
    var brand: String? = null
    var price: Double? = null
    var priceText: String? = null
    var oldPrice: Double? = null
    var oldPriceText: String? = null
    var specialPrice: Double? = null
    var specialPriceText: String? = null
    var gender: String? = null
    var colors: ArrayList<String>? = null
    var sizes: ArrayList<String>? = null
    var allSizes: ArrayList<String>? = null
    var labels: ArrayList<String>? = null
    var badges: ArrayList<String>? = null
    var noUpdate: Boolean? = null
    var params: Map<String, Any>? = null
    var paramsList: Map<String, List<String>>? = null
    var language: String? = null
    var currency: String? = null
    var lastUpdateTime: Date? = null
    var stockCount: Int? = null
    var stockRatio: Float? = null
    var stockStatus: Int? = null
    var publishTime: Date? = null
    var combineIds: ArrayList<String>? = null
    var groupId: String? = null
    var priceSegment: String? = null
    var lastBoughtTime: Date? = null
    var scoreCount: Int? = null
    var reviewCount: Int? = null
    var savingOverTime: Long? = null
}
