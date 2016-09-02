package com.worldline.template.clean;

import com.karumi.dexter.Dexter;
import com.worldline.template.clean.data.AndroidApplication;

/**
 * Android App Application Class
 * TODO: Add and modify all initialisations need
 */
public class AndroidAppApplication extends AndroidApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //        TODO uncomment to initialize Fabric.Crashlytics
        //        Fabric.with(this, new CrashLytics());

        Dexter.initialize(this);
    }
}
