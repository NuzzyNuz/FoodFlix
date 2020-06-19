/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.foodflix.databinding.ProgresssDialogLayoutBinding;

/**
 * The type Progress dialog util.
 */
public class ProgressDialogUtil {

    private static ProgressDialogUtil sInstance;
    private AlertDialog mAlertDialog;

    private ProgressDialogUtil() {

    }

    /**
     * On progress dialog util.
     *
     * @return the progress dialog util
     */
    public static ProgressDialogUtil on() {
        if (sInstance == null) {
            sInstance = new ProgressDialogUtil();
        }

        return sInstance;
    }

    /**
     * Show progress dialog.
     *
     * @param context the context
     */
    public void showProgressDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        ProgresssDialogLayoutBinding binding =
                ProgresssDialogLayoutBinding.inflate(LayoutInflater.from(context),
                        null, false);

        binding.textViewMessage.setTypeface(null, Typeface.NORMAL);

        builder.setCancelable(false);
        builder.setView(binding.getRoot());

        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    /**
     * Hide progress dialog.
     */
    public void hideProgressDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }
}
