package com.segmentify.segmentifyandroidsdk.model

import com.google.gson.annotations.SerializedName

class ServerResponseModel {

    @SerializedName("timestamp")
    private var timestamp: Long = 0

    @SerializedName("statusCode")
    private var statusCode: String? = null

    fun getTimestamp(): Long {
        return timestamp
    }

    fun setTimestamp(timestamp: Long) {
        this.timestamp = timestamp
    }

    fun getStatusCode(): String? {
        return if (statusCode != null) {
            if (statusCode!!.isEmpty())
                null
            else
                statusCode
        } else {
            null
        }
    }

    fun setStatusCode(statusCode: String) {
        this.statusCode = statusCode
    }
}