package com.worldline.template.view.adapter.viewholder;

import com.worldline.template.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainItemsViewHolder extends BaseClickViewHolder {

    public MainItemsViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @BindView(R.id.pointOfInterestTitle)
    public TextView pointOfInterest;

    @BindView(R.id.distance)
    public TextView distance;

    @BindView(R.id.ivFavorite)
    public ImageView favorite;
}

