package com.segmentify.segmentifyandroidsdk.network.Factories

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserSessionFactory {

    @GET("get/key")
    fun getSession(@Query("count") count : Int): Call<ArrayList<String>>

    @GET("get/key?")
    fun getUserIdAndSession(@Query("count") count : Int): Call<ArrayList<String>>
}