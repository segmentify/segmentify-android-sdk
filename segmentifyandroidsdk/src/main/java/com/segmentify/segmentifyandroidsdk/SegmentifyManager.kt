package com.segmentify.segmentifyandroidsdk

import com.segmentify.segmentifyandroidsdk.model.ConfigModel

object SegmentifyManager {

    var logStatus : Boolean? = null
    var setup = ConfigModel()
    var sessionKeepSecond: Int = 86400


    fun setSessionKeepSecond(sessionKeepSecond: Int) : Int{
        this.sessionKeepSecond = sessionKeepSecond
        return sessionKeepSecond
    }


    fun logStatus(isVisible: Boolean) : Boolean{
        logStatus = isVisible
        return isVisible
    }

    fun config(appkey: String, dataCenterUrl: String, subDomain: String) {
        this.setup.apiKey = appkey
        this.setup.dataCenterUrl = dataCenterUrl
        this.setup.subDomain = subDomain
    }

}