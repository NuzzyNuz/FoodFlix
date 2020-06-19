/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//import androidx.preference.PreferenceManager;

/**
 * The type Shared pref util.
 */
public class SharedPrefUtil {
    private static SharedPreferences preferences;

    private SharedPrefUtil() {
    }

    /**
     * Init.
     *
     * @param context the context
     */
    public static void init(Context context) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    /**
     * Write boolean.
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean write(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * Write boolean.
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean write(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * Write boolean.
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean write(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * Write boolean.
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public static boolean write(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * Read string string.
     *
     * @param key the key
     * @return the string
     */
    public static String readString(String key) {
        return preferences.getString(key, "");
    }

    /**
     * Read long long.
     *
     * @param key the key
     * @return the long
     */
    public static long readLong(String key) {
        return preferences.getLong(key, 0);
    }

    /**
     * Read int int.
     *
     * @param key the key
     * @return the int
     */
    public static int readInt(String key) {
        return preferences.getInt(key, 0);
    }

    /**
     * Read boolean default true boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean readBooleanDefaultTrue(String key) {
        return preferences.getBoolean(key, true);
    }

    /**
     * Read boolean boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean readBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    /**
     * Contains boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean contains(String key) {
        return preferences.contains(key);
    }

    /**
     * Clear.
     */
    public static void clear() {
        preferences.edit().clear().apply();
    }

    /**
     * Delete.
     *
     * @param key the key
     */
    public static void delete(String key) {
        preferences.edit().remove(key).apply();
    }
}