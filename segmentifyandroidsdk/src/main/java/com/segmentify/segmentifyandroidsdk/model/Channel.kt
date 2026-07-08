package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

enum class Channel {
    @SerializedName("web_push") WEB_PUSH,
    @SerializedName("email") EMAIL,
    @SerializedName("whatsapp") WHATSAPP,
    @SerializedName("sms") SMS,
    @SerializedName("call") CALL
}
