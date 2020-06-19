/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.itemtouch;


/**
 * Interface to notify an item ViewHolder of relevant callbacks from {@link
 *
 * @author Paul Burke (ipaulpro)*
 */
public interface ItemTouchHelperViewHolder {

    /**
     * On item selected.
     */
    void onItemSelected();

    /**
     * On item clear.
     */
    void onItemClear();
}
