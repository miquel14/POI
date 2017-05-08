package com.worldline.template.view.adapter;

import com.worldline.template.view.adapter.viewholder.BaseClickViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * A generic RecyclerView definition including an onClickListener similar to a ListView's one.
 * BaseClickViewHolder
 */
public abstract class BaseRecyclerViewAdapter <T extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T> implements
        BaseClickViewHolder.OnViewHolderClickedListener {

    /**
     * A generic interface definition for a callback to be invoked when an item in this
     * RecyclerView
     * has been clicked.
     */
    public interface OnItemClickedListener {

        /**
         * Callback method to be invoked when an item in this RecyclerView has been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to access the data
         * associated
         * with the selected item.
         *
         * @param recyclerView    The RecyclerView where the click happened.
         * @param view            The view within the RecyclerView that was clicked (this will be a
         *                        view
         *                        provided by the adapter).
         * @param adapterPosition The position of the view in the adapter.
         */
        void onRecyclerViewItemClick(RecyclerView recyclerView, View view, int adapterPosition);
    }

    private OnItemClickedListener listener;

    public void setListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    private RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public void onViewHolderClick(View view, int adapterPosition) {
        if (listener != null) {
            listener.onRecyclerViewItemClick(recyclerView, view, adapterPosition);
        }
    }
}
