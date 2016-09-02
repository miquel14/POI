package com.worldline.template.clean.data;

import com.worldline.template.clean.data.internal.di.component.ApplicationComponent;
import com.worldline.template.clean.data.internal.di.component.DaggerApplicationComponent;
import com.worldline.template.clean.data.internal.di.module.ApplicationModule;

import android.app.Application;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();

        // TODO Add other initialisations needed
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}