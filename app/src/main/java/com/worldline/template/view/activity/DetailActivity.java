package com.worldline.template.view.activity;

import com.worldline.template.R;
import com.worldline.template.internal.di.HasComponent;
import com.worldline.template.internal.di.component.DaggerDetailActivityComponent;
import com.worldline.template.internal.di.component.DetailActivityComponent;
import com.worldline.template.internal.di.module.DetailActivityModule;
import com.worldline.template.presenter.DetailActivityPresenter;
import com.worldline.template.presenter.Presenter;
import com.worldline.template.view.IView;
import com.worldline.template.view.fragment.DetailFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailActivity extends RootActivity implements HasComponent<DetailActivityComponent>,IView {

    @Inject
    DetailActivityPresenter presenter;

    private DetailActivityComponent component;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(R.id.container_detail_fragment, new DetailFragment());
        initializeInjector();
        int id = getIntent().getIntExtra("PARAM_ID",0);
        String title = getIntent().getStringExtra("PARAM_TITLE");

        restoreActionBar(title);

        Fragment fragment = DetailFragment.newInstance(id);
        addFragment(R.id.container_detail_fragment,fragment);
        presenter.setView(this);
        presenter.start();
    }



    public static Intent getDetailCallingIntent(Context context, int id, String title) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("PARAM_ID", id);
        intent.putExtra("PARAM_TITLE",title);
        return intent;
    }

    @Override
    public DetailActivityComponent getComponent() {
        return component;
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

