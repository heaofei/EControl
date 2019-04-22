package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.contract.LockGesturePswContract;
import com.mxkj.econtrol.contract.LockTemporaryPswContract;
import com.mxkj.econtrol.model.LockTemporaryPswModel;
import com.mxkj.econtrol.presenter.LockTemporaryPswPresenter;
import com.mxkj.econtrol.ui.activity.SelectPictureActivity;
import com.mxkj.econtrol.utils.DESUtils;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.RSAPKCS8X509Utils;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

/***
 * 门锁临时密码界面
 */
public class LockTemporaryPswActivity extends BaseActivity implements LockTemporaryPswContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;
    private TextView tv_temporary;
    private TextView tv_countdown;

    private LockTemporaryPswContract.Presenter mPresenter;
    private String lockId;
    private String lockToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lock_temporary_psw);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_temporary = findView(R.id.tv_temporary);
        tv_countdown = findView(R.id.tv_countdown);
        tv_title_content.setText("临时密码");

        lockId = getIntent().getStringExtra("lockId");
        lockToken = getIntent().getStringExtra("lockToken");
        if (TextUtils.isEmpty(lockToken)) {
            showToast("token为空");
        }

        mPresenter = new LockTemporaryPswPresenter(this, new LockTemporaryPswModel());
        ReqLockPartPasswordTemporarySet reqLockPartPasswordTemporarySet = new ReqLockPartPasswordTemporarySet();
        reqLockPartPasswordTemporarySet.setPartId(lockId);
        reqLockPartPasswordTemporarySet.setHouseId(getHouseId());
        reqLockPartPasswordTemporarySet.setToken(lockToken);
        mPresenter.partPasswordTemporarySet(reqLockPartPasswordTemporarySet);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.btn_next:
                finish();
                break;
        }
    }

    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
    }

    @Override
    public void partPasswordTemporarySetSuccess(ResLockPartPasswordTemporarySet resLockPartPasswordTemporarySet) {
        String content = resLockPartPasswordTemporarySet.getPassword();
//       String psw =  RSAPKCS8X509Utils.getInstance().decryptWithBase64(content);
        try {
            String token = APP.user.getToken();
            if (token.length() >= 8) { // 拿登陆tokn前8位
                token = token.substring(0, 8);
                String psw = DESUtils.decryptDES(resLockPartPasswordTemporarySet.getPassword(), token);
                tv_temporary.setText(psw);
                recLen = resLockPartPasswordTemporarySet.getTimeout();
                handler.postDelayed(runnable, 1000);
            }
        } catch (Exception e) {


        }


    }

    @Override
    public void partPasswordTemporarySetFali(String msg) {
        showToast("" + msg);
    }

    @Override
    public void setPresenter(LockTemporaryPswContract.Presenter presenter) {

    }


    private int recLen = 6; // 搜索设定时间

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen > 0) {
                recLen--;
                tv_countdown.setText(recLen + "秒后失效");
                handler.postDelayed(this, 1000);
            } else {
//                tv_search.setText("重新搜索");
                showDialog();
            }
        }
    };

    private void showDialog() {
        MyDialogUtil myDialogUtil = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                finish();
            }
        };
        myDialogUtil.showConfirmTipDialog("提示","临时密码已失效");

    }
}
