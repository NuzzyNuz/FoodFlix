/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.util.database;

import android.content.Context;

import androidx.room.Database;

import com.example.foodflix.R;
import com.example.foodflix.helpers.model.Code;
import com.example.foodflix.helpers.model.CodeDao;


/**
 * The type Qr coba database.
 */
@Database(entities = {Code.class},
        version = 1, exportSchema = false)
public abstract class QrCobaDatabase extends AppDatabase {

    private static volatile QrCobaDatabase sInstance;

    /**
     * On qr coba database.
     *
     * @return the qr coba database
     */
// Get a database instance
    public static synchronized QrCobaDatabase on() {
        return sInstance;
    }

    /**
     * Init.
     *
     * @param context the context
     */
    public static synchronized void init(Context context) {

        if (sInstance == null) {
            synchronized (QrCobaDatabase.class) {
                sInstance = createDb(context, context.getString(R.string.app_name), QrCobaDatabase.class);
            }
        }
    }

    /**
     * Code dao code dao.
     *
     * @return the code dao
     */
    public abstract CodeDao codeDao();
}
