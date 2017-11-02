package com.worldline.template.internal.di.component;

import com.worldline.template.internal.di.PerActivity;
import com.worldline.template.internal.di.module.ActivityModule;
import com.worldline.template.internal.di.module.MainActivityModule;
import com.worldline.template.view.activity.MainActivity;

import dagger.Component;

/**
 * Component for {@link MainActivity}.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MainActivityModule.class})
public interface MainActivityComponent extends ActivityComponent {

    void inject(MainActivity mainActivity);

}
