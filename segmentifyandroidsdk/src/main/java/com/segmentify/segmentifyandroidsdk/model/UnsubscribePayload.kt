package com.segmentify.segmentifyandroidsdk.model

data class UnsubscribePayload(
    var channel: Channel,
    var email: String? = null,
    var phone: String? = null,
    var purpose: List<Purpose>? = null
)
