package com.worldline.template.view.activity;

import com.worldline.template.R;
import com.worldline.template.internal.di.HasComponent;
import com.worldline.template.internal.di.component.DaggerDetailActivityComponent;
import com.worldline.template.internal.di.component.DetailActivityComponent;
import com.worldline.template.internal.di.module.DetailActivityModule;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.presenter.DetailActivityPresenter;
import com.worldline.template.presenter.Presenter;
import com.worldline.template.view.IView;
import com.worldline.template.view.adapter.viewholder.MainItemsAdapter;
import com.worldline.template.view.fragment.DetailFragment;
import com.worldline.template.view.fragment.MainFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailActivity extends RootActivity implements HasComponent<DetailActivityComponent>,IView {

    @Inject
    DetailActivityPresenter presenter;

    private DetailActivityComponent component;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setSupportActionBar(toolbar);
        addFragment(R.id.container_detail_fragment, new DetailFragment());
        setContentView(R.layout.activity_main);
        super.restoreActionBar("Informació");
        if (savedInstanceState == null) {
            initializeInjector();
        }
    }

    public static Intent getDetailCallingIntent(Context context,int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("PARAM_ID", id);
        return intent;
    }

    @Override
    public DetailActivityComponent getComponent() {
        return getComponent();
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_detail;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }


    private void initializeInjector() {
        this.component = DaggerDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .detailActivityModule(new DetailActivityModule())
                .build();
        this.component.inject(this);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public boolean isAlreadyLoaded() {
        return false;
    }

    @Override
    public void showEmptyCase() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}

