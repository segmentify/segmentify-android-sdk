package com.segmentify.segmentifyandroidsdk.utils

import android.content.Context

/**
 * Created by rifatmertdolar on 11/04/2018.
 */

class ClientPreferences(context : Context) : PreferencesManager(context){

    private val USER_ID = "USER_ID"
    private val SESSION_ID = "SESSION_ID"
    private val IS_LOG_VISIBLE = "IS_LOG_VISIBLE"
    private val API_URL = "API_URL"

    fun getApiUrl(): String {
        return getString(API_URL, "")!!
    }

    fun setApiUrl(apiUrl: String) {
        putString(API_URL, apiUrl)
    }

    fun getUserId(): String {
        return getString(USER_ID, "")!!
    }

    fun setUserId(userId: String) {
        putString(USER_ID, userId)
    }

    fun getSessionId(): String {
        return getString(SESSION_ID, "")!!
    }

    fun setSessionId(userId: String) {
        putString(SESSION_ID, userId)
    }

    fun isLogVisible(): Boolean {
        return getBooleanValue(IS_LOG_VISIBLE, true)
    }

    fun setLogVisible(isLogVisible: Boolean) {
        putBoolean(IS_LOG_VISIBLE, isLogVisible)
    }

}