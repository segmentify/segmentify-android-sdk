package com.segmentify.segmentifyandroidsdk.model.faceted

import java.lang.Boolean
import kotlin.String


class SearchBanner {
    var id: String? = null
    var instanceId: String? = null
    var status: BannerStatus? = null
    var searchType: SearchType? = null
    var name: String? = null
    var bannerUrl: String? = null
    var targetUrl: String? = null
    var position: Position? = null
    var width: String? = null
    var height: String? = null
    var method: Method? = null
    var newtab = Boolean.TRUE

    enum class Position {
        RESULTS_FOOTER, RESULTS_HEADER, FILTERS_FOOTER, FILTERS_HEADER, ASSETS_FOOTER, ASSETS_HEADER
    }

    enum class Method {
        UPLOAD, URL
    }

    enum class BannerStatus {
        ACTIVE, DRAFT, PASSIVE, DELETED
    }

    enum class SearchType {
        INSTANT, FACETED;

        companion object {
            fun from(type: String?): SearchType {
                return if (type == null) {
                    INSTANT
                } else valueOf(
                    type!!
                )
            }
        }
    }
}