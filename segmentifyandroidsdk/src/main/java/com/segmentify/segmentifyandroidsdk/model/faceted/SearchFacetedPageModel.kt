package com.segmentify.segmentifyandroidsdk.model.faceted

import com.segmentify.segmentifyandroidsdk.model.SegmentifyObject

class SearchFacetedPageModel : SegmentifyObject() {
    var query: String? = null
    var type: String = "faceted"
    var trigger: String  = "page"
    var ordering: Ordering? = null
    var filters:  List<Filter>? = arrayListOf()
}