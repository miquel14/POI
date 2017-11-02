package com.worldline.template.view.fragment;


import com.worldline.domain.model.HomeItems;
import com.worldline.template.R;
import com.worldline.template.internal.di.HasComponent;
import com.worldline.template.internal.di.component.MainFragmentComponent;
import com.worldline.template.presenter.MainFragmentPresenter;
import com.worldline.template.presenter.Presenter;
import com.worldline.template.view.IView;
import com.worldline.template.view.adapter.BaseRecyclerViewAdapter;
import com.worldline.template.view.adapter.viewholder.MainItemsAdapter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainFragment extends RootFragment implements HasComponent<MainFragmentComponent>, BaseRecyclerViewAdapter.OnItemClickedListener,
        IView, MainFragmentPresenter.View {

    @BindView(R.id.mainRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.mainSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.mainEmptyCaseLayout)
    RelativeLayout emptyCaseLayout;
    @Inject

    MainFragmentPresenter presenter;

    private MainFragmentComponent mainFragmentComponent;

    private MainItemsAdapter adapter;

    @Override
    public MainFragmentComponent getComponent() {
        return null;
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected void initializeFragment(Bundle savedInstanceState) {
        //initializeInjector();
        initList();
        setListener();
        initPresenter();
    }

    private void setListener(){
        adapter.setListener(this);
    }
    private void initList(){
        adapter = new MainItemsAdapter(getContext());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initPresenter(){
        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    public void onRecyclerViewItemClick(RecyclerView recyclerView, View view, int adapterPosition) {

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
    public void showItems(List<HomeItems> homeItems) {
        adapter.addAll(homeItems);
    }
}
