package com.segmentify.segmentifyandroidsdk.network

import android.content.Context
import android.net.ConnectivityManager
import com.segmentify.segmentifyandroidsdk.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ConnectionManager {
    private val timeoutInterval = 60
    //private var customerFactory : CustomerFactory
    //private var applicationFactory : ApplicationFactory
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
        val service = Retrofit.Builder()
                .baseUrl(BuildConfig.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        //customerFactory = service.create(CustomerFactory::class.java)
        //applicationFactory = service.create(ApplicationFactory::class.java)
    }

    /*fun getCustomerFactory(): CustomerFactory {
        return customerFactory
    }

    fun getApplicationFactory(): ApplicationFactory {
        return applicationFactory
    }*/

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}