package com.mxkj.econtrol.view.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqUserLogin;
import com.mxkj.econtrol.bean.response.ResUserLogin;
import com.mxkj.econtrol.contract.RegistContract;
import com.mxkj.econtrol.model.RegistModel;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.RegistPresenter;
import com.mxkj.econtrol.ui.activity.PrivateAndPolicy;
import com.mxkj.econtrol.utils.AndroidBug5497Workaround;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.RSAPKCS8X509Utils;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.StatusBarUtils;
import com.mxkj.econtrol.widget.ClearEditText;
import com.mxkj.econtrol.widget.MyScrollView;

import org.greenrobot.eventbus.EventBus;

/**
 * 注册界面
 */
public class RegistActivity extends BaseActivity implements RegistContract.View, TextWatcher {


    private com.mxkj.econtrol.widget.ClearEditText et_input_phone;
    private android.support.design.widget.TextInputLayout tt_code;
    private com.mxkj.econtrol.widget.ClearEditText et_input_code;
    private TextView tv_get_code;
    private com.mxkj.econtrol.widget.ClearEditText et_input_password;
    private ImageView iv_password_visibility;
    private com.mxkj.econtrol.widget.ClearEditText et_input_repassword;
    private ImageView iv_repassword_visibility;
    private Button btn_regist;
    private TextView tv_service;
    private TextView tv_policy;
    private TextView tv_title_left;
    private TextView tv_tip; // 密码提示
    private LinearLayout ll_layout;
    private LinearLayout ll_bottom;
    private LinearLayout ll_head;
    private MyScrollView myscrollView;
   /* private RelativeLayout rl_item;
    private RelativeLayout rl_title;*/
    private boolean flag_visibility = false;
    private boolean flag_visibility02 = false;

    private RegistContract.Presenter mRegistPresenter;

    private int keyHeight = 0;      //软件盘弹起后所占高度
    private float scale = 0.01f;     //logo缩放比例


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_regist);
        super.onCreate(savedInstanceState);
        mRegistPresenter = new RegistPresenter(this, new RegistModel());

        if (isFullScreen(this)) {
            AndroidBug5497Workaround.assistActivity(this);
        }
        int screenHeight = this.getResources().getDisplayMetrics().heightPixels;
        keyHeight = screenHeight / 3;
    }


    private boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    @Override
    public void setStatusBar(boolean isShow, int drawable) {
        super.setStatusBar(false, drawable);
        StatusBarUtils.with(this)
                .init();
    }

    @Override
    protected void initView() {
        mRegistPresenter = new RegistPresenter(this, new RegistModel());

        et_input_phone = (com.mxkj.econtrol.widget.ClearEditText) findViewById(R.id.et_input_phone);
        et_input_code = (com.mxkj.econtrol.widget.ClearEditText) findViewById(R.id.et_input_code);
        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        et_input_password = (com.mxkj.econtrol.widget.ClearEditText) findViewById(R.id.et_input_password);
        iv_password_visibility = (ImageView) findViewById(R.id.iv_password_visibility);
        et_input_repassword = (com.mxkj.econtrol.widget.ClearEditText) findViewById(R.id.et_input_repassword);
        iv_repassword_visibility = (ImageView) findViewById(R.id.iv_repassword_visibility);
        btn_regist = (Button) findViewById(R.id.btn_regist);
        tv_service = (TextView) findViewById(R.id.tv_service);
        tv_policy = (TextView) findViewById(R.id.tv_policy);
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        ll_layout = findView(R.id.ll_layout);
        ll_bottom = findView(R.id.ll_bottom);
        ll_head = findView(R.id.ll_head);
        tv_tip = findView(R.id.tv_tip);
        myscrollView = findView(R.id.myscrollView);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btn_regist.setOnClickListener(this);
        tv_get_code.setOnClickListener(this);
        tv_service.setOnClickListener(this);
        tv_policy.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        iv_password_visibility.setOnClickListener(this);
        iv_repassword_visibility.setOnClickListener(this);
        et_input_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(et_input_phone.getText().toString().trim()) && !TextUtils.isEmpty(et_input_code.getText().toString().trim())
                        && !TextUtils.isEmpty(et_input_password.getText().toString().trim()) && !TextUtils.isEmpty(et_input_repassword.getText().toString().trim())) {
                    btn_regist.setBackgroundResource(R.drawable.login_regist_bg_white360);
                    btn_regist.setEnabled(true);
                } else {
                    btn_regist.setBackgroundResource(R.drawable.login_regist_bg_white_alpha40);
                    btn_regist.setEnabled(false);
                }

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
                    et_input_phone.setText(sb.toString());
//                    et_input_phone.setSelection(index);
                    et_input_phone.setSelection(sb.toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_input_code.addTextChangedListener(this);
        et_input_password.addTextChangedListener(this);
        et_input_repassword.addTextChangedListener(this);
        ll_layout.setOnClickListener(this);
//        setHightListener(et_input_phone);



        myscrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        myscrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom != 0 && oldBottom != 0 && (oldBottom - bottom > keyHeight)) {
                    int dist = ll_layout.getBottom() - bottom;
                    if (dist>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(ll_layout, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        zoomIn(ll_head, dist);
                    }
                    ll_bottom.setVisibility(View.INVISIBLE);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    if ((ll_layout.getBottom() - oldBottom)>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(ll_layout, "translationY", ll_layout.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        zoomOut(ll_head);
                    }
                    ll_bottom.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    /**
     * 缩小
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    /**
     * f放大
     */
    public void zoomOut(final View view) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }


    @Override
    public void onClick(View v) {
        if (v == btn_regist && checkData()) {
            showLoading();
            mRegistPresenter.regist();
        } else if (v == tv_get_code) {
            getSmsCode();
        } else if (v == tv_service) {
            Intent intent = new Intent(RegistActivity.this, PrivateAndPolicy.class);
            intent.putExtra("type", "agreement");
            startToActivity(intent);
        } else if (v == tv_policy) {
            Intent intent = new Intent(RegistActivity.this, PrivateAndPolicy.class);
            intent.putExtra("type", "privatePolicy");
            startToActivity(intent);
        } else if (v == iv_password_visibility) {
            showOrHidePsw();
        } else if (v == iv_repassword_visibility) {
            showOrHidePsw02();
        } else if (v == tv_title_left) {
            finish();
        } else if (v == ll_layout) {
            hideSoftInput();
        }

    }

    public void getSmsCode() {
        if (TextUtils.isEmpty(getTel())) {
            showToast(getString(R.string.please_input_phone));
            return;
        }
        mRegistPresenter.userRegistSms();
    }

    @Override
    public void registFail(String msg) {
        dismissLoading();// 对话框消失
        showToast(msg);
    }

    @Override
    public void registSuccess() {
        dismissLoading();// 对话框消失
        showToast(getString(R.string.regist_success));
//        SharedPreferencesUtils.setParam(this, "token", APP.user.getToken());
//        SharedPreferencesUtils.setParam(this, "userName", APP.user.getUserName());
        isClose = false;
        EventBus.getDefault().post(new EventBusUIMessage(Msg.CLEAR_ACITVITY));

        ReqUserLogin reqUserLogin = new ReqUserLogin();
        String userName =et_input_phone.getText().toString().trim();
        userName =  userName.replaceAll(" ", "");
        reqUserLogin.setUserName(userName);
        reqUserLogin.setPassWord(RSAPKCS8X509Utils.getInstance().encryptWithBase64(et_input_password.getText().toString().trim()));
        APISubcriber<ResUserLogin> subcriber = new APISubcriber<ResUserLogin>() {
            @Override
            public void onFail(ResUserLogin baseResponse, String msg) {
                startToActivity(LoginActivity.class);
                finish();
            }
            @Override
            public void onSuccess(ResUserLogin resUserLogin) {
                APP.user = resUserLogin;
                APP.isLogin = true;
                APP.headerData.getHeaderToken().setToken(resUserLogin.getToken());
                APP.headerData.getHeaderToken().setUserName(resUserLogin.getUserName());
                SharedPreferencesUtils.setParam(RegistActivity.this, "houseId",resUserLogin.getDefaultHouseId());// 默认的房子id
                SharedPreferencesUtils.setParam(RegistActivity.this, "token", APP.user.getToken());
                SharedPreferencesUtils.setParam(RegistActivity.this, "userName", APP.user.getUserName());
                //链接tcp
                TcpClient.getInstacne().init();
                startToActivity(MainNewActivity.class);
                finish();
            }
        };
        subcriber.setShowLoding(false);
        RetrofitUtil.getInstance().getmApiService().userLogin(reqUserLogin.toJsonStr()).compose(new APITransFormer<ResUserLogin>()).subscribe(subcriber);

    }

    @Override
    public String getUserName() {
        return et_input_phone.getText().toString().trim();
    }

    @Override
    public String getUserPassword() {
        return et_input_password.getText().toString();
    }

    @Override
    public String getRePassword() {
        return et_input_repassword.getText().toString();
    }

    @Override
    public String getTel() {
        return et_input_phone.getText().toString().replace(" ", "");
    }

    @Override
    public String getCode() {
        return et_input_code.getText().toString();
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void sendCodeSuccess() {
        showToast("验证码发送成功，请注意查收");
        tv_get_code.setEnabled(false);
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_get_code.setText("重发验证码（" + millisUntilFinished / 1000 + "s)");
                tv_get_code.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
            }

            @Override
            public void onFinish() {
                tv_get_code.setEnabled(true);
                tv_get_code.setText("获取验证码");
                tv_get_code.setTextColor(getResources().getColor(R.color.text_orangereg02));
            }
        };
        timer.start();
    }

    @Override
    public void setPresenter(RegistContract.Presenter presenter) {

    }

    /**
     * @Desc: 校验用户输入的数据
     * @author:liangshan
     */
    public boolean checkData() {
        String tel = getTel();
        String code = getCode();
        String userName = getUserName();
        String password = getUserPassword();
        String rePassword = getRePassword();
        if (TextUtils.isEmpty(tel)) {
            showMsg(getString(R.string.please_input_phone));
            return false;
        } else if (!tel.matches("^1[3|4|5|7|8][0-9]\\d{8}$")) {
            showMsg(getString(R.string.please_enter_the_correct_phone_number));
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            showMsg(getString(R.string.please_input_code));
            return false;
        }
        if (TextUtils.isEmpty(userName)) {
            showMsg(getString(R.string.please_input_user_name));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            showMsg(getString(R.string.please_input_password));
            return false;
        } else if (password.length() < 6) {
            showMsg(getString(R.string.password_length_must_be_greater_than_6));
            return false;
        }
        if (TextUtils.isEmpty(rePassword)) {
            showMsg(getString(R.string.please_input_rePassword));
            return false;
        }
        if (!password.equals(rePassword)) {
            showMsg(getString(R.string.two_passwords_are_inconsistent));
            tv_tip.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }


    private void showOrHidePsw() {
        if (!flag_visibility) {// 可视
            et_input_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            flag_visibility = true;
            iv_password_visibility.setImageResource(R.drawable.hide);
        } else if (flag_visibility) {// 隐藏
            et_input_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            flag_visibility = false;
            iv_password_visibility.setImageResource(R.drawable.display);
        }
        et_input_password.setSelection(et_input_password.length());
    }

    private void showOrHidePsw02() {
        if (!flag_visibility02) {// 可视
            et_input_repassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            flag_visibility02 = true;
            iv_repassword_visibility.setImageResource(R.drawable.hide);
        } else if (flag_visibility02) {// 隐藏
            et_input_repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            flag_visibility02 = false;
            iv_repassword_visibility.setImageResource(R.drawable.display);
        }
        et_input_repassword.setSelection(et_input_repassword.length());
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下的是返回键，并且没有重复
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            // 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画
//            overridePendingTransition(R.anim.activity_open, 0);
            return false;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(et_input_phone.getText().toString().trim()) && !TextUtils.isEmpty(et_input_code.getText().toString().trim())
                && !TextUtils.isEmpty(et_input_password.getText().toString().trim()) && !TextUtils.isEmpty(et_input_repassword.getText().toString().trim())) {
            btn_regist.setBackgroundResource(R.drawable.login_regist_bg_white360);
            btn_regist.setEnabled(true);
        } else {
            btn_regist.setBackgroundResource(R.drawable.login_regist_bg_white_alpha40);
            btn_regist.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

/*
    public void setHightListener(ClearEditText hightListener) {
        hightListener.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                RegistActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = RegistActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                LogUtil.i("=======jianpanGaodu " + "Size: " + heightDifference);
                if (heightDifference > 0) { // 判断键盘高度是多少， 然后主题父布局上移一半
                    rl_item.scrollTo(0, heightDifference / 2);
                }
                else {
//                    rl_item.scrollTo(0,rl_title.getHeight());
                }
            }
        });
    }*/


    /**
     * 动态隐藏软键盘
     */
    private void hideSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = getCurrentFocus();
        if (view == null) view = new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


