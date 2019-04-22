package com.mxkj.econtrol.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.request.ReqUserApplyBindHouse;
import com.mxkj.econtrol.contract.QrCodeScanContract;
import com.mxkj.econtrol.model.QrCodeScanModel;
import com.mxkj.econtrol.presenter.QrCodeScanPresenter;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.view.activity.EmpowerToiPadActivity;
import com.mxkj.econtrol.view.activity.UserInfoModifyActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 定制化显示扫描界面
 */
public class SecondActivity extends BaseActivity implements QrCodeScanContract.View {

    private CaptureFragment captureFragment;
    private TextView tv_title_left;
    private TextView tv_tips;
    private ImageButton btn_open;
    private boolean isOpen = false;

    private QrCodeScanContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_scan);
        super.onCreate(savedInstanceState);

    }

    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_tips = findView(R.id.tv_tips);
        btn_open = findView(R.id.btn_open);


        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();


    }

    protected void initData() {
        mPresenter = new QrCodeScanPresenter(this, new QrCodeScanModel());
    }

    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        tv_tips.setOnClickListener(this);
    }


    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            SecondActivity.this.setResult(RESULT_OK, resultIntent);
//            SecondActivity.this.finish();
            if (result.length() > 7) {
                String type = result.substring(5, 8);
                if (type.equals("001")) {                          // type = 001 ：扫码添加房子
                    String[] content = result.split("#");
                    if (content.length > 2) {
                        String houseId = content[1];
                        String houseName = content[2];
                        showConfirmDialog(houseId, houseName);
                    }
                }else if(type.equals("002")){                     // type = 002 ：扫码授权ipad登录
                    Intent intent = new Intent(SecondActivity.this,EmpowerToiPadActivity.class);
                    String[] content = result.split("#");
                    if (content.length >= 2) {
                        String rid = content[2];
                       intent.putExtra("rid",rid);
                    }
                    startActivity(intent);
                    finish();
                }else {
                    Intent resultIntent2 = new Intent();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                    bundle2.putString(CodeUtils.RESULT_STRING, result);
                    resultIntent.putExtras(bundle2);
                    SecondActivity.this.setResult(RESULT_OK, resultIntent);
                    SecondActivity.this.finish();
                }
            }
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            SecondActivity.this.setResult(RESULT_OK, resultIntent);
            SecondActivity.this.finish();
        }
    };

    @Override
    public void setPresenter(QrCodeScanContract.Presenter presenter) {

    }

    @Override
    public void userApplyBindHouseSuccess() {
        showToast(getString(R.string.apply_success));
        if (isOpen) {
            //关闭闪光灯
            CodeUtils.isLightEnable(false);
        }
        finish();
    }

    @Override
    public void userApplyBindHouseFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                if (isOpen) {
                    //关闭闪光灯
                    CodeUtils.isLightEnable(false);
                }
                finish();
                break;
            case R.id.tv_tips:
                showTipasDialog();
                break;
            case R.id.btn_open:
                if (!isOpen) {
                    //打开闪光灯
                    CodeUtils.isLightEnable(true);
                    btn_open.setBackgroundResource(R.drawable.ic_scan_open_light);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    btn_open.setBackgroundResource(R.drawable.ic_scan_closed_light);
                    isOpen = false;
                }
                break;
        }
    }

    /**
     * 确认加入对话框
     */
    private void showConfirmDialog(final String houseId, String houseName) {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(APP.user.getRealName())) {
                    showFailDialog(); // 提示完善真实姓名
                } else {
                    mPresenter.userApplyBindHouse(new ReqUserApplyBindHouse(houseId, "1", APP.user.getRealName(), "", "", "")); // 1是家庭成员
                }
            }
        };
        dialog.showTipDialog(getString(R.string.qrcode_submit), getString(R.string.qrcode_join) + houseName + "?");
    }

    /***
     * // 提示完善真实姓名
     */
    private void showFailDialog() {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                startActivity(new Intent(SecondActivity.this, UserInfoModifyActivity.class));
                if (isOpen) {
                    //关闭闪光灯
                    CodeUtils.isLightEnable(false);
                }
                finish();
            }
        };
        dialog.showPerfectTipDialog(getString(R.string.qrcode_join_fail), getString(R.string.qrcode_join_realname), getString(R.string.cancel), getString(R.string.qrcode_join_perfectrealname));
    }


    /***
     * 提示没有二维码
     */
    private void showTipasDialog() {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {

            }
        };
        dialog.showConfirmTipDialog("找不到二维码?", "在个人中心“我的住宅”里打开房间二维码");
    }

}
