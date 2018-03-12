package com.worldline.template.internal.di.component;

import com.worldline.template.internal.di.PerActivity;
import com.worldline.template.internal.di.module.ActivityModule;
import com.worldline.template.internal.di.module.DetailActivityModule;
import com.worldline.template.view.activity.DetailActivity;

import dagger.Component;

/**
 * Created by A672272 on 09/11/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DetailActivityModule.class})
public interface DetailActivityComponent extends ActivityComponent {

    void inject(DetailActivity detailActivity);

}
