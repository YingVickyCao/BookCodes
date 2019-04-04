/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack19.test;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.hades.androidhacks.hack19.test.util.ThreadUtils;

public class SlowDrawView extends AppCompatTextView {

    public SlowDrawView(Context context) {
        super(context);
    }

    public SlowDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlowDrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    /*
     * Please remember this is just a hack for the purpose of the demonstration.
     * Do not block the activity_hack_19 thread in production code as it may fire an
     * "Application Not Responding" dialog.
     */
        ThreadUtils.sleep();
        super.onDraw(canvas);
    }
}
