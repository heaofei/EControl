package com.mxkj.econtrol.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.mxkj.econtrol.R;

/**
 * Created by liang on 2017/4/18.
 */

public class RoateSwitchView extends RelativeLayout {

    //低档
    public static final int SWITCH_LEVEL_LOW = 3;
    public static final int SWITCH_LEVEL_MIDDLE = 2;
    public static final int SWITCH_LEVEL_HIGH = 1;
    //当前档位
    private int mCurLevel;
    private TouchRoateImageView mRoateView;
    private SwitchLevelChangeLinstener mLinstener;

    public RoateSwitchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 1);
    }

    public RoateSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_roate_switch_view, this);
        mRoateView = (TouchRoateImageView) findViewById(R.id.imv_switch);
        mRoateView.setDegreeChangeLinstenter(new TouchRoateImageView.DegreeChangeLinstenter() {
            @Override
            public void onDegreeChange(float degree) {
                if (mLinstener != null) {
                    if (degree == -90) {
                        //如果和当前档位一样说明没改变档位
                        if (mCurLevel == SWITCH_LEVEL_LOW) {
                            return;
                        }
                        mCurLevel = SWITCH_LEVEL_LOW;
                        mLinstener.onChange(SWITCH_LEVEL_LOW);
                    } else if (degree == 0) {
                        if (mCurLevel == SWITCH_LEVEL_MIDDLE) {
                            return;
                        }
                        mCurLevel = SWITCH_LEVEL_MIDDLE;
                        mLinstener.onChange(SWITCH_LEVEL_MIDDLE);
                    } else if (degree == 90) {
                        if (mCurLevel == SWITCH_LEVEL_HIGH) {
                            return;
                        }
                        mCurLevel = SWITCH_LEVEL_HIGH;
                        mLinstener.onChange(SWITCH_LEVEL_HIGH);
                    }
                }
            }
        });
    }

    /**
     * 设置开关档位
     */
    public void setSwitchLevel(int level) {
        if (level == SWITCH_LEVEL_LOW) {
            mRoateView.setCurDegree(-90);
            mCurLevel = level;
        } else if (level == SWITCH_LEVEL_MIDDLE) {
            mRoateView.setCurDegree(0);
            mCurLevel = level;
        } else if (level == SWITCH_LEVEL_HIGH) {
            mRoateView.setCurDegree(90);
            mCurLevel = level;
        }

    }

    public int getSwitchLevel() {
        if (mRoateView.getCurDegree() == -90) {
            return SWITCH_LEVEL_LOW;
        } else if (mRoateView.getCurDegree() == 0) {
            return SWITCH_LEVEL_MIDDLE;
        } else if (mRoateView.getCurDegree() == 90) {
            return SWITCH_LEVEL_HIGH;
        } else {
            return 0;
        }
    }


    public void setSwitchLevelChangeLinstener(SwitchLevelChangeLinstener mLinstener) {
        this.mLinstener = mLinstener;
    }

    public interface SwitchLevelChangeLinstener {
        void onChange(int level);
    }
}

