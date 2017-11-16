package com.worldline.template.view.adapter.viewholder;


import com.worldline.data.GeoConstant;
import com.worldline.domain.model.HomeItem;
import com.worldline.template.R;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.view.adapter.BaseRecyclerViewAdapter;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Comparator;

public class MainItemsAdapter extends BaseRecyclerViewAdapter<MainItemsViewHolder>
        implements BaseClickViewHolder.OnViewHolderClickedListener {


    public MainItemsAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public MainItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainItemsViewHolder holder = new MainItemsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_viewholder, parent, false));
        holder.setListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainItemsViewHolder holder, int position) {
        if (data.get(position) instanceof HomeItemModel) {
            final HomeItemModel item = (HomeItemModel) data.get(position);
            holder.pointOfInterest.setText(item.getTitle());
            holder.distance.setText(holder.distance.getContext().getString(R.string.distance_in_km, item.getDistanceInKm()));
        }
    }
}
