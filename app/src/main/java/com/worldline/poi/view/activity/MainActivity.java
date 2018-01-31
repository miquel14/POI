package com.worldline.template.view.activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.worldline.template.R;
import com.worldline.template.internal.di.HasComponent;
import com.worldline.template.internal.di.component.DaggerMainActivityComponent;
import com.worldline.template.internal.di.component.MainActivityComponent;
import com.worldline.template.internal.di.module.MainActivityModule;
import com.worldline.template.presenter.MainActivityPresenter;
import com.worldline.template.presenter.Presenter;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import javax.inject.Inject;

import butterknife.BindView;


public class MainActivity extends RootActivity implements HasComponent<MainActivityComponent>, IView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    MainActivityPresenter presenter;

    private MainActivityComponent component;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        restoreActionBar(getString(R.string.app_name), false);
        initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.container_fragment, new MainFragment());
        }
        presenter.setView(this);
        presenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initializeInjector() {
        this.component = DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .mainActivityModule(new MainActivityModule())
                .build();
        this.component.inject(this);
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public MainActivityComponent getComponent() {
        return component;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public boolean isAlreadyLoaded() {
        return true;
    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
