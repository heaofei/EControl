package com.mxkj.econtrol.ui.activity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqInitailEntity;
import com.mxkj.econtrol.bean.response.ResInitailEntity;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.utils.DeviceUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.StatusBarUtils;
import com.mxkj.econtrol.view.activity.MainNewActivity;
import com.mxkj.econtrol.view.activity.SplashActivity;
import com.mxkj.econtrol.widget.AbSlidingPlayView;


/**
 * 引导页
 */
public class GuideActivity extends BaseActivity implements OnClickListener {
    private AbSlidingPlayView mSlidingPlayView = null;
    private TextView bt_guide;
    private AnimationSet animationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setStatusBar(boolean isShow, int drawable) {
        super.setStatusBar(false, drawable);
        StatusBarUtils.with(this)
                .init();
    }

    @Override
    protected void initView() {

        bt_guide = (TextView) findViewById(R.id.bt_guide);
        mSlidingPlayView = (AbSlidingPlayView) findViewById(R.id.mAbSlidingPlayView);
        //mSlidingPlayView.setNavPageResources(R.drawable.btn_pressed,R.drawable.btn_unpressed);
        mSlidingPlayView.setNavHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        animationSet = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(1000);

        //参数1：x轴的初始值
        //参数2：x轴收缩后的值
        //参数3：y轴的初始值
        //参数4：y轴收缩后的值
        //参数5：确定x轴坐标的类型
        //参数6：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        //参数7：确定y轴坐标的类型
        //参数8：y轴的值，0.5f表明是以自身这个控件的一半长度为y轴
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        //设置动画时间
        scaleAnimation.setDuration(1000);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        initLogin();
    }

    private void initLogin() {
        ReqInitailEntity reqInitailEntity = new ReqInitailEntity();
        int adVersionCode = (int) SharedPreferencesUtils.getParam(this, "adVersionCode", 0);
        String adVersionName = (String) SharedPreferencesUtils.getParam(this, "adVersionName", "v1.0");
        int appVersionCode = DeviceUtil.getVersionCode(this);
        String appVersionName = DeviceUtil.getVersionName(this);
        reqInitailEntity.setAdVersionCode(adVersionCode);
        reqInitailEntity.setAdVersionName(adVersionName);
        reqInitailEntity.setAppVersionCode(appVersionCode);
        reqInitailEntity.setAppVersionName(appVersionName);
        reqInitailEntity.setX(APP.latitude);
        reqInitailEntity.setY(APP.longitude);

        APISubcriber<ResInitailEntity> subcriber = new APISubcriber<ResInitailEntity>() {
            @Override
            public void onFail(ResInitailEntity baseResponse, String msg) {
            }
            @Override
            public void onSuccess(ResInitailEntity resInitailEntity) {
                if (resInitailEntity.getUser() != null) {
                    APP.user = resInitailEntity.getUser();
                    APP.headerData.getHeaderToken().setToken(APP.user.getToken());
                    APP.headerData.getHeaderToken().setUserName(APP.user.getUserName());
                    SharedPreferencesUtils.setParam(GuideActivity.this, "token", APP.user.getToken());
                    SharedPreferencesUtils.setParam(GuideActivity.this, "userName", APP.user.getUserName());
                    String mHouseId = resInitailEntity.getUser().getDefaultHouseId();
                    SharedPreferencesUtils.setParam(GuideActivity.this, "houseId", mHouseId);// 初始化的时候，把默认房子id肤赋值给，当前用户房子id
                    APP.isLogin = true;
                    TcpClient.getInstacne().init();
                } else {
                    APP.user = null;
                    APP.isLogin = false;
                }
            }
        };
        subcriber.setShowLoding(false);
        RetrofitUtil.getInstance().getmApiService().initail(reqInitailEntity.toJsonStr()).compose(new APITransFormer<ResInitailEntity>()).subscribe(subcriber);

    }

    @Override
    public void initData() {

        ImageView iv_play1 = new ImageView(GuideActivity.this);
        iv_play1.setScaleType(ScaleType.FIT_XY);
        iv_play1.setBackgroundResource(R.drawable.guide_01);
        ImageView iv_play2 = new ImageView(GuideActivity.this);
        iv_play2.setScaleType(ScaleType.FIT_XY);
        iv_play2.setBackgroundResource(R.drawable.guide_02);
        ImageView iv_play3 = new ImageView(GuideActivity.this);
        iv_play3.setScaleType(ScaleType.FIT_XY);
        iv_play3.setBackgroundResource(R.drawable.guide_03);
        ImageView iv_play4 = new ImageView(GuideActivity.this);
        iv_play4.setScaleType(ScaleType.FIT_XY);
        iv_play4.setBackgroundResource(R.drawable.guide_04);

        mSlidingPlayView.addView(iv_play1);
        mSlidingPlayView.addView(iv_play2);
        mSlidingPlayView.addView(iv_play3);
        mSlidingPlayView.addView(iv_play4);
        /*mSlidingPlayView.setNavPageResources(R.drawable.center_home_card_choose_selected,
                R.drawable.center_home_card_choose_default);*/
//		mSlidingPlayView.setNavHorizontalGravity(Gravity.CENTER);

    }

    @Override
    protected void initListener() {
        bt_guide.setOnClickListener(this);

        mSlidingPlayView.setOnPageChangeListener(new AbSlidingPlayView.AbOnChangeListener() {
            @Override
            public void onChange(int position) {
                if (position == 0) {
                    bt_guide.setVisibility(View.GONE);
                } else if (position == 1) {
                    bt_guide.setVisibility(View.GONE);
                } else if (position == 2) {
                    bt_guide.setVisibility(View.GONE);
                }else if (position == 3) {
                    bt_guide.setVisibility(View.VISIBLE);
                }
            }


        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_guide:
                    if (APP.user != null) {
                        startToActivity(MainNewActivity.class);
                    } else {
//                        Intent intent = new Intent(GuideActivity.this, LoginRegistActivity.class);
                        Intent intent = new Intent(GuideActivity.this, MainNewActivity.class);
                        startToActivity(intent);
                    }
                    SharedPreferencesUtils.setParam(this, "notFirst", true);
                    finish();
                break;

           /* case R.id.tv_skip:
                if (APP.user != null) {
                    startToActivity(MainNewActivity.class);
                } else {
                    Intent intent = new Intent(GuideActivity.this, LoginRegistActivity.class);
                    startToActivity(intent);
                }
                SharedPreferencesUtils.setParam(this, "notFirst", true);
                finish();

                break;*/
            default:
                break;
        }
    }



}
