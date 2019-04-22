package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordChange;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordGrant;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordInit;
import com.mxkj.econtrol.bean.response.ResLockPasswordGrant;
import com.mxkj.econtrol.contract.LockGesturePswContract;
import com.mxkj.econtrol.model.LockGesturePswModel;
import com.mxkj.econtrol.presenter.LockGesturePswPresenter;
import com.mxkj.econtrol.utils.DESUtils;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.RSAPKCS8X509Utils;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.view.activity.LockTemporaryPswActivity;
import com.wangnan.library.listener.OnGestureLockListener;
import org.greenrobot.eventbus.EventBus;


/***
 * 门锁手势密码界面
 */
public class LockGesturePasswordActivity extends BaseActivity implements LockGesturePswContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;


    private com.wangnan.library.GestureLockView mGestureLockView;
    private TextView tv_tips1, tv_tips2;
    private LockGesturePswContract.Presenter mPresenter;
    private boolean partPasswordInit; // 初始密码
    private boolean partPasswordGrant; // 验证密码
    private boolean temporary; // 临时密码
    private boolean partPasswordChange; // 修改密码
    private boolean isPasswordTure = false; // 旧密码是否正确
    private String originPassword; // 旧密码
    private String newPassword; // 新密码
    private String lockId; // 门锁id
    private String lockToken; // 开锁token


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lock_gesture_password);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_tips1 = findView(R.id.tv_tips1);
        tv_tips2 = findView(R.id.tv_tips2);
        tv_title_content.setText("手势密码");

        partPasswordInit = getIntent().getBooleanExtra("partPasswordInit", false);
        partPasswordGrant = getIntent().getBooleanExtra("partPasswordGrant", false);
        partPasswordChange = getIntent().getBooleanExtra("partPasswordChange", false);
        temporary = getIntent().getBooleanExtra("temporary", false);
        lockId = getIntent().getStringExtra("lockId");

        mPresenter = new LockGesturePswPresenter(this, new LockGesturePswModel());


        if (partPasswordInit) {
            // 密码初始化
            tv_tips1.setText("绘制解锁图案，请至少连接4个点");
            tv_tips2.setText("请牢记您的密码，忘记后将无法找回");
        }
        if (partPasswordGrant) {
            // 验证手势密码
            tv_tips1.setText("绘制解锁图案，请至少连接4个点");
//            tv_tips2.setText("请牢记您的密码，忘记后将无法找回");
        }
        if (partPasswordChange) {
            // 修改密码
            tv_tips1.setText("请输入原手势密码");
            tv_tips2.setText("请牢记您的密码，忘记后将无法找回");
            tv_title_content.setText("重置密码");
        }

    }

    @Override
    protected void initData() {

        mGestureLockView = (com.wangnan.library.GestureLockView) findViewById(R.id.gesture_lock);
        mGestureLockView.setPressImageResource(R.drawable.ic_circle_selected);


        // 设置手势解锁监听器
        mGestureLockView.setGestureLockListener(new OnGestureLockListener() {
            @Override
            public void onStarted() {
            }
            @Override
            public void onProgress(String progress) {
            }
            @Override
            public void onComplete(String result) {
                if (TextUtils.isEmpty(result)) {
                    return;
                } else if (result.length() < 4) {
                    tv_tips1.setText("至少连接4个点，请重试");
                    tv_tips1.setTextColor(getResources().getColor(R.color.text_orangereg02));
                    mGestureLockView.clearView();
                } else {
                    tv_tips1.setText("绘制解锁图案，请至少连接4个点");
//                    mGestureLockView.showErrorStatus(1000);
                    if (partPasswordInit) {
                        passwordInit(result); // 密码初始化
                    }
                    if (partPasswordGrant) {
                        passwordGrant(result);// 验证手势密码
                    }
                    if (partPasswordChange) {
                        passwordChange(result); // 修改密码
                    }
                }
            }
        });
    }


    private void showDialog() {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                finish();
            }
        };
        dialog.showConfirmTipDialog("提示", "抱歉，您输入错误次数已达上限。您已失去解锁权限。");
    }

    /***
     * 密码初始化
     */
    private void passwordInit(String content) {

        if (TextUtils.isEmpty(originPassword)) { // 第一次输入密码
            originPassword = content;
            mGestureLockView.clearView();
            tv_tips1.setText("再次绘制图案进行确认");
            tv_tips1.setTextColor(getResources().getColor(R.color.text_black_333));
            tv_tips2.setText("请牢记您的密码，忘记后将无法找回");
            // 请再次输入同样密码
        } else if (originPassword.equals(content)) {
            // 设置初始密码
            ReqLockPartPasswordInit reqLockPartPasswordInit = new ReqLockPartPasswordInit();
            reqLockPartPasswordInit.setHouseId(getHouseId()); // 房子id
            reqLockPartPasswordInit.setPartId(lockId); // 门锁id
            reqLockPartPasswordInit.setPassword(RSAPKCS8X509Utils.getInstance().encryptWithBase64(content));
            reqLockPartPasswordInit.setType("01");// "00"-"普通密码","01"-"手势密码"
            mPresenter.partPasswordInit(reqLockPartPasswordInit);
        } else {
            tv_tips1.setText("与上次绘制不一致，请重新绘制");
            tv_tips1.setTextColor(getResources().getColor(R.color.text_black_333));

            tv_tips2.setText("请牢记您的密码，忘记后将无法找回");
            mGestureLockView.clearView();
        }

    }

    /***
     * 密码验证
     */
    private void passwordGrant(String content) {
        ReqLockPartPasswordGrant reqLockPartPasswordGrant = new ReqLockPartPasswordGrant();
        reqLockPartPasswordGrant.setHouseId(getHouseId()); // 房子id
        reqLockPartPasswordGrant.setPartId(lockId); // 门锁id
        reqLockPartPasswordGrant.setPassword(RSAPKCS8X509Utils.getInstance().encryptWithBase64(content));
        reqLockPartPasswordGrant.setType("01");// "00"-"普通密码","01"-"手势密码"
        mPresenter.partPasswordGrant(reqLockPartPasswordGrant);
    }

    /***
     * 密码重置
     */
    private void passwordChange(String content) {
        if (!isPasswordTure) { // 第一次输入旧密码
            originPassword = RSAPKCS8X509Utils.getInstance().encryptWithBase64(content);
            passwordGrant(content); // 验证密码
            mGestureLockView.clearView();
        } else {
            if (TextUtils.isEmpty(newPassword)) {
                newPassword = content;
                tv_tips1.setText("再次绘制图案进行确认");
                tv_tips1.setTextColor(getResources().getColor(R.color.text_black_333));
                tv_tips2.setText("请牢记您的密码，忘记后将无法找回");
                mGestureLockView.clearView();
            } else if (newPassword.equals(content)) {
                // 验证手势密码
                ReqLockPartPasswordChange reqLockPartPasswordChange = new ReqLockPartPasswordChange();
                reqLockPartPasswordChange.setHouseId(getHouseId()); // 房子id
                reqLockPartPasswordChange.setPartId(lockId); // 门锁id
                reqLockPartPasswordChange.setType("01");// "00"-"普通密码","01"-"手势密码"
                reqLockPartPasswordChange.setOriginPassword(originPassword); // 旧密码
                reqLockPartPasswordChange.setNewPassword(RSAPKCS8X509Utils.getInstance().encryptWithBase64(content)); // 新密码
                mPresenter.partPasswordChange(reqLockPartPasswordChange);

            } else {
                tv_tips1.setText("与上次绘制不一致，请重新绘制");
                tv_tips1.setTextColor(getResources().getColor(R.color.text_black_333));
                tv_tips2.setText("请牢记您的密码，忘记后将无法找回");
                mGestureLockView.clearView();
            }

        }
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
        }
    }

    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
    }

    /***
     * 初始化手势密码成功
     */
    @Override
    public void partPasswordInitSuccess(BaseResponse baseResponse) {
        showToast("初始化成功");
        finish();
    }

    /***
     * 初始化手势密码失败
     */
    @Override
    public void partPasswordInitFali(String msg) {
        showToast("初始化" + msg);
    }

    /***
     * 验证手势密码成功
     */
    @Override
    public void partPasswordGrantSuccess(ResLockPasswordGrant baseResponse) {
        lockToken = baseResponse.getToken().trim();
//        showToast("验证手势成功");
        if (partPasswordGrant) {  // 验证手势密码
            if (temporary) {  // 临时密码
                Intent intent1 = new Intent(this, LockTemporaryPswActivity.class);
                intent1.putExtra("lockId", lockId); // 锁id
                try {
                    String token = APP.user.getToken();
                    if (token.length() >= 8) {  // 拿登陆tokn前8位
                        token = token.substring(0, 8);
                        String des = DESUtils.encryptDES(lockToken, token);
                        intent1.putExtra("lockToken", des); //
                    }
                } catch (Exception e) {
                }
                startActivity(intent1);
                finish();
            } else {  // 立即开锁

                Intent intent = new Intent();
                try {
                    String token = APP.user.getToken();
                    if (token.length() >= 8) {  // 拿登陆tokn前8位
                        token = token.substring(0, 8);
                        String des = DESUtils.encryptDES(lockToken, token);
                        intent.putExtra("token", des);
                    }
                } catch (Exception e) {
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        if (partPasswordChange) {
            // 修改密码
            isPasswordTure = true;
            tv_tips1.setText("绘新制解锁图案，请至少连接4个点");
            tv_tips2.setText("请牢记您的密码，忘记后将无法找回");
        }

    }

    /***
     * 验证手势密码失败
     */
    @Override
    public void partPasswordGrantFali(ResLockPasswordGrant baseResponse) {

        if (baseResponse.getErrorCode().equals("0003")) { // 密码错误
            tv_tips1.setText("错误，再输入错误" + baseResponse.getRestErrorTimes() + "次 \n您将失去解锁权限");
            tv_tips1.setTextColor(getResources().getColor(R.color.text_orangereg02));
        } else if (baseResponse.getErrorCode().equals("0002")) { //密码错误次数超出限制
            EventBus.getDefault().post(new EventBusUIMessage(Msg.LOCK_PESSWORD_GRANT_ERROR));
            showDialog();
        }
//        showToast("" + baseResponse.getMsg());
        mGestureLockView.clearView();
    }

    /***
     * 修改手势密码成功
     */
    @Override
    public void partPasswordChangeSuccess(BaseResponse baseResponse) {
        finish();
        showToast("修改手势密码成功");
    }

    /***
     * 修改手势密码失败
     */
    @Override
    public void partPasswordChangeFali(String msg) {
        showToast("修改手势密码失败" + msg);
    }

    @Override
    public void setPresenter(LockGesturePswContract.Presenter presenter) {

    }
}
