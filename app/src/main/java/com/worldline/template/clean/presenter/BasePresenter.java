package com.worldline.template.clean.presenter;

import com.worldline.template.clean.data.bus.RxBus;
import com.worldline.template.clean.data.util.preferences.PreferencesUtil;
import com.worldline.template.clean.navigation.Navigator;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public abstract class BasePresenter implements Presenter {

    @Inject
    Navigator navigator;

    @Inject
    PreferencesUtil preferencesUtil;

    @Inject
    RxBus rxBus;

    private CompositeSubscription subscriptions;

    protected void subscribeToBus() {
        subscriptions = new CompositeSubscription();
        // TODO Add subscriptions
    }

    @Override
    public void destroy() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    subscriptions.unsubscribe();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}