package com.segmentify.segmentifyandroidsdk

import android.content.Context
import com.segmentify.segmentifyandroidsdk.controller.EventController
import com.segmentify.segmentifyandroidsdk.controller.KeyController
import com.segmentify.segmentifyandroidsdk.model.*
import com.segmentify.segmentifyandroidsdk.utils.ClientPreferences
import com.segmentify.segmentifyandroidsdk.utils.Constant
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger

object SegmentifyManager {

    var clientPreferences : ClientPreferences? = null
    var configModel = ConfigModel()
    private var sessionKeepSecond = Constant.sessionKeepSecond


    fun setSessionKeepSecond(sessionKeepSecond: Int) : Int {
        this.sessionKeepSecond = sessionKeepSecond
        return sessionKeepSecond
    }

    fun logStatus(isVisible: Boolean) {
        clientPreferences?.setLogVisible(isVisible)
    }

    fun config(context: Context,appKey: String, dataCenterUrl: String, subDomain: String) {

        if(appKey.isNullOrBlank() || dataCenterUrl.isNullOrBlank() || subDomain.isNullOrBlank()){
            SegmentifyLogger.printErrorLog("Api is not initialized, you can not enter null or empty parameter to config, please recheck your config parameters")
            return
        }

        clientPreferences = ClientPreferences(context)
        this.configModel.apiKey = appKey
        this.configModel.dataCenterUrl = dataCenterUrl
        this.configModel.subDomain = subDomain
        setBaseApiUrl()

        if(clientPreferences?.getSessionId().isNullOrBlank()){
            if(clientPreferences?.getUserId().isNullOrBlank())
                KeyController.getUserIdAndSessionId()
            else
                KeyController.getSessionId()
        }
    }

    private fun setBaseApiUrl() {
        configModel.dataCenterUrl?.let { clientPreferences?.setApiUrl(it) }
    }

    fun sendPageView(pageModel: PageModel,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {

        if(pageModel.category.isNullOrBlank()){
            SegmentifyLogger.printErrorLog("you must fill category before accessing sendPageView event method")
            return
        }

        if(pageModel.subCategory != null) {
            pageModel.subCategory = pageModel.subCategory
        }
        EventController.sendPageView(pageModel, configModel.apiKey!!,object : SegmentifyCallback<ArrayList<RecommendationModel>>{
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })

    }

    fun sendPageView(category : String,subCategory : String?,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {

        if(category.isNullOrBlank()){
            SegmentifyLogger.printErrorLog("you must fill category before accessing sendPageView event method")
            return
        }

        var pageModel = PageModel()
        pageModel.eventName = Constant.pageViewEventName
        pageModel.category = category
        pageModel.subCategory = subCategory

        EventController.sendPageView(pageModel, configModel.apiKey!!,object : SegmentifyCallback<ArrayList<RecommendationModel>>{
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendCustomEvent(customEventModel: CustomEventModel,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        if(customEventModel == null || customEventModel?.type.isNullOrBlank()){
            SegmentifyLogger.printErrorLog("you must fill type before accessing sendCustomEvent event method")
            return
        }
        customEventModel.eventName = Constant.customEventName

        EventController.sendCustomEvent(customEventModel, configModel.apiKey!!,object : SegmentifyCallback<ArrayList<RecommendationModel>>{
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendProductView(productModel: ProductModel,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        productModel.name = Constant.productViewEventName

        if(productModel?.productId == null){
            SegmentifyLogger.printErrorLog("You must fill productId before accessing sendProductView event")
            return
        }
        if(productModel?.name == null){
            SegmentifyLogger.printErrorLog("You must fill title before accessing sendProductView event method")
            return
        }
        if(productModel?.url == null){
            SegmentifyLogger.printErrorLog("You must fill url before accessing sendProductView event method")
            return
        }
        if(productModel?.image == null){
            SegmentifyLogger.printErrorLog("You must fill image before accessing sendProductView event method")
            return
        }
        if(productModel?.category == null){
            SegmentifyLogger.printErrorLog("You must fill category before accessing sendProductView event method")
            return
        }
        if(productModel?.categories != null && productModel?.categories != null){
            SegmentifyLogger.printErrorLog("You can not both fill category and categpries parameters")
            return
        }
        if(productModel?.categories == null && productModel?.categories == null){
            SegmentifyLogger.printErrorLog("You should fill one of category and categories parameters")
            return
        }
        if(productModel.price == null){
            SegmentifyLogger.printErrorLog("You must fill price before accessing sendProductView event method")
            return
        }

        EventController.sendProductView(productModel, configModel.apiKey!!,object : SegmentifyCallback<ArrayList<RecommendationModel>>{
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendAddOrRemoveBasket(basketModel: BasketModel){
        basketModel.eventName = Constant.basketOperationsEventName

        if(basketModel.step == null){
            SegmentifyLogger.printErrorLog("You must fill step before accessing sendAddOrRemoveBasket event method")
            return
        }
        if(basketModel.productId == null){
            SegmentifyLogger.printErrorLog("You must fill productId before accessing sendAddOrRemoveBasket event method")
            return
        }
        if(basketModel.quantity == null){
            SegmentifyLogger.printErrorLog("You must fill quantity before accessing sendAddOrRemoveBasket event method")
            return
        }

        EventController.sendAddOrRemoveBasket(basketModel, configModel.apiKey!!)
    }

    fun sendPurchase(checkoutModel: CheckoutModel,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        checkoutModel.eventName = Constant.checkoutEventName
        checkoutModel.eventName = Constant.paymentPurchaseStep

        if(checkoutModel.totalPrice == null) {
            SegmentifyLogger.printErrorLog("You must fill userId before accessing sendPurchase event")
            return
        }
        if(checkoutModel.productList == null || checkoutModel.productList?.size == 0){
            SegmentifyLogger.printErrorLog("You must fill productList before accessing sendPurchase event method")
            return
        }

        EventController.sendPurchase(checkoutModel, configModel.apiKey!!,object : SegmentifyCallback<ArrayList<RecommendationModel>>{
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendUserRegister(userModel: UserModel) {
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.registerStep

        if(userModel.email == null && userModel.username == null) {
            SegmentifyLogger.printErrorLog("You must fill userId or email before accessing sendUserLogout event")
            return
        }

        EventController.sendUserRegister(userModel, configModel.apiKey!!)

    }

    fun sendUserRegister(username : String?, fullName : String?, email : String?, mobilePhone : String?, gender : String?, age : String?, birthdate : String?){
        var userModel = UserModel()
        userModel.eventName = Constant.userOperationEventName
        userModel.userOperationStep = Constant.registerStep
        userModel.username = username
        userModel.fullName = fullName
        userModel.email = email
        userModel.mobilePhone = mobilePhone
        userModel.gender = gender
        userModel.age = age
        userModel.birthdate = birthdate

        EventController.sendUserRegister(userModel, configModel.apiKey!!)
    }

}