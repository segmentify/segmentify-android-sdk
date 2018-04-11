package com.segmentify.segmentifyandroidsdk.network

import android.util.Log
import com.segmentify.segmentifyandroidsdk.utils.SegmentifyLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class NetworkCallback<T> : Callback<T> {

    private val TOTAL_RETRIES = 3
    private var retryCount = 0

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        response?.apply{
            if(isSuccessful)
            {
                response.body()?.apply{
                    onSuccess(this)
                }
            }
            else if(response.code() == 500){

            }
        }
    }

    abstract fun onSuccess(response:T)

    override fun onFailure(call: Call<T>, t: Throwable) {
        if(!call.isCanceled) run {
            SegmentifyLogger.printLog(t.message.toString())
            if (retryCount++ < TOTAL_RETRIES) {
                SegmentifyLogger.printLog(": Retrying... ($retryCount out of $TOTAL_RETRIES)")
                retry(call)
            }
        }
    }

    private fun retry(call: Call<T>) {
        call.clone().enqueue(this)
    }

}