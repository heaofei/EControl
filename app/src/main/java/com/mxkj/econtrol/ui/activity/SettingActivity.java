package com.mxkj.econtrol.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseTCPCMDRequest;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqUserLogout;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.view.activity.LoginActivity;
import com.mxkj.econtrol.view.activity.UserPassWordResetActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class SettingActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private RelativeLayout mRlLogout;
    private RelativeLayout mClearCache;
    private RelativeLayout mModifyPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_setting);
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void initView() {

        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText(getString(R.string.app_setting));
        mRlLogout = findView(R.id.rl_logout);
        mClearCache = findView(R.id.rl_clear_cache);
        mModifyPass = findView(R.id.rl_modify_password);
    }




    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mRlLogout.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        mModifyPass.setOnClickListener(this);
        mClearCache.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_logout:
                logout();
                APP.isLogin = false;
                break;
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_modify_password:
                Intent intent = new Intent(this,UserPassWordResetActivity.class);
                intent.putExtra("type","CHANGE");
                startActivity(intent);
                break;
            case R.id.rl_clear_cache:
                clearCache();
                Glide.get(this).clearMemory();
                break;
        }
    }

    /**
     * @Desc: 注销
     * @author:liangshan
     */
    public void logout() {
        if (APP.user == null) {
            isClose = false;
            EventBusUIMessage eventBusMessage = new EventBusUIMessage(Msg.CLEAR_ACITVITY);
            EventBus.getDefault().post(eventBusMessage);
            startToActivity(LoginActivity.class);
            TcpClient.getInstacne().close();
            finish();
            return;
        }
        ReqUserLogout reqUserLogout = new ReqUserLogout(APP.user.getUserName());
        RetrofitUtil.getInstance().getmApiService()
                .API(reqUserLogout.toJsonStr(), APIService.USER_LOGOUT)
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        showToast(getString(R.string.logout_success));
                        SharedPreferencesUtils.setParam(SettingActivity.this, "token", "");
                        SharedPreferencesUtils.setParam(SettingActivity.this, "userName", "");
                        isClose = false;
                        EventBusUIMessage eventBusMessage = new EventBusUIMessage(Msg.CLEAR_ACITVITY);
                        EventBus.getDefault().post(eventBusMessage);
                        startToActivity(LoginRegistActivity.class);
                        BaseTCPCMDRequest regist = new BaseTCPCMDRequest();
                        regist.setAction("QUIT");
                        TcpClient.getInstacne().sendCMD(regist);
                        TcpClient.getInstacne().close();
                        APP.user = null;
                        finish();
                    }
                });

    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        File directory = getCacheDir();
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
        showToast(getString(R.string.clear_cache_success));
    }


}
