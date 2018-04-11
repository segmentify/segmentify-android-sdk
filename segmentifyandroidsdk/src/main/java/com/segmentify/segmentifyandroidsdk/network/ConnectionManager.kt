package com.segmentify.segmentifyandroidsdk.network

import android.content.Context
import android.net.ConnectivityManager
import com.segmentify.segmentifyandroidsdk.BuildConfig
import com.segmentify.segmentifyandroidsdk.SegmentifyManager
import com.segmentify.segmentifyandroidsdk.network.Factories.EventFactory
import com.segmentify.segmentifyandroidsdk.network.Factories.UserSessionFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ConnectionManager {
    private val timeoutInterval = 60
    private var userSessionFactory : UserSessionFactory
    private var eventFactory : EventFactory
    private val client : OkHttpClient

    init {
        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG_MODE) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(timeoutInterval.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(timeoutInterval.toLong(), TimeUnit.SECONDS)

        client = httpClient.build()
        val keyService = Retrofit.Builder()
                .baseUrl(BuildConfig.KEY_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        userSessionFactory = keyService.create(UserSessionFactory::class.java)

        val eventService = Retrofit.Builder()
                .baseUrl(SegmentifyManager.clientPreferences?.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        eventFactory = eventService.create(EventFactory::class.java)
    }

    fun getUserSessionFactory(): UserSessionFactory {
        return userSessionFactory
    }

    fun getEventFactory(): EventFactory {
        return eventFactory
    }


    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}