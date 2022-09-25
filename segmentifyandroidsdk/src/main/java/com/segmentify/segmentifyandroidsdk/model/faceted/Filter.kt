package com.segmentify.segmentifyandroidsdk.model.faceted

class Filter() {
    var facet: String? = null
    var values: List<String>? = null

    constructor(facet: String, values: List<String>) : this() {
        this.facet = facet
        this.values = values
    }
}