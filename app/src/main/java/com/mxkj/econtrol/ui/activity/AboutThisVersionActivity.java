package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.utils.DeviceUtil;
import com.mxkj.econtrol.view.activity.RegistActivity;
import com.zhy.autolayout.config.AutoLayoutConifg;

/***
 * 关于版本
 *
 */
public class AboutThisVersionActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private TextView mTvVerion;
    private TextView mTvPrivate;
    private RelativeLayout rl_about;//介绍
    private RelativeLayout rl_service;//服务协议
    private RelativeLayout rl_policy;//隐私政策

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_this_version);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {

        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);

        tv_title_content.setText(getString(R.string.about_fiswink));

        mTvVerion = findView(R.id.tv_version);
        mTvPrivate = findView(R.id.tv_policy);
        rl_about = findView(R.id.rl_about);
        rl_service = findView(R.id.rl_service);
        rl_policy = findView(R.id.rl_policy);


    }

    @Override
    protected void initData() {
        mTvVerion.setText("版本 " + DeviceUtil.getVersionName(this));
        mTvPrivate.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannableString = new SpannableString(mTvPrivate.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(AboutThisVersionActivity.this, PrivateAndPolicy.class);
                intent.putExtra("type", "agreement");
                startToActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(clickableSpan, 0, 8, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(AboutThisVersionActivity.this, PrivateAndPolicy.class);
                intent.putExtra("type", "privatePolicy");
                startToActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }, 9, 17, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        mTvPrivate.setText(spannableString);
    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_service.setOnClickListener(this);
        rl_policy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
             case R.id.rl_service:

                 Intent agreement = new Intent(this, PrivateAndPolicy.class);
                 agreement.putExtra("type", "agreement");
                 startToActivity(agreement);

                break;
            case R.id.rl_policy:
                Intent privatePlicy = new Intent(this, PrivateAndPolicy.class);
                privatePlicy.putExtra("type", "privatePolicy");
                startToActivity(privatePlicy);

                break;

        }
    }
}
