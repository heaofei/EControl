package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqLockUserApplyPartPassword;
import com.mxkj.econtrol.contract.LockSubmitVerificationContract;
import com.mxkj.econtrol.model.LockSubmitVerificationModel;
import com.mxkj.econtrol.presenter.LockSubmitVerificationPresenter;
import com.mxkj.econtrol.ui.activity.SelectPictureActivity;
import com.mxkj.econtrol.utils.Bitmap2StringUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;

import org.greenrobot.eventbus.EventBus;

/***
 *
 *    门锁提交身份证验证界面
 */
public class LockSubmitVerificationActivity extends BaseActivity implements LockSubmitVerificationContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private LinearLayout ll_sfz01,ll_sfz02,ll_sfz03;// 对应身份证三张的顺序
    private ImageView iv_sfz01,iv_sfz02,iv_sfz03;// 对应身份证三张的顺序
    private Button btn_submit;
    private String picPath;
    private String mUrl01,mUrl02,mUrl03;// 对应身份证三张的顺序
    private LockSubmitVerificationContract.Presenter mPresenter;
    private String lockId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lock_submit_verification);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText("身份验证");
        lockId= getIntent().getStringExtra("lockId");
        mPresenter  = new LockSubmitVerificationPresenter(this,new LockSubmitVerificationModel());

        ll_sfz01 = findView(R.id.ll_sfz01);
        ll_sfz02 = findView(R.id.ll_sfz02);
        ll_sfz03 = findView(R.id.ll_sfz03);
        iv_sfz01 = findView(R.id.iv_sfz01);
        iv_sfz02 = findView(R.id.iv_sfz02);
        iv_sfz03 = findView(R.id.iv_sfz03);
        btn_submit = findView(R.id.btn_submit);




    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        ll_sfz01.setOnClickListener(this);
        ll_sfz02.setOnClickListener(this);
        ll_sfz03.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
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
            case R.id.ll_sfz01:
                Intent intent01 = new Intent(this, SelectPictureActivity.class);
                intent01.putExtra("isTakePhoto", true);
                intent01.putExtra("requestCode", 1);
                startActivityForResult(intent01, 1);
                break;
            case R.id.ll_sfz02:
                Intent intent02 = new Intent(this, SelectPictureActivity.class);
                intent02.putExtra("isTakePhoto", true);
                intent02.putExtra("requestCode", 2);
                startActivityForResult(intent02, 2);
                break;
            case R.id.ll_sfz03:
                Intent intent03 = new Intent(this, SelectPictureActivity.class);
                intent03.putExtra("isTakePhoto", true);
                intent03.putExtra("requestCode", 3);
                startActivityForResult(intent03, 3);
                break;
            case R.id.btn_submit:

                String setIdcardFront =  Bitmap2StringUtil.bitmapToString(mUrl01); // 直接util转String之后直接传给后台
                String setIdcardBack =  Bitmap2StringUtil.bitmapToString(mUrl02); // 直接util转String之后直接传给后台
                String setIdcardHold =  Bitmap2StringUtil.bitmapToString(mUrl03); // 直接util转String之后直接传给后台

                ReqLockUserApplyPartPassword reqLockUserApplyPartPassword = new ReqLockUserApplyPartPassword();
                reqLockUserApplyPartPassword.setType("00"); //type 00-业主忘记手势密码，01-成员忘记手势密码
                reqLockUserApplyPartPassword.setIdcardFront(setIdcardFront);
                reqLockUserApplyPartPassword.setIdcardBack(setIdcardBack);
                reqLockUserApplyPartPassword.setIdcardHold(setIdcardHold);
                reqLockUserApplyPartPassword.setPartId(lockId);
                mPresenter.userApplyPartPassword(reqLockUserApplyPartPassword);


                break;
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            picPath = data.getStringExtra("patch");
            if (requestCode == 01) { // 请求码
                mUrl01 = picPath;
                Glide.with(this).load(picPath).into(iv_sfz01);
            } else if (requestCode == 02) {
                mUrl02 = picPath;
                Glide.with(this).load(picPath).into(iv_sfz02);
            } else if (requestCode == 03) {
                mUrl03 = picPath;
                Glide.with(this).load(picPath).into(iv_sfz03);
            }
        }

    }

    @Override
    public void setPresenter(LockSubmitVerificationContract.Presenter presenter) {

    }

    @Override
    public void userApplyPartPasswordSuccess(BaseResponse baseResponse) {
        EventBus.getDefault().post(new EventBusUIMessage(Msg.LOCK_APPLY_SUCCESS));
        showDialog();

    }

    private void showDialog() {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                finish();
            }
        };
        dialog.showConfirmTipDialog("提交成功","您已成功提交解锁权限申请。我们将于三个工作日内完成审核，请注意查收。");
    }

    @Override
    public void userApplyPartPasswordFali(String msg) {
        showToast(msg);
    }
}
