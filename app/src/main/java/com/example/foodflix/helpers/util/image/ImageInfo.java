/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.util.image;

import android.net.Uri;

/**
 * The type Image info.
 */
public class ImageInfo {
    private Uri mImageUri;
    private boolean mTakenByCamera;

    /**
     * Instantiates a new Image info.
     */
    public ImageInfo() {
    }

    /**
     * Instantiates a new Image info.
     *
     * @param imageUri      the image uri
     * @param takenByCamera the taken by camera
     */
    public ImageInfo(Uri imageUri, boolean takenByCamera) {
        mImageUri = imageUri;
        mTakenByCamera = takenByCamera;
    }

    /**
     * Gets image uri.
     *
     * @return the image uri
     */
    public Uri getImageUri() {
        return mImageUri;
    }

    /**
     * Sets image uri.
     *
     * @param imageUri the image uri
     */
    public void setImageUri(Uri imageUri) {
        mImageUri = imageUri;
    }

    /**
     * Is taken by camera boolean.
     *
     * @return the boolean
     */
    public boolean isTakenByCamera() {
        return mTakenByCamera;
    }

    /**
     * Sets taken by camera.
     *
     * @param takenByCamera the taken by camera
     */
    public void setTakenByCamera(boolean takenByCamera) {
        mTakenByCamera = takenByCamera;
    }
}
