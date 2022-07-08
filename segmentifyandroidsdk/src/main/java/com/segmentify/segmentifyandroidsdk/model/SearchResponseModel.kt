package com.segmentify.segmentifyandroidsdk.model

class SearchResponseModel {
    var products: ArrayList<ProductRecommendationModel>? = null
    var campaign: SearchCampaignModel? = null
    var categoryProducts: HashMap<String, ArrayList<ProductRecommendationModel>>? = null
    var brands: HashMap<String, String>? = null
    var keywords: HashMap<String, ArrayList<ProductRecommendationModel>>? = null
    var brandProducts: HashMap<String, ArrayList<ProductRecommendationModel>>? = null
    var lastSearches: List<String>? = null
    var categories: HashMap<String, String>? = null

}