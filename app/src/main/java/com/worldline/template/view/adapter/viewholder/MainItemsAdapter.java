package com.worldline.template.view.adapter.viewholder;


import com.worldline.data.GeoConstant;
import com.worldline.template.R;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.view.adapter.BaseRecyclerViewAdapter;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

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
            holder.id.setText((String.valueOf(item.getId())));
            holder.distance.setText(holder.distance.getContext().getString(R.string.distance_in_km, distance(item.getGeoCoordinates())));
        }
    }

    private String distance(String coordinates) {
        String[] coordinatesList = coordinates.split(",");
        String latitude = coordinatesList[0];
        String longitude = coordinatesList[1];

        Location loc1 = new Location("");
        loc1.setLongitude(Double.parseDouble(longitude));
        loc1.setLatitude(Double.parseDouble(latitude));

        Location loc2 = new Location("");
        loc2.setLatitude(GeoConstant.latitude);
        loc2.setLongitude(GeoConstant.longitude);

        float distanceMeters = loc2.distanceTo(loc1);
        float distanceKm = distanceMeters / 1000;
        return String.format("%.2f", distanceKm);
    }

}
