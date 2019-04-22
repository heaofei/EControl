package com.mxkj.econtrol.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqQrLogin;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

public class EmpowerToiPadActivity extends BaseActivity {

    private TextView tv_title_left;
    private Button btn_comfig, btn_clean;
    private String rid; //ipad登陆的Rid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_empower_toi_pad);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        btn_comfig = findView(R.id.btn_comfig);
        btn_clean = findView(R.id.btn_clean);


    }

    @Override
    protected void initData() {
        rid = getIntent().getStringExtra("rid");


    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        btn_comfig.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.btn_comfig:
                qeLogin();
                break;
            case R.id.btn_clean:
                showToast("取消登录");
                finish();
                break;
        }
    }

    private void qeLogin() {

        ReqQrLogin reqQrLogin = new ReqQrLogin();
       String psw   =  (String) SharedPreferencesUtils.getParam(this, "psw", ""); //  这里解释出来的密码， 多了\\r \\n
        psw = psw.replaceAll("\\r","");
        psw = psw.replaceAll("\\n","");
        psw = psw.trim();
        reqQrLogin.setPassWord(psw);
        reqQrLogin.setUserName(APP.user.getUserName());
        reqQrLogin.setrId(rid);

        APP.headerData.setOsVersion("ipad");
        APP.headerData.setDigest("ipad");
        APP.headerData.setTime(DateTimeUtil.getSecond());
        String header = APP.headerData.toJsonStr();
        reqQrLogin.setHead(header);


        String data= reqQrLogin.toJsonStr();

         RetrofitUtil.getInstance().getmApiService()
//                 .qrLogin02(APP.user.getUserName(),psw,rid)
                 .qrLogin(reqQrLogin)
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse, String msg) {
                        showToast(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        showToast("登陆成功！");
                        finish();
                    }
                });


        APP.headerData.setOsVersion(Build.VERSION.RELEASE + ""); // 将ipad登陆的标示设置回来

    }


}
