package com.segmentify.segmentifyandroidsdk.utils

import android.util.Log

class SegmentifyLogger {
    companion object {
        fun printLog(message:String){
            Log.e(Constant.segmentfyErrorLog,message)
        }
    }
}