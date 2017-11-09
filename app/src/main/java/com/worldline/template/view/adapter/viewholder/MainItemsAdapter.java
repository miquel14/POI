package com.worldline.template.view.adapter.viewholder;


import com.worldline.template.R;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.presenter.MainFragmentPresenter;
import com.worldline.template.view.adapter.BaseRecyclerViewAdapter;
import com.worldline.template.view.fragment.MainFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.R.styleable.View;

public class MainItemsAdapter extends BaseRecyclerViewAdapter<MainItemsViewHolder>
        implements BaseClickViewHolder.OnViewHolderClickedListener {


    public MainItemsAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public MainItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainItemsViewHolder holder = new MainItemsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_viewholder,parent,false));
        holder.setListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainItemsViewHolder holder, int position) {
        if (data.get(position) instanceof HomeItemModel) {
            final HomeItemModel item = (HomeItemModel) data.get(position);
            holder.pointOfInterest.setText(item.getTitle());
            holder.id.setText((String.valueOf(item.getId())));
        }
    }

}
