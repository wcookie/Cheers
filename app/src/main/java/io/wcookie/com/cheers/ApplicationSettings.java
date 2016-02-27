package io.wcookie.com.cheers;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class ApplicationSettings {

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
    }

    public static String getStringPref(Context context, String key) {
        return getPrefs(context).getString(key, null);
    }

    public static int getIntPref(Context context, String key) {
        return getPrefs(context).getInt(key, -1);
    }

    public static String setStringPref(Context context, String key, String value) {

        getPrefs(context).edit().putString(key, value).commit();
        return value;
    }


    public static int setIntPref(Context context, String key, int value) {

        getPrefs(context).edit().putInt(key, value).commit();
        return value;
    }

    public static float setFloatPref(Context context, String key, float value) {

        getPrefs(context).edit().putFloat(key, value).commit();
        return value;
    }

    public static Float getFloatPref(Context context, String key) {
        return getPrefs(context).getFloat(key, -1);
    }

    public static boolean getBooleanPref(Context context, String key) {
        return getPrefs(context).getBoolean(key, false);
    }

    public static boolean setBooleanPref(Context context, String key, boolean value) {

        getPrefs(context).edit().putBoolean(key, value).commit();
        return value;
    }
}