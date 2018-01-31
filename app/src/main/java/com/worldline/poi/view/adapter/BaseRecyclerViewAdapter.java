package com.worldline.template.view.adapter;

import com.worldline.template.model.HomeItemModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;


/**
 * A generic RecyclerView definition including an onClickListener similar to a ListView's one.
 * BaseClickViewHolder
 */
public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected List<Object> data;

    private OnItemClickedListener listener;

    private RecyclerView recyclerView;

    public void setListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.stopScroll();
        this.recyclerView = null;
    }

    public void onViewHolderClick(View view, int adapterPosition) {
        if (listener != null) {
            listener.onRecyclerViewItemClick(recyclerView, view, adapterPosition);
        }
    }

    @Override
    public long getItemId(int position) {
        if (hasStableIds()) {
            return position;
        } else {
            return super.getItemId(position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<? extends HomeItemModel> list) {
        int positionStart = data.size();
        data.addAll(list);
        notifyItemRangeInserted(positionStart, list.size());
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<? extends Object> getData() {
        return data;
    }

    public Object getItemAtPosition(int position) {
        Object result;
        try {
            result = data.get(position);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public List<HomeItemModel> getItems() {
        return (List<HomeItemModel>) getData();
    }

    public void addAtPosition(int position, HomeItemModel model) {
        getItems().add(position, model);
        notifyItemInserted(position);
    }


    public int getPosition(Object item) {
        return data.indexOf(item);
    }


    /**
     * A generic interface definition for a callback to be invoked when an item in this RecyclerView has been clicked.
     */
    public interface OnItemClickedListener {

        /**
         * Callback method to be invoked when an item in this RecyclerView has been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to access the data associated with the selected item.
         *
         * @param recyclerView    The RecyclerView where the click happened.
         * @param view            The view within the RecyclerView that was clicked (this will be a view provided by the
         *                        adapter).
         * @param adapterPosition The position of the view in the adapter.
         */
        void onRecyclerViewItemClick(RecyclerView recyclerView, View view, int adapterPosition);
    }
}
