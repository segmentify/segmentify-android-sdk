package com.segmentify.segmentifyandroidsdk.model.faceted

class FacetElement {
    var property: String? = null
    var items: List<Item>? = arrayListOf()
    var filtered: List<Filter>? = arrayListOf()
    var viewMode: String? = null
}