/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.model;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.foodflix.helpers.constant.TableNames;
import com.example.foodflix.helpers.util.database.BaseDao;

import java.util.List;

import io.reactivex.Flowable;

/**
 * The interface Code dao.
 */
@Dao

public interface CodeDao extends BaseDao<Code> {
    /**
     * Gets all flowable codes.
     *
     * @return the all flowable codes
     */
    @Query("SELECT * FROM " + TableNames.CODES)
    Flowable<List<Code>> getAllFlowableCodes();

}
