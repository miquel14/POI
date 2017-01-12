package com.worldline.data.util.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Util class to perform operations in a shared preferences file
 */
@Singleton
public class PreferencesUtil {

    private static final String STRING_DEF_VALUE = "";

    private static final int INT_DEF_VALUE = 0;

    private SharedPreferences sharedPreferences;

    @Inject
    public PreferencesUtil(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Remove property from settings file
     *
     * @param settingName to remove
     */
    private void removeSetting(String settingName) {
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.remove(settingName);
        e.apply();
    }

    /**
     * Look for the value of a key in the shared preferences file
     *
     * @param key String name of the property
     *
     * @return String value of the property. By default an empty String will be returned.
     */
    private String getStringProperty(String key) {
        return sharedPreferences.getString(key, STRING_DEF_VALUE);
    }

    /**
     * Look for the value of a key in the shared preferences file
     *
     * @param key          String name of the property
     * @param defaultValue default value
     *
     * @return String value of the property. By default an empty String will be returned.
     */
    private String getStringProperty(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * Save a String property in the shared preferences file
     *
     * @param key   String name of the property
     * @param value String value of the property
     */
    private void setStringProperty(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Save a int property in the shared preferences file
     *
     * @param key   String name of the property
     * @param value int value of the property
     */
    private void setIntProperty(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Look for the value of a key in the shared preferences file
     *
     * @param key String name of the property
     *
     * @return int value of the property. By default 0 will be returned.
     */
    private int getIntProperty(String key) {
        return sharedPreferences.getInt(key, INT_DEF_VALUE);
    }

    /**
     * Save a boolean property in the shared preferences file
     *
     * @param key   String name of the property
     * @param value boolean value of the property
     */
    private void setBooleanProperty(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Look for the value of a key in the shared preferences file
     *
     * @param key          String name of the property
     * @param defaultValue default value
     *
     * @return boolean value of the property. By default 0 will be returned.
     */
    private boolean getBooleanProperty(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // TODO Add all preferences needed

}
