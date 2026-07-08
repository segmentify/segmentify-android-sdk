package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

enum class Purpose {
    @SerializedName("MARKETING") MARKETING,
    @SerializedName("TRANSACTIONAL") TRANSACTIONAL,
    @SerializedName("PERSONALISATION") PERSONALISATION,
    @SerializedName("ALL") ALL
}
