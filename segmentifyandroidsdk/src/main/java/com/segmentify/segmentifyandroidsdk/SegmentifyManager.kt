package com.segmentify.segmentifyandroidsdk

import android.content.Context
import com.segmentify.segmentifyandroidsdk.controller.EventSender
import com.segmentify.segmentifyandroidsdk.model.*
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import com.segmentify.segmentifyandroidsdk.utils.ClientPreferences
import com.segmentify.segmentifyandroidsdk.utils.Constant
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger

object SegmentifyManager {

    var clientPreferences : ClientPreferences? = null
    private var eventSender : EventSender = EventSender()
    var configModel = ConfigModel()
    var sessionKeepSecond = Constant.sessionKeepSecond

    fun setSessionKeepSecond(sessionKeepSecond: Int) : Int{
        this.sessionKeepSecond = sessionKeepSecond
        return sessionKeepSecond
    }

    fun logStatus(isVisible: Boolean){
        clientPreferences?.setLogVisible(isVisible)
    }

    fun config(context: Context,appkey: String, dataCenterUrl: String, subDomain: String) {
        clientPreferences = ClientPreferences(context)
        this.configModel.apiKey = appkey
        this.configModel.dataCenterUrl = dataCenterUrl
        this.configModel.subDomain = subDomain
        setBaseApiUrl()

        if(clientPreferences?.getSessionId().isNullOrBlank()){
            if(clientPreferences?.getUserId().isNullOrBlank())
                getUserIdAndSessionId()
            else
                getSessionId()
        }
    }

    private fun setBaseApiUrl(){
        configModel.dataCenterUrl?.let { clientPreferences?.setApiUrl(it) }
    }

    fun sendPageView(pageModel: PageModel,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {

        if(pageModel.category.isNullOrBlank()){
            SegmentifyLogger.printLog("you must fill category before accessing sendPageView event method")
            return
        }

        if(pageModel.subCategory != null){
            pageModel.subCategory = pageModel.subCategory
        }

        eventSender.sendPageView(pageModel, configModel.apiKey!!,object : SegmentifyCallback<ArrayList<RecommendationModel>>{
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })

    }

    fun sendPageView(category : String,subCategory : String?,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        var pageModel = PageModel()
        pageModel.eventName = Constant.pageViewEventName
        pageModel.category = category
        pageModel.userID = clientPreferences?.getUserId()

        eventSender.sendPageView(pageModel, configModel.apiKey!!,object : SegmentifyCallback<ArrayList<RecommendationModel>>{
            override fun onDataLoaded(data: ArrayList<RecommendationModel>) {
                segmentifyCallback.onDataLoaded(data)
            }
        })
    }

    fun sendCustomEvent(customEventModel: CustomEventModel,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>) {
        if(customEventModel == null || customEventModel?.type.isNullOrBlank()){
            SegmentifyLogger.printLog("you must fill type before accessing sendCustomEvent event method")
            return
        }
        customEventModel.eventName = Constant.customEventName

        //setIDAndSendEventWithCallback
    }

    private fun getSessionId() {
        ConnectionManager.getUserSessionFactory().getSession(1).enqueue(object : NetworkCallback<ArrayList<String>>(){
            override fun onSuccess(response: ArrayList<String>) {
                clientPreferences?.setSessionId(response[0])
            }
        })
    }

    private fun getUserIdAndSessionId() {
        ConnectionManager.getUserSessionFactory().getUserIdAndSession(2).enqueue(object : NetworkCallback<ArrayList<String>>(){
            override fun onSuccess(response: ArrayList<String>) {
                clientPreferences?.setUserId(response[0])
                clientPreferences?.setSessionId(response[1])
            }
        })
    }

}