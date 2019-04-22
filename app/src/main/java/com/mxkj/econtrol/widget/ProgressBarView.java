package com.mxkj.econtrol.widget;

/**
 * Created by liangshan on 2017/5/23.
 *
 * @Description：
 */

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.utils.LogUtil;

/**
 * 自定义绚丽的ProgressBar.
 */
public class ProgressBarView extends View {
    /**
     * 进度条所占用的角度
     */
    private static final int ARC_FULL_DEGREE = 360;
    /**
     * 弧线的宽度
     */
    private int STROKE_WIDTH;


    /**
     * 组件的宽，高
     */
    private int width, height;
    /**
     * 进度条最大值和当前进度值
     */
    private float min = 0, max = 12, progress;


    /**
     * 是否允许拖动进度条
     */
    private boolean draggingEnabled = false;


    /**
     * 绘制弧线的矩形区域
     */
    private RectF circleRectF;


    /**
     * 绘制弧线的画笔
     */
    private Paint progressPaint;
    /**
     * 绘制文字的画笔
     */
    private Paint textPaint;
    /**
     * 绘制当前进度值的画笔
     */
    private Paint thumbPaint;

    /**
     * /**
     * 圆弧的半径
     */
    private int circleRadius;
    /**
     * 圆弧圆心位置
     */
    private int centerX, centerY;

    private ViewPager parent;

    private OnProgressChange mOnProgressChange;


    public ProgressBarView(Context context) {
        super(context);
        init();
    }


    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);


        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);


        thumbPaint = new Paint();
        thumbPaint.setAntiAlias(true);


        //使用自定义字体
//        textPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fangz.ttf"));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    private Rect textBounds = new Rect();


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (width == 0 || height == 0) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();

            //计算圆弧半径和圆心点
            circleRadius = Math.min((int) (width - width * 0.168), (int) (height - height * 0.168)) / 2;
            STROKE_WIDTH = circleRadius / 8;
            circleRadius -= STROKE_WIDTH;
            centerX = width / 2;
            centerY = height / 2;
            //圆弧所在矩形区域
            circleRectF = new RectF();
            circleRectF.left = centerX - circleRadius;
            circleRectF.top = centerY - circleRadius;
            circleRectF.right = centerX + circleRadius;
            circleRectF.bottom = centerY + circleRadius;
        }
        float start = -90 + ((360 - ARC_FULL_DEGREE) >> 1); //进度条起始点
        float sweep1 = ARC_FULL_DEGREE * (progress / max); //进度划过的角度
        float sweep2 = ARC_FULL_DEGREE - sweep1; //剩余的角度

        //绘制背景圆
        Paint paint = new Paint();
        LinearGradient shader = null;
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffe6e6e6);
        canvas.drawCircle(centerX, centerY, Math.min(width, height) / 2 - 3, paint);


        //绘制进度条背景
        progressPaint.setStrokeWidth(STROKE_WIDTH);
        progressPaint.setStyle(Paint.Style.STROKE);//设置空心
        //第一、二象限
        shader = new LinearGradient(centerX, centerY - circleRadius, centerX, centerY + circleRadius, 0xffaff9fc, 0xfff3bbf8, Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);
        canvas.drawArc(circleRectF, -90, 181, false, progressPaint);
        //第三、四象限
        shader = new LinearGradient(centerX, centerY + circleRadius, centerX, centerY - circleRadius, 0xfff3bbf8, 0xfffebad1, Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);
        canvas.drawArc(circleRectF, 90, 180, false, progressPaint);


        //绘制进度条
        shader = new LinearGradient(centerX, centerY - circleRadius, centerX, centerY + circleRadius, 0xff00f0ff, 0xffD929EF, Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);
        //第一、二象限
        if (sweep1 >= 0 && sweep1 <= 180) {
            canvas.drawArc(circleRectF, start, sweep1, false, progressPaint);
        } else if (sweep1 > 180) {
            canvas.drawArc(circleRectF, start, 180, false, progressPaint);
        }

        //第三、四象限
        shader = new LinearGradient(centerX, centerY + circleRadius, centerX, centerY - circleRadius, 0xffD92AF5, 0xfff62973, Shader.TileMode.CLAMP);
        progressPaint.setShader(shader);
        if (sweep1 > 180)
            canvas.drawArc(circleRectF, 90 - 8, sweep1 - 180, false, progressPaint);
        //绘制起始位置小圆形
        progressPaint.setColor(Color.WHITE);
        progressPaint.setStrokeWidth(0);
        progressPaint.setStyle(Paint.Style.FILL);
        float radians = (float) (((360.0f - ARC_FULL_DEGREE) / 2) / 180 * Math.PI);
        progressPaint.setShader(null);
        progressPaint.setColor(0xff00f0ff);
        progressPaint.setStyle(Paint.Style.FILL);
        float startX = centerX + circleRadius * (float) Math.sin(radians);
        float startY = centerY - circleRadius * (float) Math.cos(radians);
        canvas.drawCircle(startX, startY, STROKE_WIDTH / 2, progressPaint);

        //上一行文字
        textPaint.setTextSize(circleRadius >> 1);
        textPaint.setColor(0xfff72971);
        String text = (int) (progress) + 19 + "";
        float textLen = textPaint.measureText(text);
        //计算文字高度
        textPaint.getTextBounds("8", 0, 1, textBounds);
        float h1 = textBounds.height();
        //前面的数字水平居中，适当调整
        float extra = text.startsWith("1") ? -textPaint.measureText("1") / 2 : 0;
        canvas.drawText(text, centerX - textLen / 2 + extra, centerY - 30 + h1 / 2, textPaint);
        //号
        textPaint.setTextSize(circleRadius >> 2);
        canvas.drawText("℃", centerX + textLen / 2 + extra + 10, centerY - 30 + h1 / 2 - 50, textPaint);
        //下一行文字
        textPaint.setTextSize(circleRadius / 5);
        textPaint.setColor(0xff333333);
        text = getContext().getString(R.string.temp);
        textLen = textPaint.measureText(text);
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        float h2 = textBounds.height();
        canvas.drawText(text, centerX - textLen / 2, centerY + h1 / 2 + h2, textPaint);


        //绘制进度位置，也可以直接替换成一张图片
        float progressRadians = (float) (((360.0f - ARC_FULL_DEGREE) / 2 + sweep1) / 180 * Math.PI);
        float thumbX = centerX + circleRadius * (float) Math.sin(progressRadians);
        float thumbY = centerY - circleRadius * (float) Math.cos(progressRadians);
        thumbPaint.setColor(0xffe5e5e5);
        canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 1.4f + 2, thumbPaint);
        thumbPaint.setColor(Color.WHITE);
        thumbPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        thumbPaint.setStrokeWidth(2);
        canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 1.4f, thumbPaint);

    }

    private boolean isDragging = false;


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (parent != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    parent.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    parent.requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }
        if (!draggingEnabled) {
            return super.onTouchEvent(event);
        }


        //处理拖动事件
        float currentX = event.getX();
        float currentY = event.getY();


        int action = event.getAction();


        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //判断是否在进度条thumb位置
                if (checkOnArc(currentX, currentY) && checkOnThumb(currentX, currentY)) {
                    float newProgress = calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max;
                    // setProgressSync(newProgress);
                    isDragging = true;
                }
                break;


            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    //判断拖动时是否移出去了
                    if (checkOnArc(currentX, currentY)) {
                        setProgressSync(calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max);
                    } else {
                        isDragging = false;
                        if (mOnProgressChange != null) {
                            mOnProgressChange.OnProgressChange(progress + 19);
                        }
                    }
                }

                break;


            case MotionEvent.ACTION_UP:

                if (mOnProgressChange != null && isDragging) {
                    mOnProgressChange.OnProgressChange(progress + 19);
                } else if (mOnProgressChange != null && !isDragging) {
                    if (progress > 0 && progress < 1 || progress > (int)(350 * max / ARC_FULL_DEGREE)) {
                        mOnProgressChange.OnProgressChange(progress + 19);
                    }
                }

                isDragging = false;
                break;
        }


        return true;
    }


    private float calDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }


    /**
     * 判断该点是否在弧线上（附近）
     */
    private boolean checkOnArc(float currentX, float currentY) {
        float distance = calDistance(currentX, currentY, centerX, centerY);
        float degree = calDegreeByPosition(currentX, currentY);
        return distance > circleRadius - STROKE_WIDTH * 5 && distance < circleRadius + STROKE_WIDTH * 5
                && (degree >= -8 && degree <= 350);
    }

    /**
     * 判断该点是否在拖动点上（附近）
     */
    private boolean checkOnThumb(float currentX, float currentY) {
        float progressRadians = (float) (((360.0f - ARC_FULL_DEGREE) / 2 + ARC_FULL_DEGREE * (progress / max)) / 180 * Math.PI);
        float thumbX = centerX + circleRadius * (float) Math.sin(progressRadians);
        float thumbY = centerY - circleRadius * (float) Math.cos(progressRadians);
        double num = Math.pow(Math.abs(currentX - thumbX), 2) + Math.pow(Math.abs(currentY - thumbY), 2);
        return Math.sqrt(num) < STROKE_WIDTH * 1.4;

    }

    /**
     * 根据当前位置，计算出进度条已经转过的角度。
     */
    private float calDegreeByPosition(float currentX, float currentY) {
        float a1 = (float) (Math.atan(1.0f * (centerX - currentX) / (currentY - centerY)) / Math.PI * 180);
        if (currentY < centerY && currentX > centerX) {
            a1 += 0;
        } else if (currentY < centerY && currentX < centerX) {
            a1 += 360;
        } else {
            a1 += 180;
        }

        System.out.println("=======================" + (a1 - (360 - ARC_FULL_DEGREE) / 2));
        return a1 - (360 - ARC_FULL_DEGREE) / 2;
    }


    public void setMax(int max) {
        this.max = max;
        invalidate();
    }


    public void setProgress(float progress) {
        final float validProgress = checkProgress(progress - 19);
        if (validProgress == ProgressBarView.this.progress) {
            return;
        }

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progressSync", ProgressBarView.this.progress, validProgress);
        objectAnimator.setDuration(800);
        objectAnimator.start();
    }


    public void setProgressSync(float progress) {
        this.progress = checkProgress(progress);
        invalidate();
    }


    //保证progress的值位于[min,max]
    private float checkProgress(float progress) {

        if (progress < min) {
            return min;
        }


        return progress > max ? max : progress;
    }


    public void setDraggingEnabled(boolean draggingEnabled) {
        this.draggingEnabled = draggingEnabled;
    }

    public void setParent(ViewPager v) {
        parent = v;
    }

    public float getProgress() {
        return progress;
    }

    public void setmOnProgressChange(OnProgressChange mOnProgressChange) {
        this.mOnProgressChange = mOnProgressChange;
    }

    public interface OnProgressChange {
        void OnProgressChange(float progress);
    }
}