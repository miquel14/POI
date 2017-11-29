package com.worldline.template.view.adapter.viewholder;

import com.worldline.template.R;
import com.worldline.template.model.HomeItemModel;
import com.worldline.template.view.adapter.BaseRecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainItemsAdapter extends BaseRecyclerViewAdapter<MainItemsViewHolder>
        implements BaseClickViewHolder.OnViewHolderClickedListener {

    public MainItemsAdapter() {
        data = new ArrayList<>();
    }

    public interface onFavoriteClicked {

        void addItemFavoriteClicked(HomeItemModel item, int position);
    }

    private onFavoriteClicked onFavoriteClicked;

    @Override
    public MainItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainItemsViewHolder holder = new MainItemsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_viewholder, parent, false));
        holder.setListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MainItemsViewHolder holder, final int position) {
        if (data.get(position) instanceof HomeItemModel) {
            final HomeItemModel item = (HomeItemModel) data.get(position);
            holder.pointOfInterest.setText(item.getTitle());
            if (item.getDistanceInKm() != null){
                holder.distance.setVisibility(View.VISIBLE);
                holder.distance.setText(holder.distance.getContext().getString(R.string.distance_in_km, item.getDistanceInKm()));
            }
            else {
                holder.distance.setVisibility(View.INVISIBLE);
            }
            holder.favorite.setActivated(item.getFavorite());
            holder.favorite.setVisibility(View.VISIBLE);
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFavoriteClicked != null) {
                        onFavoriteClicked.addItemFavoriteClicked(item, holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setOnFavoriteClicked(onFavoriteClicked onFavoriteClicked) {
        this.onFavoriteClicked = onFavoriteClicked;
    }
}
