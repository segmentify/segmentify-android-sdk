package com.segmentify.segmentifyandroidsdk.controller

import com.segmentify.segmentifyandroidsdk.model.*
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger

internal object EventController {

    fun sendPageView(pageModel : PageModel,apiKey : String,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){
        ConnectionManager.getEventFactory().sendPageView(pageModel,apiKey)
                .enqueue(object : NetworkCallback<EventResponseModel>(){
                    override fun onSuccess(response: EventResponseModel) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        //segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

    fun sendCustomEvent(customEventModel: CustomEventModel,apiKey : String, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){
        ConnectionManager.getEventFactory().sendCustomEvent(customEventModel,apiKey)
                .enqueue(object : NetworkCallback<EventResponseModel>(){
                    override fun onSuccess(response: EventResponseModel) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        //segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

    fun sendProductView(productModel: ProductModel,apiKey : String, segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){
        ConnectionManager.getEventFactory().sendProductView(productModel,apiKey)
                .enqueue(object : NetworkCallback<EventResponseModel>(){
                    override fun onSuccess(response: EventResponseModel) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        //segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

    fun sendAddOrRemoveBasket(basketModel: BasketModel,apiKey : String){
        ConnectionManager.getEventFactory().sendAddOrRemoveBasket(basketModel,apiKey)
                .enqueue(object : NetworkCallback<Any>(){
                    override fun onSuccess(response: Any) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                    }
                })
    }

    fun sendPurchase(checkoutModel: CheckoutModel,apiKey : String,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        ConnectionManager.getEventFactory().sendPurchase(checkoutModel,apiKey)
                .enqueue(object : NetworkCallback<EventResponseModel>(){
                    override fun onSuccess(response: EventResponseModel) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        //segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

    fun sendUserRegister(userModel: UserModel,apiKey : String){
        ConnectionManager.getEventFactory().sendUserRegister(userModel,apiKey)
                .enqueue(object : NetworkCallback<Any>(){
                    override fun onSuccess(response: Any) {
                        SegmentifyLogger.printSuccessLog("Req basarili!")
                        //segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

}