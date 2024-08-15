package com.segmentify.segmentifyandroidsdk.controller

import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.network.ConnectionManager
import com.segmentify.segmentifyandroidsdk.network.NetworkCallback
import java.util.*
import java.util.concurrent.TimeUnit


internal object KeyController {

    fun controlSessionTimeout() {
        val calendar = Calendar.getInstance()
        calendar.time = Calendar.getInstance().time
        calendar.add(
            Calendar.SECOND,
            SegmentifyManager.clientPreferences?.getSessionKeepSeconds()!!
        )
        var date = calendar.timeInMillis
        if (SegmentifyManager.clientPreferences?.getSavedDateForSession() != null) {
            date = SegmentifyManager.clientPreferences?.getSavedDateForSession()!!
        }
        val dayBySecondsDifference = getDifferenceDays(date, Calendar.getInstance().timeInMillis)
        if (dayBySecondsDifference > SegmentifyManager.clientPreferences?.getSessionKeepSeconds()!!) {
            SegmentifyManager.clientPreferences?.setSavedDateforSession(calendar.timeInMillis)
            getSessionId()
        } else {
            SegmentifyManager.clientPreferences?.setSavedDateforSession(calendar.timeInMillis)
        }
    }

    fun getDifferenceDays(d1: Long, d2: Long): Long {
        val diff = d2 - d1
        return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS)
    }

    fun getSessionId() {
        ConnectionManager.getUserSessionFactory().getSession(1)
            .enqueue(object : NetworkCallback<ArrayList<String>>() {
                override fun onSuccess(response: ArrayList<String>) {
                    SegmentifyManager.clientPreferences?.setSessionId(response[0])
                }
            })
    }

    fun getUserIdAndSessionId() {
        ConnectionManager.getUserSessionFactory().getUserIdAndSession(2)
            .enqueue(object : NetworkCallback<ArrayList<String>>() {
                override fun onSuccess(response: ArrayList<String>) {
                    SegmentifyManager.clientPreferences?.setUserId(response[0])
                    SegmentifyManager.clientPreferences?.setSessionId(response[1])
                }
            })
    }

}