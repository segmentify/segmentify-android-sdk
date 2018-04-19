package com.segmentify.segmentifyandroidsdk.controller

import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import java.util.*
import javax.xml.datatype.DatatypeConstants.DAYS
import java.util.concurrent.TimeUnit


internal object KeyController {

    fun controlSessionTimeout() {
        val calendar = Calendar.getInstance()
        calendar.time = Calendar.getInstance().time
        calendar.add(Calendar.SECOND, SegmentifyManager.clientPreferences?.getSessionKeepSeconds()!!)
        var date = calendar.time
        if(SegmentifyManager.clientPreferences?.getSavedDateForSession() != null){
            date = SegmentifyManager.clientPreferences?.getSavedDateForSession()
        }
        val dayBySecondsDifference = getDifferenceDays(date,Calendar.getInstance().time)
        if(dayBySecondsDifference > SegmentifyManager.clientPreferences?.getSessionKeepSeconds()!!) {
            SegmentifyManager.clientPreferences?.setSavedDateforSession(calendar.time)
            getSessionId()
        }
        else{
            SegmentifyManager.clientPreferences?.setSavedDateforSession(calendar.time)
        }
    }

    fun getDifferenceDays(d1: Date, d2: Date): Long {
        val diff = d2.time - d1.time
        return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS)
    }

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