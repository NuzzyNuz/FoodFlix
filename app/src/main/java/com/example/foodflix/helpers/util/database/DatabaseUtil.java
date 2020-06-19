/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.util.database;

import android.content.Context;

import com.example.foodflix.helpers.model.Code;
import com.example.foodflix.helpers.model.CodeDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * The type Database util.
 */
public class DatabaseUtil {
    /**
     * Fields
     */
    private static DatabaseUtil sInstance;
    private CodeDao mCodeDao;

    private DatabaseUtil() {
        setCodeDao(QrCobaDatabase.on().codeDao());
    }

    /**
     * This method builds an instance
     *
     * @param context the context
     */
    public static void init(Context context) {
        QrCobaDatabase.init(context);

        if (sInstance == null) {
            sInstance = new DatabaseUtil();
        }
    }

    /**
     * On database util.
     *
     * @return the database util
     */
    public static DatabaseUtil on() {
        if (sInstance == null) {
            sInstance = new DatabaseUtil();
        }

        return sInstance;
    }

    private CodeDao getCodeDao() {
        return mCodeDao;
    }

    private void setCodeDao(CodeDao codeDao) {
        mCodeDao = codeDao;
    }

    /**
     * Insert code completable.
     *
     * @param code the code
     * @return the completable
     */
    public Completable insertCode(Code code) {
        return getCodeDao().insert(code);
    }

    /**
     * Gets all codes.
     *
     * @return the all codes
     */
    public Flowable<List<Code>> getAllCodes() {
        return getCodeDao().getAllFlowableCodes();
    }

    /**
     * Delete entity int.
     *
     * @param code the code
     * @return the int
     */
    public int deleteEntity(Code code) {
        return getCodeDao().delete(code);
    }
}
