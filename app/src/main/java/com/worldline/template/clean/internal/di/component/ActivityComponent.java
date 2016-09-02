package com.worldline.template.clean.internal.di.component;

import com.worldline.template.clean.internal.di.PerActivity;
import com.worldline.template.clean.internal.di.module.ActivityModule;
import com.worldline.template.clean.view.activity.BaseActivity;
import com.worldline.template.clean.data.internal.di.component.ApplicationComponent;

import android.app.Activity;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 * <p>
 * Subtypes of ActivityComponent should be decorated with annotation: {@link PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Activity activity();
}