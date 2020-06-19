/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.history;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodflix.R;
import com.example.foodflix.databinding.ItemHistoryBinding;
import com.example.foodflix.helpers.constant.AppConstants;
import com.example.foodflix.helpers.itemtouch.ItemTouchHelperAdapter;
import com.example.foodflix.helpers.model.Code;
import com.example.foodflix.helpers.util.TimeUtil;
import com.example.foodflix.helpers.util.database.DatabaseUtil;
import com.example.foodflix.ui.base.ItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * The type History adapter.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> implements ItemTouchHelperAdapter {
    /**
     * Fields
     */
    private List<Code> mItemList;
    private ItemClickListener<Code> mItemClickListener;

    /**
     * Instantiates a new History adapter.
     *
     * @param itemClickListener the item click listener
     */
    public HistoryAdapter(ItemClickListener<Code> itemClickListener) {
        mItemList = new ArrayList<>();
        mItemClickListener = itemClickListener;
    }

    private boolean isEqual(Code left, Code right) {
        return /*left.equals(right)*/false;
    }

    /**
     * Clear.
     */
    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    /**
     * Sets item list.
     *
     * @param itemList the item list
     */
    public void setItemList(List<Code> itemList) {
        mItemList = itemList;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<Code> getItems() {
        return mItemList;
    }

    /**
     * Remove item.
     *
     * @param item the item
     */
    public void removeItem(Code item) {
        int index = getItemPosition(item);
        if (index < 0 || index >= mItemList.size()) return;
        mItemList.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * Gets item.
     *
     * @param position the position
     * @return the item
     */
    public Code getItem(int position) {
        if (position < 0 || position >= mItemList.size()) return null;
        return mItemList.get(position);
    }

    /**
     * Gets item position.
     *
     * @param item the item
     * @return the item position
     */
    public int getItemPosition(Code item) {
        return mItemList.indexOf(item);
    }

    /**
     * Add item int.
     *
     * @param item the item
     * @return the int
     */
    public int addItem(Code item) {
        Code oldItem = findItem(item);

        if (oldItem == null) {
            mItemList.add(item);
            notifyItemInserted(mItemList.size() - 1);
            return mItemList.size() - 1;
        }

        return updateItem(item, item);
    }

    /**
     * Add item.
     *
     * @param items the items
     */
    public void addItem(List<Code> items) {
        for (Code item : items) {
            addItem(item);
        }
    }

    /**
     * Add item to position.
     *
     * @param item     the item
     * @param position the position
     */
    public void addItemToPosition(Code item, int position) {
        mItemList.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * Add item to position.
     *
     * @param item     the item
     * @param position the position
     */
    public void addItemToPosition(List<Code> item, int position) {
        mItemList.addAll(position, item);
        notifyItemRangeChanged(position, item.size());
    }

    /**
     * Find item code.
     *
     * @param item the item
     * @return the code
     */
    public Code findItem(Code item) {
        for (Code currentItem : mItemList) {
            if (isEqual(item, currentItem)) {
                return currentItem;
            }
        }
        return null;
    }

    /**
     * Update item int.
     *
     * @param oldItem the old item
     * @param newItem the new item
     * @return the int
     */
    public int updateItem(Code oldItem, Code newItem) {
        int oldItemIndex = getItemPosition(oldItem);
        mItemList.set(oldItemIndex, newItem);
        notifyItemChanged(oldItemIndex);
        return oldItemIndex;
    }

    /**
     * Update item int.
     *
     * @param newItem  the new item
     * @param position the position
     * @return the int
     */
    public int updateItem(Code newItem, int position) {
        mItemList.set(position, newItem);
        notifyItemChanged(position);
        return position;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Code item = getItem(position);

        if (item != null)
            holder.bind(item);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        AsyncTask.execute(() -> {
            DatabaseUtil.on().deleteEntity(getItem(position));
            mItemList.remove(position);
        });

    }

    /**
     * The type History view holder.
     */
    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemHistoryBinding mBinding;

        /**
         * Instantiates a new History view holder.
         *
         * @param itemBinding the item binding
         */
        HistoryViewHolder(@NonNull ItemHistoryBinding itemBinding) {
            super(itemBinding.getRoot());
            mBinding = itemBinding;
        }

        /**
         * Bind.
         *
         * @param item the item
         */
        void bind(Code item) {
            Context context = mBinding.getRoot().getContext();

            if (context != null) {
                Glide.with(context)
                        .asBitmap()
                        .apply(new RequestOptions()
                                .skipMemoryCache(false)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .load(item.getCodeImagePath())
                        .into(mBinding.imageViewCode);

                String scanType = String.format(Locale.ENGLISH,
                        context.getString(R.string.code_scan),
                        context.getResources().getStringArray(R.array.code_types)[item.getType()]);

                mBinding.textViewCodeType.setText(scanType);

                mBinding.textViewTime.setText(
                        TimeUtil.getFormattedDateString(item.getTimeStamp(),
                                AppConstants.APP_HISTORY_DATE_FORMAT));
            }

            mBinding.constraintLayoutContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getItem(getAdapterPosition()), getAdapterPosition());
            }
        }
    }
}
