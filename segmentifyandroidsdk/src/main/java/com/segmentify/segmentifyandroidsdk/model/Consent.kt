package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

enum class Consent {
    @SerializedName("SUBSCRIBED") SUBSCRIBED,
    @SerializedName("UNSUBSCRIBED") UNSUBSCRIBED,
    @SerializedName("NOT_SET") NOT_SET
}
