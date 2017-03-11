package com.assignment.pawan.soreboard.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rahul Kumar Patel.
 */
public class PreferenceManager {

    private static final String PREF_NAME = "BWealthy";

    /*USER DETAILS*/
    public static final String WAR_DB = "word_store";



    public static String getStringPreference(Context activity, String key) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        return sp.getString(key, "");
    }

    public static void setStringPreference(Context activity, String key, String value) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static long getLongPreference(Context activity, String key) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        return sp.getLong(key, 0);
    }

    public static void setLongPreference(Context activity, String key, long value) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static boolean getBooleanPreference(Context activity, String key) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        return sp.getBoolean(key, false);
    }

    public static void setBooleanPreference(Context activity, String key, boolean value) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getIntegerPreference(Context activity, String key) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        return sp.getInt(key, 0);
    }

    public static void setIntegerPreference(Context activity, String key, int value) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void removeStringPreference(Context activity, String key) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static boolean isExist(Context activity, String key) {
        SharedPreferences sp = activity.getSharedPreferences(PreferenceManager.PREF_NAME, 0);
        return sp.contains(key);
    }
}
