package com.worldline.template.presenter;

import com.worldline.template.internal.di.PerActivity;
import com.worldline.template.view.activity.MainActivity;

import javax.inject.Inject;

/**
 * {@link Presenter} for the {@link MainActivity}.
 */
@PerActivity
public class MainActivityPresenter extends Presenter<MainActivity> {

    @Inject
    public MainActivityPresenter() {
    }

    @Override
    protected void initialize() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
    }

}