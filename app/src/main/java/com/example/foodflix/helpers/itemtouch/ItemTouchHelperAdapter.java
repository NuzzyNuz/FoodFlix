/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.itemtouch;


/**
 * The interface Item touch helper adapter.
 */
public interface ItemTouchHelperAdapter {

    /**
     * On item move boolean.
     *
     * @param fromPosition the from position
     * @param toPosition   the to position
     * @return the boolean
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * On item dismiss.
     *
     * @param position the position
     */
    void onItemDismiss(int position);
}
