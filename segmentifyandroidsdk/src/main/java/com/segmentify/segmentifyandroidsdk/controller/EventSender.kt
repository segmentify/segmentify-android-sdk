package com.segmentify.segmentifyandroidsdk.controller

import com.segmentify.segmentifyandroidsdk.model.PageModel
import com.segmentify.segmentifyandroidsdk.model.RecommendationModel
import com.segmentify.segmentifyandroidsdk.model.ServerResponseModel
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyCallback

class EventSender {

    fun sendPageView(pageModel : PageModel,apiKey : String,segmentifyCallback: SegmentifyCallback<ArrayList<RecommendationModel>>){
        ConnectionManager.getEventFactory().sendPageView(pageModel,apiKey)
                .enqueue(object : NetworkCallback<ArrayList<RecommendationModel>>(){
                    override fun onSuccess(response: ArrayList<RecommendationModel>) {
                        segmentifyCallback.onDataLoaded(response)
                    }
                })
    }

}