package com.segmentify.segmentifyandroidsdk.controller

import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback

internal object KeyController {

    fun getSessionId() {
        ConnectionManager.getUserSessionFactory().getSession(1).enqueue(object : NetworkCallback<ArrayList<String>>(){
            override fun onSuccess(response: ArrayList<String>) {
                SegmentifyManager.clientPreferences?.setSessionId(response[0])
            }
        })
    }

    fun getUserIdAndSessionId() {
        ConnectionManager.getUserSessionFactory().getUserIdAndSession(2).enqueue(object : NetworkCallback<ArrayList<String>>(){
            override fun onSuccess(response: ArrayList<String>) {
                SegmentifyManager.clientPreferences?.setUserId(response[0])
                SegmentifyManager.clientPreferences?.setSessionId(response[1])
            }
        })
    }

}