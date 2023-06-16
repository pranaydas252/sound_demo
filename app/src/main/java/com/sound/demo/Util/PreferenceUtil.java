package com.sound.demo.Util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sound.demo.Base.App;
import com.google.gson.Gson;


public class PreferenceUtil {
    private static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());
    private static final String NOTIFICATION = "notification";
    private static final String CLOUD_MESSAGING_TOKEN_SENT_KEY = "cloud_messaging_token_sent";

    public static boolean isUserLoggedIn() {
        return preferences.getBoolean("is_logged_in", false);
    }

    public static void setUserLoggedIn(boolean isLoggedIn) {
        preferences.edit().putBoolean("is_logged_in", isLoggedIn).apply();
    }


    public static void putNotification(Boolean key) {
        preferences.edit().putBoolean(NOTIFICATION, key).apply();
    }

    public static Boolean getNotification() {
        //  final String lan = preferences.getString(USER_MULTILINGUAL, null);
        return preferences.getBoolean(NOTIFICATION, false);
    }

    public static boolean getCloudMessagingTokenSent() {
        return preferences.getBoolean(CLOUD_MESSAGING_TOKEN_SENT_KEY, false);
    }

    public static void setCloudMessagingTokenSent(boolean tokenSent) {
        preferences.edit().putBoolean(CLOUD_MESSAGING_TOKEN_SENT_KEY, tokenSent).apply();
    }
}
