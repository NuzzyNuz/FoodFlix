/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.helpers.widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;

/**
 * The type Base status bar.
 */
public class BaseStatusBar extends View {
    private int mStatusBarHeight;

    /**
     * Instantiates a new Base status bar.
     *
     * @param context the context
     */
    public BaseStatusBar(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Base status bar.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public BaseStatusBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBarHeight = dpToPx();
            return insets.consumeSystemWindowInsets();
        }
        return insets;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight);
    }

    private int dpToPx() {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round((float) 24.0 * density);
    }
}
