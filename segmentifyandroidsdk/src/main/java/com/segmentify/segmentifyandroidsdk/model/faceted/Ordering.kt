package com.segmentify.segmentifyandroidsdk.model.faceted

class Ordering {
    var sort: String? = null
    var page: Int? = null

    constructor(sort: String, page: Int) : this() {
        this.sort = sort
        this.page = page
    }

    constructor() {
        this.sort = "SMART_SORTING"
        this.page = 1
    }
}