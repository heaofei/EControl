package com.mxkj.econtrol.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.contract.UserPassWordResetContract;
import com.mxkj.econtrol.model.UserPasswordRestModel;
import com.mxkj.econtrol.presenter.UserPassWordResetPresenter;
import com.mxkj.econtrol.utils.ResourceUtil;

import org.greenrobot.eventbus.EventBus;

/**
 *
 * 修改密码  / 忘记密码
 */
public class UserPassWordResetActivity extends BaseActivity implements UserPassWordResetContract.View, TextWatcher {
    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private EditText mEditPhone;
    private EditText mEditCode;
    private EditText mEditNewPass;
    private EditText mEditRePass;
    private TextView tv_tip; // 密码提示
    private Button mTvOk;
    private TextView mTvGetCode;
    private UserPassWordResetContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_pass_word_reset);
        super.onCreate(savedInstanceState);
        mPresenter = new UserPassWordResetPresenter(this, new UserPasswordRestModel());
    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        if (getIntent().getStringExtra("type").equals("CHANGE")){
        tv_title_content.setText("修改密码");
        }else {
        tv_title_content.setText("忘记密码");
        }

        mEditPhone = findView(R.id.edit_phone);
        mEditCode = findView(R.id.edit_verity_code);
        mTvGetCode = findView(R.id.tv_get_code);
        mEditNewPass = findView(R.id.edit_new_password);
        mEditRePass = findView(R.id.edit_repassword);
        mTvOk = findView(R.id.btn_ok);
        tv_tip = findView(R.id.tv_tip);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {
        mTvOk.setOnClickListener(this);
        mTvGetCode.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //手机号格式化xxx xxxx xxxx
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    mEditPhone.setText(sb.toString());
                    mEditPhone.setSelection(sb.toString().length());

                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(getPhone()) && !TextUtils.isEmpty(getVerityCode()) && !TextUtils.isEmpty(getPassword()) && !TextUtils.isEmpty(getRePassword())) {
                    mTvOk.setEnabled(true);
                    mTvOk.setBackgroundResource(R.drawable.login_regist_bg);
                } else {
                    mTvOk.setEnabled(false);
                    mTvOk.setBackgroundResource(R.drawable.login_regist_bg_50opaque);
                }
            }
        });
        mEditCode.addTextChangedListener(this);
        mEditNewPass.addTextChangedListener(this);
        mEditRePass.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_ok:
                resetPwd();
                break;
            case R.id.tv_title_left:
                finish();
                break;
        }
    }


    //获取验证码
    private void getCode() {
        if (TextUtils.isEmpty(getPhone())) {
            showToast(getString(R.string.please_input_phone));
            return;
        } else if (!getPhone().matches("^1[3|4|5|7|8][0-9]\\d{8}$")) {
            showToast(getString(R.string.please_enter_the_correct_phone_number));
            return;
        }
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvGetCode.setText("重发验证码（" + millisUntilFinished / 1000 + "s)");
                mTvGetCode.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
            }

            @Override
            public void onFinish() {
                mTvGetCode.setEnabled(true);
                mTvGetCode.setText("获取验证码");
                mTvGetCode.setTextColor(getResources().getColor(R.color.text_orangereg02));
            }
        };
        timer.start();
        mPresenter.getCode();
    }

    //重置密码
    private void resetPwd() {
        if (checkData()) {
            mPresenter.resetPassword();
        }
    }

    @Override
    public void setPresenter(UserPassWordResetContract.Presenter presenter) {

    }

    @Override
    public void getCodeFail(String msg) {
        showToast(msg);
    }

    @Override
    public void getCodeSuccess() {
        showToast(getString(R.string.get_code_success));
    }

    @Override
    public void resetFail(String msg) {
        showToast(msg);
    }

    @Override
    public void resetSuccess() {
        showToast(getString(R.string.reset_pwd_success));

        if (getIntent().getStringExtra("type").equals("CHANGE")){
            finish();
        }else {
            startToActivity(LoginActivity.class);
            finish();
            EventBus.getDefault().post(new EventBusUIMessage(Msg.CLEAR_ACITVITY));
        }

    }

    @Override
    public String getPassword() {
        return mEditNewPass.getText().toString();
    }

    @Override
    public String getRePassword() {
        return mEditRePass.getText().toString();
    }

    @Override
    public String getVerityCode() {
        return mEditCode.getText().toString();
    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString().replace(" ", "");
    }

    /**
     * @Desc: 校验用户输入的数据
     * @author:liangshan
     */
    public boolean checkData() {
        String tel = getPhone();
        String code = getVerityCode();
        String password = getPassword();
        String rePassword = getRePassword();
        if (TextUtils.isEmpty(tel)) {
            showToast(getString(R.string.please_input_phone));
            return false;
        } else if (!tel.matches("^1[3|4|5|7|8][0-9]\\d{8}$")) {
            showToast(getString(R.string.please_enter_the_correct_phone_number));
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            showToast(getString(R.string.please_input_code));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.please_input_password));
            return false;
        } else if (password.length() < 6) {
            showToast(getString(R.string.password_length_must_be_greater_than_6));
            return false;
        }
        if (TextUtils.isEmpty(rePassword)) {
            showToast(getString(R.string.please_input_rePassword));
            return false;
        }
        if (!password.equals(rePassword)) {
            showToast(getString(R.string.two_passwords_are_inconsistent));
            tv_tip.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(getPhone()) && !TextUtils.isEmpty(getVerityCode()) && !TextUtils.isEmpty(getPassword()) && !TextUtils.isEmpty(getRePassword())) {
            mTvOk.setEnabled(true);
            mTvOk.setBackgroundResource(R.drawable.login_regist_bg);
        } else {
            mTvOk.setEnabled(false);
            mTvOk.setBackgroundResource(R.drawable.login_regist_bg_50opaque);
        }
    }

}
