package com.example.foodflix.helpers.model;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.foodflix.helpers.constant.TableNames;
import com.example.foodflix.helpers.util.database.BaseDao;

import java.util.List;

import io.reactivex.Flowable;

@Dao

public interface CodeDao extends BaseDao<Code> {
    @Query("SELECT * FROM " + TableNames.CODES)
    Flowable<List<Code>> getAllFlowableCodes();

}
