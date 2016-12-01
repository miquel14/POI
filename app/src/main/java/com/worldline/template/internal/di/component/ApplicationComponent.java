package com.worldline.template.internal.di.component;

import com.worldline.data.bus.RxBus;
import com.worldline.data.util.preferences.PreferencesUtil;
import com.worldline.domain.executor.PostExecutionThread;
import com.worldline.domain.executor.ThreadExecutor;
import com.worldline.template.AndroidApplication;
import com.worldline.template.internal.di.module.ApplicationModule;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    // Add your inject() methods
    void inject(AndroidApplication androidApplication);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    PreferencesUtil preferencesUtil();

    RxBus rxBus();

}