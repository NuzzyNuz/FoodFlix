/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.base;

import android.view.View;

/**
 * The interface Item click listener.
 *
 * @param <T> the type parameter
 */
public interface ItemClickListener<T> {
    /**
     * On item click.
     *
     * @param view     the view
     * @param item     the item
     * @param position the position
     */
    void onItemClick(View view, T item, int position);
}
