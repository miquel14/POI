package com.worldline.template;

import com.karumi.dexter.Dexter;

import android.app.Application;

public class AndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //        TODO uncomment to initialize Fabric.Crashlytics
        //        Fabric.with(this, new CrashLytics());

        Dexter.initialize(this);
    }
}
