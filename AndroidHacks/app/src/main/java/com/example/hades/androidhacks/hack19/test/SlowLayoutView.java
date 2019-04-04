/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack19.test;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.hades.androidhacks.hack19.test.util.ThreadUtils;

public class SlowLayoutView extends AppCompatTextView {

    public SlowLayoutView(Context context) {
        super(context);
    }

    public SlowLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlowLayoutView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top,
                            int right, int bottom) {
    /*
     * Please remember this is just a hack for the purpose of the demonstration.
     * Do not block the activity_hack_19 thread in production code as it may fire an
     * "Application Not Responding" dialog.
     */
        ThreadUtils.sleep();
        super.onLayout(changed, left, top, right, bottom);
    }
}
