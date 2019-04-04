/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack7.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class DrawView extends View {
    private Rectangle mRectangle;
    public int width;
    public int height;

    public DrawView(Context context) {
        super(context);

        //创建方块对象
        mRectangle = new Rectangle(context, this);
        mRectangle.setARGB(255, 255, 0, 0);
        mRectangle.setSpeedX(3);
        mRectangle.setSpeedY(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //变换方块位置
        mRectangle.move();

        //将方块绘制到Canvas上
        mRectangle.draw(canvas);

        //重绘view
        // invalidate()这个方法可以强制重绘视图，在onDraw()方法中一般通过调用invalidate()方法变换视图的位置实现自定义动画。
        invalidate();
    }

}
