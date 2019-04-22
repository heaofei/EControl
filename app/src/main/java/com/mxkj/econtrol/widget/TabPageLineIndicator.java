package com.mxkj.econtrol.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

public class TabPageLineIndicator extends TabLayout {


    public TabPageLineIndicator(Context context) {
        super(context);

    }

    public TabPageLineIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabPageLineIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getMeasuredWidth();
        int width = (w - (getTabCount() - 1) * 10)/getTabCount();
        int x = 0;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
        paint.setColor(0xffcccccc);
        for (int i = 0; i < getTabCount(); i++) {
            canvas.drawLine(x, 0, x + width, 0, paint);
            x += width+10;
        }
//        setIndicator(getContext());
    }


    public void setIndicator(Context context) {
        Class<?> tabLayout = this.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            if(i==0){
                continue;
            }
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = 8;
            params.rightMargin = 8;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}



