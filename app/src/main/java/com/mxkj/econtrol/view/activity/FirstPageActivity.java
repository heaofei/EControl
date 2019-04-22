package com.mxkj.econtrol.view.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.utils.LogUtil;

/***
 * 首页 （旧版）
 */
public class FirstPageActivity extends BaseActivity {

    private TextView mBtnLogin;
    private TextView mBtnRegist;
    private ImageView mImageView;
    private ImageView imvClose;
    private ScrollView scrollView;
    private RelativeLayout mRlTop;
    private LinearLayout mllLogin01, mllLogin02;
    private ImageView mImvBg, mImvLogo, mImvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_first_page);
        super.onCreate(savedInstanceState);
        setStateBarColor(0xffff695a);


    }

    private void setStateBarColor(int color) {
        //设置状态栏颜色
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        mBtnLogin = findView(R.id.tv_login);
        mBtnRegist = findView(R.id.tv_regist);
        mImageView = findView(R.id.imv_header_pic);
        imvClose = findView(R.id.imv_close);
        scrollView = findView(R.id.scrollView);
        mRlTop = findView(R.id.rl_top);
        mllLogin01 = findView(R.id.ll_login01);
        mllLogin02 = findView(R.id.ll_login02);
        mImvBg = findView(R.id.imv_top_bg);
        mImvLogo = findView(R.id.imv_logo);
        mImvHeader = findView(R.id.imv_header_pic);


    }

    @Override
    protected void initData() {
        Glide.with(this).load(R.drawable.ic_no_head).into(mImageView);

    }

    @Override
    protected void initListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnRegist.setOnClickListener(this);
        imvClose.setOnClickListener(this);
        findView(R.id.tv_regist02).setOnClickListener(this);
        findView(R.id.tv_login02).setOnClickListener(this);
        scrollView.requestFocusFromTouch();
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (scrollView.getScrollY() == 0) {
                        scrollToTop();

                    } else {
                        scroll();

                    }
                }
                return false;

            }
        });
    }

    private void scroll() {
        //已经是隐藏头部了不再执行动画
        if (View.GONE == mRlTop.getVisibility()) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator imvBgtranslate = ObjectAnimator.ofFloat(mImvBg, "translationY", 0, -mRlTop.getMeasuredHeight());
        ObjectAnimator llLogin01ranslate = ObjectAnimator.ofFloat(mllLogin01, "translationY", 0, -mImvBg.getMeasuredHeight() + mllLogin01.getMeasuredHeight());
        Animator headerScale = AnimatorInflater.loadAnimator(this, R.animator.first_page_scale_anim01);
        headerScale.setTarget(mImvHeader);
        Animator logoScale = AnimatorInflater.loadAnimator(this, R.animator.first_page_scale_anim01);
        logoScale.setTarget(mImvLogo);
        animatorSet.play(imvBgtranslate)
                .with(llLogin01ranslate)
                .with(headerScale)
                .with(logoScale);
        animatorSet.setDuration(500);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRlTop.setVisibility(View.GONE);
                mllLogin02.setVisibility(View.VISIBLE);
                setStateBarColor(0xff222222);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

    private void scrollToTop() {
        //已经是显示部了不再执行动画
        if (View.VISIBLE == mRlTop.getVisibility()) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator imvBgtranslate = ObjectAnimator.ofFloat(mImvBg, "translationY", -mRlTop.getMeasuredHeight(), 0);
        ObjectAnimator llLogin01ranslate = ObjectAnimator.ofFloat(mllLogin01, "translationY", -mImvBg.getMeasuredHeight() + mllLogin01.getMeasuredHeight(), 0);
        Animator headerScale = AnimatorInflater.loadAnimator(this, R.animator.first_page_sacle_anim02);
        headerScale.setTarget(mImvHeader);
        Animator logoScale = AnimatorInflater.loadAnimator(this, R.animator.first_page_sacle_anim02);
        logoScale.setTarget(mImvLogo);
        animatorSet.play(imvBgtranslate)
                .with(llLogin01ranslate)
                .with(headerScale)
                .with(logoScale);
        animatorSet.setDuration(500);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRlTop.setVisibility(View.VISIBLE);
                mllLogin02.setVisibility(View.GONE);
                setStateBarColor(0xffff695a);
            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_login:
                //到登陆页面
                startToActivity(LoginActivity.class);
                break;
            case R.id.tv_login02:
                //到登陆页面
                startToActivity(LoginActivity.class);
                break;
            case R.id.tv_regist:
                //到注册页面
                startToActivity(RegistActivity.class);
                break;
            case R.id.tv_regist02:
                //到注册页面
                startToActivity(RegistActivity.class);
                break;
            case R.id.imv_close:
                startToActivity(PublicCommunityActivity.class);
                finish();
                break;
            default:
                break;
        }
    }
}
