package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.utils.StatusBarUtils;
import com.mxkj.econtrol.view.activity.LoginActivity;
import com.mxkj.econtrol.view.activity.MainNewActivity;
import com.mxkj.econtrol.view.activity.NewHouseActivity;
import com.mxkj.econtrol.view.activity.RegistActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class LoginRegistActivity extends BaseActivity {


    private Button btn_login, btn_regist, btn_experience;
    private ImageView iv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_regist);
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
        btn_login = findView(R.id.btn_login);
        btn_regist = findView(R.id.btn_regist);
        btn_experience = findView(R.id.btn_experience);
        iv_close = findView(R.id.iv_close);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        btn_experience.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_login:
                startActivity(new Intent(this, LoginActivity.class));
//                startActivity(new Intent(this, NewHouseActivity.class));
//                startActivity(new Intent(this, NewHouseActivity.class));
                // 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画
//                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                break;
            case R.id.btn_regist:
                startActivity(new Intent(this, RegistActivity.class));
                // 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画
//                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

                break;
            case R.id.btn_experience: // 体验一下
                APP.isExperience = true ;
                APP.DeviceId = "体验" ;
                startActivity(new Intent(this, MainNewActivity.class));
                finish();
                break;
        }
    }
}
