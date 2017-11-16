package com.worldline.template.view.fragment;


import com.worldline.template.AndroidApplication;
import com.worldline.template.R;
import com.worldline.template.internal.di.HasComponent;
import com.worldline.template.internal.di.component.DaggerMainFragmentComponent;
import com.worldline.template.internal.di.component.MainFragmentComponent;
import com.worldline.template.internal.di.module.MainFragmentModule;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.presenter.MainFragmentPresenter;
import com.worldline.template.presenter.Presenter;
import com.worldline.template.view.adapter.BaseRecyclerViewAdapter;
import com.worldline.template.view.adapter.viewholder.MainItemsAdapter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainFragment extends RootFragment implements HasComponent<MainFragmentComponent>,
        BaseRecyclerViewAdapter.OnItemClickedListener,
        MainFragmentPresenter.View, SwipeRefreshLayout.OnRefreshListener {

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
        return mainFragmentComponent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initializeFragment(Bundle savedInstanceState) {
        initializeInjector();
        initList();
        setListener();
        initPresenter();
    }

    void initializeInjector() {
        mainFragmentComponent = DaggerMainFragmentComponent.builder()
                .applicationComponent(((AndroidApplication) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(getFragmentModule())
                .mainFragmentModule(new MainFragmentModule())
                .build();
        mainFragmentComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter.setListener(this);
    }

    private void initList() {
        adapter = new MainItemsAdapter();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void initPresenter() {
        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onRecyclerViewItemClick(RecyclerView recyclerView, View view, int adapterPosition) {
        presenter.gotoDetail(((HomeItemModel) adapter.getItemAtPosition(adapterPosition)).getId(),
                ((HomeItemModel) adapter.getItemAtPosition(adapterPosition)).getTitle());
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
        emptyCaseLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showItems(List<HomeItemModel> homeItems) {
        emptyCaseLayout.setVisibility(View.GONE);
        adapter.clear();
        Collections.sort(homeItems,presenter.comp);
        adapter.notifyDataSetChanged();
        adapter.addAll(homeItems);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.getHomeItems();
    }
}
