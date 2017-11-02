package com.worldline.template.view.adapter.viewholder;


import com.worldline.template.view.adapter.BaseRecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainItemsAdapter extends BaseRecyclerViewAdapter<ViewHolder> {
    public MainItemsAdapter(Context context) {
        this.context = context;
    }

    public enum MainItemType{;
       // LLOC1(R.s)
        public int getStringResId() {
            return stringResId;
        }

        private final int stringResId;

        MainItemType(int stringResId) {
            this.stringResId = stringResId;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // ViewHolder holder = new ViewHolder()
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    private final Context context;

    private final List<MainItemType> items = new ArrayList<>();
    @Override
    public int getItemCount() {
        return 0;
    }
}
