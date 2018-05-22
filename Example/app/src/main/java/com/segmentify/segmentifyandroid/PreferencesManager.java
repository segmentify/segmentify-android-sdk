package com.segmentify.segmentifyandroid;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;

public class PreferencesManager {

    private static final String defaultPackageName = "mobi.appcent.defacto";
    private static final String emptyString = "";
    private final String preferenceName;
    private final SharedPreferences targetPreferences;
    private final Gson gson = new Gson();


    protected PreferencesManager(Context targetContext) {
        this(targetContext, defaultPackageName);
    }

    protected PreferencesManager(Context targetContext, String preferenceName) {
        this.preferenceName = preferenceName;
        String str = "test" + defaultPackageName;
        targetPreferences = new SecurePreferences(targetContext, str, "defacto_prefs.xml");
    }

    public void clearKey(String key) {
        if (targetPreferences.contains(key)) {
            putString(key, null);
        }
    }

    public boolean getBooleanValue(String key) {
        return targetPreferences.getBoolean(key, false);
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return targetPreferences.getBoolean(key, defaultValue);
    }

    public float getFloat(String key) {
        return targetPreferences.getFloat(key, 0);
    }

    public int getInt(String key) {
        return targetPreferences.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return targetPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return targetPreferences.getLong(key, 0);
    }

    public <T> T getObject(String key, Class<T> targetType) {
        return getObject(key, targetType, null);
    }

    public <T> T getObject(String key, Class<T> targetType, T defaultValue) {
        if (targetPreferences.contains(key)) {
            String preferenceTarget = targetPreferences.getString(key, "");
            if (!"".equals(preferenceTarget)) {
                return gson.fromJson(preferenceTarget, targetType);
            }
        }
        return defaultValue;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public String getString(String key) {
        return getString(key, emptyString);
    }

    public String getString(String key, String defaultValue) {
        return targetPreferences.getString(key, defaultValue);
    }

    public boolean putBoolean(String key, boolean value) {
        SharedPreferences.Editor targetEditor = targetPreferences.edit();
        targetEditor.putBoolean(key, value);
        return targetEditor.commit();
    }

    public boolean putFloat(String key, float targetValue) {
        SharedPreferences.Editor targetEditor = targetPreferences.edit();
        return targetEditor.putFloat(key, targetValue).commit();
    }

    public boolean putInt(String key, int targetValue) {
        SharedPreferences.Editor targetEditor = targetPreferences.edit();
        return targetEditor.putInt(key, targetValue).commit();
    }

    public boolean putLong(String key, long targetValue) {
        SharedPreferences.Editor targetEditor = targetPreferences.edit();
        return targetEditor.putLong(key, targetValue).commit();
    }

    public void putObject(String key, Object targetObject) {
        putString(key, gson.toJson(targetObject));
    }

    public void putString(String[] keys, String[] values) {
        SharedPreferences.Editor targetEditor = targetPreferences.edit();
        int counter = 0;
        for (String key : keys) {
            targetEditor.putString(key, values[counter++]);
        }
        targetEditor.apply();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor targetEditor = targetPreferences.edit();
        targetEditor.putString(key, value);
        targetEditor.apply();
    }

}

