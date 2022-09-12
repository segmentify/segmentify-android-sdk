package com.segmentify.segmentifyandroidsdk.model.faceted

import com.segmentify.segmentifyandroidsdk.model.ProductRecommendationModel

class SearchFacetedResponseModel {
    var facets: List<FacetElement>? = null
    var meta: Meta? = null
    var contents: List<CustomContent>? = arrayListOf()
    var banners: List<SearchBanner>? = arrayListOf()
    var meanings: List<String>? = arrayListOf()
    var products: List<ProductRecommendationModel>? = arrayListOf()
}