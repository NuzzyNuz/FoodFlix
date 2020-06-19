/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.util.database;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import io.reactivex.Completable;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 */
public interface BaseDao<T> {
    /**
     * Insert a entity in the database. If the entity already exists, replace it.
     *
     * @param entity the entity to be inserted.
     * @return the completable
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(T entity);

    /**
     * Delete a entity in the database.
     *
     * @param entity the entity to be delete.
     * @return the int
     */
    @Delete
    int delete(T entity);
}
