package com.segmentify.segmentifyandroidsdk.utils

class Constant {
    companion object {

        //Keys
        val tokenKey = "SEGMENTIFY_TOKEN_KEY"
        val registerKey = "SEGMENTIFY_REGISTER_KEY"
        val lastRequestDateKey = "SEGMENTIFY_LAST_REQUEST_DATE_KEY"


        //Events
        val pageViewEventName = "PAGE_VIEW"
        val productViewEventName = "PRODUCT_VIEW"
        val basketOperationsEventName = "BASKET_OPERATIONS"
        val checkoutEventName = "CHECKOUT"
        val userOperationEventName = "USER_OPERATIONS"
        val userChangeEventName = "USER_CHANGE"
        val customEventName = "CUSTOM_EVENT"
        val interactionEventName = "INTERACTION"

        //Steps
        val customerInformationStep = "customer"
        val viewBasketStep = "view-basket"
        val paymentInformationStep = "payment-info"
        val paymentPurchaseStep = "purchase"
        val signInStep = "signin"
        val registerStep = "signup"
        val logoutStep = "signout"
        val updateUserStep = "update"
        val impressionStep = "impression"
        val widgetViewStep = "widget-view"
        val clickStep = "click"

        //Custom
        val sessionKeepSecond = 86400
        val segmentfyErrorLog = "Segmentify Error"
    }
}