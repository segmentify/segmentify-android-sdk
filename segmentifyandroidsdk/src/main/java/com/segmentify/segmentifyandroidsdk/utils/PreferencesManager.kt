package com.segmentify.segmentifyandroidsdk.utils

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import com.google.gson.Gson
import java.text.ParseException
import java.util.*

abstract class PreferencesManager(targetContext: Context){
    private val defaultPackageName = "AppcentPackageSettings"
    private val emptyString = ""
    private var preferenceName: String = ""
    private var targetPreferences: SharedPreferences
    private val gson = Gson()

    init {
        targetPreferences = targetContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
    }

    /*constructor(targetContext : Context,preferenceName : String) : this(targetContext) {
    }*/

    fun clearKey(key: String) {
        if (targetPreferences.contains(key)) {
            putString(key, null)
        }
    }

    fun getBooleanValue(key: String): Boolean {
        return targetPreferences.getBoolean(key, false)
    }

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return targetPreferences.getBoolean(key, defaultValue)
    }

    fun getFloat(key: String): Float {
        return targetPreferences.getFloat(key, 0f)
    }

    fun getInt(key: String): Int {
        return targetPreferences.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return targetPreferences.getInt(key, defaultValue)
    }

    fun getLong(key: String): Long {
        return targetPreferences.getLong(key, 0)
    }

    fun <T> getObject(key: String, targetType: Class<T>): T? {
        return getObject(key, targetType, null)
    }

    fun <T> getObject(key: String, targetType: Class<T>, defaultValue: T?): T? {
        if (targetPreferences.contains(key)) {
            var preferenceTarget = targetPreferences.getString(key, "")
            if (preferenceTarget != "") {
                if(preferenceTarget.contains("AM") || preferenceTarget.contains("PM")){
                    try{
                        return gson.fromJson(preferenceTarget, targetType)
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }
                }
                else{
                    val df = SimpleDateFormat("MMM dd, yyyy hh:mm:ss")
                    val outputformat = SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa")
                    var date: Date? = null
                    var output: String? = null

                    try {
                        date = df.parse(preferenceTarget)
                        output = '"'+outputformat.format(date).toString()+'"'

                    } catch (pe: ParseException) {
                        pe.printStackTrace()
                    }
                    return gson.fromJson(output, targetType)
                }

            }
        }
        return defaultValue
    }

    fun getPreferenceName(): String {
        return preferenceName
    }

    fun getString(key: String): String? {
        return getString(key, emptyString)
    }

    fun getString(key: String, defaultValue: String): String? {
        return targetPreferences.getString(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean): Boolean {
        val targetEditor = targetPreferences.edit()
        targetEditor.putBoolean(key, value)
        return targetEditor.commit()
    }

    fun putFloat(key: String, targetValue: Float): Boolean {
        val targetEditor = targetPreferences.edit()
        return targetEditor.putFloat(key, targetValue).commit()
    }

    fun putInt(key: String, targetValue: Int): Boolean {
        val targetEditor = targetPreferences.edit()
        return targetEditor.putInt(key, targetValue).commit()
    }

    fun putLong(key: String, targetValue: Long): Boolean {
        val targetEditor = targetPreferences.edit()
        return targetEditor.putLong(key, targetValue).commit()
    }

    fun putObject(key: String, targetObject: Any) {
        putString(key, gson.toJson(targetObject))
    }

    fun putString(keys: Array<String>, values: Array<String>) {
        val targetEditor = targetPreferences.edit()
        var counter = 0
        for (key in keys) {
            targetEditor.putString(key, values[counter++])
        }
        targetEditor.apply()
    }

    fun putString(key: String, value: String?) {
        val targetEditor = targetPreferences.edit()
        targetEditor.putString(key, value)
        targetEditor.apply()
    }


}