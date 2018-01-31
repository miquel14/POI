package com.worldline.template.internal.di.component;

import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.internal.di.module.FragmentModule;
import com.worldline.template.internal.di.module.MainFragmentModule;
import com.worldline.template.view.fragment.MainFragment;

import dagger.Component;

/**
 * Component for {@link MainFragment}.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = {FragmentModule.class, MainFragmentModule.class})
public interface MainFragmentComponent extends FragmentComponent {

    void inject(MainFragment mainFragment);

}
