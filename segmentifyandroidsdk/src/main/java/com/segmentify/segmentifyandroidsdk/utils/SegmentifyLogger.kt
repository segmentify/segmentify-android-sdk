package com.segmentify.segmentifyandroidsdk.utils

import android.util.Log

class SegmentifyLogger {
    companion object {
        fun printErrorLog(message:String){
            Log.e(Constant.segmentfyErrorLog,message)
        }

        fun printSuccessLog(message:String){
            Log.e(Constant.segmentfySuccessLog,message)
        }
    }
}