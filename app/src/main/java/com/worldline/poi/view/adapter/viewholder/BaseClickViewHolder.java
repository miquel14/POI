package com.worldline.template.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * A generic ViewHolder definition including an interface for item clicks.
 * <p>
 * RecyclerViewAdapters should extend {@link com.worldline.template.view.adapter.BaseRecyclerViewAdapter}
 * if they need to provide the ViewHolder's events to the UI.
 */
public abstract class BaseClickViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    /**
     * A generic interface definition for a callback to be invoked when the ViewHolder's view has
     * been clicked.
     */
    public interface OnViewHolderClickedListener {

        /**
         * Callback method to be invoked when the item (ViewHolder) has been clicked.
         *
         * @param view            The view that was clicked.
         * @param adapterPosition The position of the view in the adapter.
         */
        void onViewHolderClick(View view, int adapterPosition);
    }

    protected OnViewHolderClickedListener listener;

    public void setListener(OnViewHolderClickedListener listener) {
        this.listener = listener;
    }

    public BaseClickViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onViewHolderClick(v, getAdapterPosition());
        }
    }
}
