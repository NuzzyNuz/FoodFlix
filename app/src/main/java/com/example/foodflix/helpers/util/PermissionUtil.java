/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.fragment.app.Fragment;

import com.example.foodflix.FoodFlixApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Permission util.
 */
public class PermissionUtil {
    /**
     * The constant REQUEST_CODE_PERMISSION_DEFAULT.
     */
    public static final int REQUEST_CODE_PERMISSION_DEFAULT = 1;
    private static PermissionUtil sInstance;

    private PermissionUtil() {

    }

    /**
     * On permission util.
     *
     * @return the permission util
     */
    public static PermissionUtil on() {
        if (sInstance == null) {
            sInstance = new PermissionUtil();
        }

        return sInstance;
    }

    /**
     * Request permission boolean.
     *
     * @param activity    the activity
     * @param permissions the permissions
     * @return the boolean
     */
    public synchronized boolean requestPermission(Activity activity, String... permissions) {
        return requestPermission(null, activity,
                REQUEST_CODE_PERMISSION_DEFAULT, Arrays.asList(permissions));
    }

    /**
     * Request permission boolean.
     *
     * @param fragment    the fragment
     * @param permissions the permissions
     * @return the boolean
     */
    public synchronized boolean requestPermission(Fragment fragment, String... permissions) {
        return requestPermission(fragment, null, REQUEST_CODE_PERMISSION_DEFAULT, Arrays.asList(permissions));
    }

    /**
     * Request permission boolean.
     *
     * @param activity    the activity
     * @param requestCode the request code
     * @param permissions the permissions
     * @return the boolean
     */
    public synchronized boolean requestPermission(Activity activity, int requestCode,
                                                  String... permissions) {
        return requestPermission(null, activity, requestCode, Arrays.asList(permissions));
    }

    /**
     * Request permission boolean.
     *
     * @param fragment    the fragment
     * @param requestCode the request code
     * @param permissions the permissions
     * @return the boolean
     */
    public synchronized boolean requestPermission(Fragment fragment, int requestCode, String... permissions) {
        return requestPermission(fragment, null, requestCode, Arrays.asList(permissions));
    }

    private boolean requestPermission(Fragment fragment, Activity activity,
                                      int requestCode, List<String> permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        List<String> permissionsNotTaken = new ArrayList<>();

        for (int i = 0; i < permissions.size(); i++) {
            if (!isAllowed(permissions.get(i))) {
                permissionsNotTaken.add(permissions.get(i));
            }
        }

        if (permissionsNotTaken.isEmpty()) {
            return true;
        }

        if (fragment == null) {
            activity.requestPermissions(permissionsNotTaken.toArray(new String[permissionsNotTaken.size()]), requestCode);
        } else {
            fragment.requestPermissions(permissionsNotTaken.toArray(new String[permissionsNotTaken.size()]), requestCode);
        }

        return false;
    }

    /**
     * Is allowed boolean.
     *
     * @param permission the permission
     * @return the boolean
     */
    boolean isAllowed(String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        return FoodFlixApplication.getContext().checkSelfPermission(permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}
