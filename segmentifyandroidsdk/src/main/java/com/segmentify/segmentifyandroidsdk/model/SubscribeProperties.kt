package com.segmentify.segmentifyandroidsdk.model

sealed class SubscribeProperties(val channel: Channel) {

    data class WebPush(
        var pushSubscriptionId: String? = null,
        var fcmToken: String? = null,
        var auth: String? = null,
        var p256dh: String? = null,
        var endPoint: String? = null
    ) : SubscribeProperties(Channel.WEB_PUSH)

    data class Email(
        var email: String,
        var purpose: List<Purpose>
    ) : SubscribeProperties(Channel.EMAIL)

    data class Whatsapp(
        var phone: String
    ) : SubscribeProperties(Channel.WHATSAPP)

    data class Sms(
        var phone: String
    ) : SubscribeProperties(Channel.SMS)

    data class Call(
        var phone: String
    ) : SubscribeProperties(Channel.CALL)
}
