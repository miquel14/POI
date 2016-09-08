package com.worldline.data.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * TODO: Add your comments
 */
public final class DeviceUtil {

    private DeviceUtil() {
    }

    /**
     * Get device id.
     *
     * @param context Context application.
     *
     * @return String device id.
     */
    public static String getDeviceSerialNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getDeviceUniqueId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}