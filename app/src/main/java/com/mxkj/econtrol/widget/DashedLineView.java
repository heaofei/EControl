package com.mxkj.econtrol.widget;

/**
 * Created by liangshan on 2017/6/12.
 *
 * @Description：
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2015/12/22.
 */
public class DashedLineView extends View {

    public DashedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffe6e6e6);//颜色可以自己设置
        paint.setStrokeWidth(getMeasuredWidth());
        Path path = new Path();
        path.moveTo(0, 0);//起始坐标
        path.lineTo(0,getMeasuredHeight());//终点坐标
        PathEffect effects = new DashPathEffect(new float[]{20,10}, 10);//设置虚线的间隔和点的长度
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}