package com.mxkj.econtrol.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mxkj.econtrol.utils.ScreenUtil;

/**
 * Created by liangshan on 2017/3/25.
 *
 * @Destription:自动填充满的布局管理器
 */

public class GridLayoutManager extends RecyclerView.LayoutManager {

    private int rows, cloumn;


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        detachAndScrapAttachedViews(recycler);
        int offsetX = 0;
        int offsetY = 0;

        int count = getItemCount();
        int width = getWidth();
        int height = getHeight();
        cloumn = (int) Math.round(Math.sqrt(count));
        rows = count % cloumn == 0 ? count / cloumn : count / cloumn + 1;

        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = (height - ScreenUtil.dip2px(1) * (rows - 1)) / rows;

            if (i == count - 1) {
                layoutParams.width = width - offsetX;
            } else {
                layoutParams.width = (width - ScreenUtil.dip2px(1) * (cloumn - 1)) / cloumn;
            }
            view.setLayoutParams(layoutParams);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int childH = getDecoratedMeasuredHeight(view);
            int childw = getDecoratedMeasuredWidth(view);
            layoutDecorated(view, offsetX, offsetY, offsetX + childw, offsetY + childH);
            offsetX = offsetX + width / cloumn + ScreenUtil.dip2px(1);

            if (offsetX >= width) {
                offsetX = 0;
                offsetY = offsetY + height / rows + ScreenUtil.dip2px(1);
            }


        }
    }


}
