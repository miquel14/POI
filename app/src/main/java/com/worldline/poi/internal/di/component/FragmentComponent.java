package com.worldline.template.internal.di.component;

import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.internal.di.module.FragmentModule;
import com.worldline.template.view.fragment.RootFragment;

import android.support.v4.app.Fragment;

import dagger.Component;

/**
 * Subtypes of FragmentComponent should be decorated with annotation: {@link PerFragment}.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {


    void inject(RootFragment rootFragment);

    //Exposed to sub-graphs.
    Fragment fragment();

}