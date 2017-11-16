package com.worldline.template.view.fragment;

import com.worldline.template.AndroidApplication;
import com.worldline.template.R;
import com.worldline.template.internal.di.HasComponent;
import com.worldline.template.internal.di.component.DaggerDetailFragmentComponent;
import com.worldline.template.internal.di.component.DetailFragmentComponent;
import com.worldline.template.internal.di.module.ItemDetailFragmentModule;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.presenter.DetailFragmentPresenter;
import com.worldline.template.presenter.Presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailFragment extends RootFragment implements HasComponent<DetailFragmentComponent>, DetailFragmentPresenter.View {

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.transport)
    TextView transport;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.phone)
    TextView phone;

    private DetailFragmentComponent component;

    @Inject
    DetailFragmentPresenter presenter;

    private int id;

    public DetailFragmentComponent getComponent() {
        return component;
    }

    public static DetailFragment newInstance(int id) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("PARAM_ID", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initializeFragment(Bundle savedInstanceState) {
        if (getArguments() != null) {
            id = getArguments().getInt("PARAM_ID");
        }
        initInjector();
        initPresenter();
    }

    private void initInjector() {
        this.component = DaggerDetailFragmentComponent.builder()
                .applicationComponent(((AndroidApplication) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(getFragmentModule())
                .itemDetailFragmentModule(new ItemDetailFragmentModule())
                .build();
        this.component.inject(this);
    }

    private void initPresenter() {
        presenter.setView(this);
        presenter.start();
        presenter.getDetail();
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
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

    public void showItem(HomeItemModel item) {
        title.setText(item.getTitle());
        show(transport, item.getTransport());
        show(phone, item.getPhone());
        show(description, item.getDescription());
        show(email, item.getEmail());
        show(address, item.getAddress());
    }
    //TODO fer mètode on comprovar els strings no permesos
    public void show(TextView tv, String value) {
        if (!TextUtils.isEmpty(value) && !value.equals("null")) {
            tv.setText(value);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    public String getItemId() {
        return String.valueOf(id);
    }
}
