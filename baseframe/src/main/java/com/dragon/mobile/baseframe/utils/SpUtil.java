package com.dragon.mobile.baseframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    private SharedPreferences settings;

    private SpUtil(Context context) {
        settings = context.getSharedPreferences(AppUtil.getInstance().getProjectName(context), Context.MODE_PRIVATE);
    }

    public static SpUtil getInstance(Context context) {

        return new SpUtil(context);
    }

    public String getSpString(String key, final String defaultValue) {
        return settings.getString(key, defaultValue);
    }

    public void putSpString(final String key, final String value) {
        settings.edit().putString(key, value).apply();
    }

    public boolean getSpBoolean(final String key, final boolean defaultValue) {
        return settings.getBoolean(key, defaultValue);
    }

    public boolean hasKey(final String key) {
        return settings.contains(key);
    }

    public void setSpBoolean(final String key, final boolean value) {
        settings.edit().putBoolean(key, value).apply();
    }

    public void setSpInt(final String key, final int value) {
        settings.edit().putInt(key, value).apply();
    }

    public int getSpInt(final String key, final int defaultValue) {
        return settings.getInt(key, defaultValue);
    }

    public void setSpFloat(final String key, final float value) {
        settings.edit().putFloat(key, value).apply();
    }

    public float getSpFloat(final String key, final float defaultValue) {
        return settings.getFloat(key, defaultValue);
    }

    public void setSpLong(final String key, final long value) {
        settings.edit().putLong(key, value).apply();
    }

    public long getSpLong(final String key, final long defaultValue) {
        return settings.getLong(key, defaultValue);
    }

    public void remove(String key) {
        settings.edit().remove(key).apply();
    }
}
