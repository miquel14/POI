package com.worldline.template.internal.di.component;

import com.worldline.template.internal.di.PerFragment;
import com.worldline.template.internal.di.module.FragmentModule;
import com.worldline.template.internal.di.module.ItemDetailFragmentModule;
import com.worldline.template.view.fragment.DetailFragment;
import com.worldline.template.view.fragment.RootFragment;

import dagger.Component;


@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = {FragmentModule.class, ItemDetailFragmentModule.class})
public interface DetailFragmentComponent extends FragmentComponent {

    void inject(DetailFragment detailFragment);
}


