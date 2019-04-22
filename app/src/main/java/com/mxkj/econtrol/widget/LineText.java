package com.mxkj.econtrol.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.utils.ScreenUtil;

/**
 * Created by liangshan on 2017/3/27.
 *
 * @Description:两边是直线中间是文本的控件
 */

public class LineText extends LinearLayout {

    //左边直线
    private View mLeftLine;
    //右边直线
    private View mRightLine;
//    中间文本
    private TextView mText;
    //线的颜色
    private int mLineColor;
    //线的高度
    private int mLineHeight;
    //文本的颜色
    private int mTextColor;
    //文本的大小
    private float mTetxtSize;

    public LineText(Context context) {
        this(context,null);
    }

    public LineText(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public LineText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(LinearLayout.HORIZONTAL);
        mLeftLine=new View(context);
        mRightLine=new View(context);
        mText=new TextView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineText,defStyleAttr,0);
        mTextColor=typedArray.getColor(R.styleable.LineText_textColor, Color.GRAY);
        String text=typedArray.getString(R.styleable.LineText_text);
        mTetxtSize=typedArray.getDimensionPixelSize(R.styleable.LineText_textSize, 20);
        mText.setText(text);
        mText.setTextColor(mTextColor);
        mText.setTextSize(mTetxtSize);
        mLineColor=typedArray.getColor(R.styleable.LineText_lineColor,Color.GRAY);
        mLineHeight=typedArray.getDimensionPixelSize(R.styleable.LineText_lineHeight,ScreenUtil.dip2px(1));
        LinearLayout.LayoutParams lineLP=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lineLP.width=0;
        lineLP.weight=2;
        lineLP.gravity= Gravity.CENTER_VERTICAL;
        lineLP.height=mLineHeight;
        mRightLine.setLayoutParams(lineLP);
        mRightLine.setBackgroundColor(mLineColor);
        mLeftLine.setLayoutParams(lineLP);
        mLeftLine.setBackgroundColor(mLineColor);
        LinearLayout.LayoutParams textLP=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        textLP.leftMargin=ScreenUtil.dip2px(10);
        textLP.rightMargin=ScreenUtil.dip2px(10);
        textLP.gravity=Gravity.CENTER_HORIZONTAL;
        mText.setLayoutParams(textLP);
        this.addView(mLeftLine);
        this.addView(mText);
        this.addView(mRightLine);



    }

    public TextView getTextView(){
        return mText;
    }
    public View getLeftLine(){
        return  mLeftLine;
    }
    public View getmRightLine(){
        return mRightLine;
    }
}
