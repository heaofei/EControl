package com.mxkj.econtrol.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by ${  chenjun  } on 2018/8/15.
 * 重写ExpandableListView以解决ScrollView嵌套ExpandableListView
 *<br> 导致ExpandableListView显示不正常的问题
 */

public class NestedExpandaleListView extends ExpandableListView {



    public NestedExpandaleListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public NestedExpandaleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NestedExpandaleListView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
