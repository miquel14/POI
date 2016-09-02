package com.worldline.template.clean.data.internal.di.module;

import com.worldline.template.clean.data.AndroidApplication;
import com.worldline.template.clean.data.bus.RxBus;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    RxBus provideRxBus() {
        return new RxBus();
    }
}