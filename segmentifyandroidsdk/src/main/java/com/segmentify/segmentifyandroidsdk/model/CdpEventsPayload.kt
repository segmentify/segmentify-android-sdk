package com.segmentify.segmentifyandroidsdk.model

data class CdpEventsPayload(
    var phone: String? = null,
    var email: String? = null,
    var username: String? = null,
    var source: String? = null,
    var gender: String? = null,
    var callConsent: Consent? = null,
    var emailConsent: Consent? = null,
    var whatsappConsent: Consent? = null,
    var smsConsent: Consent? = null,
    var fullName: String? = null,
    var custom: Map<String, Any?>? = null
)
