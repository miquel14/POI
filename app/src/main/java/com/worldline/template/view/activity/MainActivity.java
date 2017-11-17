package com.worldline.template.view.activity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import com.worldline.data.GeoConstant;
import com.worldline.template.R;
import com.worldline.template.internal.di.HasComponent;
import com.worldline.template.internal.di.component.DaggerMainActivityComponent;
import com.worldline.template.internal.di.component.MainActivityComponent;
import com.worldline.template.internal.di.module.MainActivityModule;
import com.worldline.template.presenter.MainActivityPresenter;
import com.worldline.template.presenter.Presenter;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.MainFragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import javax.inject.Inject;

import butterknife.BindView;


public class MainActivity extends RootActivity implements HasComponent<MainActivityComponent>, IView {

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
        restoreActionBar(getString(R.string.app_name));
        initializeInjector();
        addFragment(R.id.container_fragment, new MainFragment());
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
}
