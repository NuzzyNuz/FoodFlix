/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.foodflix.helpers.util.SharedPrefUtil;
import com.example.foodflix.helpers.util.database.DatabaseUtil;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;


/**
 * The type Food flix application.
 */
public class FoodFlixApplication extends MultiDexApplication {

    private static FoodFlixApplication sInstance;

    /**
     * Gets context.
     *
     * @return the context
     */
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        SharedPrefUtil.init(getApplicationContext());
        DatabaseUtil.init(getApplicationContext());
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
