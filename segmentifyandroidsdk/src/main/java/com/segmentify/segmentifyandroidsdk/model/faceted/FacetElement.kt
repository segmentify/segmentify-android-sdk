package com.segmentify.segmentifyandroidsdk.model.faceted

class FacetElement {
    var property: String? = null
    var items: List<Item>? = arrayListOf()
    var filtered: List<String>? = arrayListOf()
    var viewMode: String? = null
}