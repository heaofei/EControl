package com.mxkj.econtrol.view.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.contract.LoginContract;
import com.mxkj.econtrol.model.LoginModel;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.LoginPresenter;
import com.mxkj.econtrol.ui.activity.FeedBackActivity;
import com.mxkj.econtrol.utils.AndroidBug5497Workaround;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.RSAPKCS8X509Utils;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.StatusBarUtils;
import com.mxkj.econtrol.widget.ClearEditText;
import com.mxkj.econtrol.widget.MyScrollView;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnFocusChangeListener {

    //用户名
    private ClearEditText edit_user_name;
    //密码
    private ClearEditText edt_password;
    //登录按钮
    private Button btn_login;
    private TextView mTvFindPass;
    private TextView mTVRegist;
    private TextView tv_title_left;
    private LoginContract.Presenter mPresenter;
    private ImageView iv_password_visibility;
    private ImageView iv_hear;
    private MyScrollView myscrollView;
    private LinearLayout ll_layout;
    private LinearLayout ll_head;
    private int titleHeight = 127;
    private boolean flag_visibility = false;

    private int keyHeight = 0;      //软件盘弹起后所占高度
    private float scale = 0.01f;     //logo缩放比例


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);

        if (isFullScreen(this)) {
            AndroidBug5497Workaround.assistActivity(this);
        }
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
        ll_layout = findView(R.id.ll_layout);
        ll_head = findView(R.id.ll_head);
        btn_login = findView(R.id.btn_login);
        edit_user_name = findView(R.id.edit_user_name);
        edt_password = findView(R.id.edt_password);
        mTVRegist = findView(R.id.tv_regist);
        tv_title_left = findView(R.id.tv_title_left);
        mTvFindPass = findView(R.id.tv_forgot_pass);

        myscrollView = findView(R.id.myscrollView);
        iv_password_visibility = findView(R.id.iv_password_visibility);
        iv_hear = findView(R.id.iv_hear);
        String userName = getIntent().getStringExtra("userName");
        if (!TextUtils.isEmpty(userName)) {
            edit_user_name.setText(userName);
        }
    }

    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(this, new LoginModel());

        int screenHeight = this.getResources().getDisplayMetrics().heightPixels;
        keyHeight = screenHeight / 3;

    }

    @Override
    protected void initListener() {
        ll_layout.setOnClickListener(this);
        iv_password_visibility.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        mTvFindPass.setOnClickListener(this);
        mTVRegist.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        edit_user_name.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        edit_user_name.setOnFocusChangeListener(this);
        edt_password.setOnFocusChangeListener(this);
        edt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(edit_user_name.getText().toString().trim()) && !TextUtils.isEmpty(edt_password.getText().toString().trim())) {
                    btn_login.setBackgroundResource(R.drawable.login_regist_bg_white360);
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setBackgroundResource(R.drawable.login_regist_bg_white_alpha40);
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edit_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(edit_user_name.getText().toString().trim()) && !TextUtils.isEmpty(edt_password.getText().toString().trim())) {
                    btn_login.setBackgroundResource(R.drawable.login_regist_bg_white360);
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setBackgroundResource(R.drawable.login_regist_bg_white_alpha40);
                    btn_login.setEnabled(false);
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
                    edit_user_name.setText(sb.toString());
                    edit_user_name.setSelection(sb.toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



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
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    if ((ll_layout.getBottom() - oldBottom)>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(ll_layout, "translationY", ll_layout.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        zoomOut(ll_head);
                    }
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
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_layout:
                hideSoftInput();
                break;
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.btn_login:
                if (checkLoginData())
//                    showLoading();
                    mPresenter.login();
                break;
            case R.id.tv_regist:
                startToActivity(RegistActivity.class);
//                finish();
                break;
            case R.id.tv_forgot_pass:
                Intent intent = new Intent(this, UserPassWordResetActivity.class);
                intent.putExtra("type", "FORGET");
                startActivity(intent);

//                startActivity(new Intent(this, FeedBackActivity.class));

                break;
        /*    case R.id.imv_close:
                startToActivity(PublicCommunityActivity.class);
                finish();
                break;*/
            case R.id.iv_password_visibility:
                showOrHidePsw();
                break;
        }
    }

    @Override
    public String getUserName() {
        String userName = edit_user_name.getText().toString();
        return userName.replace(" ", "");
    }

    @Override
    public String getPassword() {
        String password = edt_password.getText().toString();
        return password;
    }

    @Override
    public void showFailedError(String msg) {
        dismissLoading();// 对话框消失
        showToast("登陆失败：" + msg);
    }

    @Override
    public void loginSuccess() {
        //链接tcp
        TcpClient.getInstacne().init();

        dismissLoading();// 对话框消失
        SharedPreferencesUtils.setParam(this, "psw", RSAPKCS8X509Utils.getInstance().encryptWithBase64(getPassword()));
        SharedPreferencesUtils.setParam(this, "token", APP.user.getToken());
        SharedPreferencesUtils.setParam(this, "userName", APP.user.getUserName());
        isClose = false;
        EventBus.getDefault().post(new EventBusUIMessage(Msg.CLEAR_ACITVITY));
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //用userid做Alias 类型：tongzhi
        mPushAgent.addAlias(APP.user.getUserId(), "tongzhi", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Log.i("loginalias",message);
            }
        });

//        startToActivity(MainActivity.class);
        startToActivity(MainNewActivity.class);

        finish();
    }

    @Override
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.SAMRT_PART_STATE_CHANGE:
                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    /**
     * @Desc: 检查数据
     * @author:liangshan
     */
    public boolean checkLoginData() {
        if (TextUtils.isEmpty(edit_user_name.getText().toString())) {
            showToast(getString(R.string.please_input_phone));
            return false;
        }
        if (!edit_user_name.getText().toString().replace(" ", "").matches("^1[3|4|5|7|8][0-9]\\d{8}$")) {
            showToast("请输入正确的手机号码！");
            return false;
        }
        if (TextUtils.isEmpty(edt_password.getText().toString())) {
            showToast(getString(R.string.please_input_password));
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        View view = null;
        switch (v.getId()) {

            case R.id.edit_user_name:
                break;

            case R.id.edt_password:
                break;
        }
    }

    private void showOrHidePsw() {
        if (!flag_visibility) {// 可视
            edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            flag_visibility = true;
            iv_password_visibility.setImageResource(R.drawable.hide);
        } else if (flag_visibility) {// 隐藏
            edt_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            flag_visibility = false;
            iv_password_visibility.setImageResource(R.drawable.display);
        }
        edt_password.setSelection(edt_password.length());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下的是返回键，并且没有重复
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            // 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画
//            overridePendingTransition(R.anim.activity_close, 0);
            return false;
        }
        return false;
    }

  /*  public void setHightListener(ClearEditText hightListener) {
        hightListener.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                LoginActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight =  LoginActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                LogUtil.i("=======Keyboard Size" + "Size: " + heightDifference);
                if (heightDifference>0) { // 判断键盘高度是多少， 然后主题父布局上移一半
                    iv_hear.setVisibility(View.INVISIBLE);
                }else {
//                    rl_item.scrollTo(0,titleHeight);
                    iv_hear.setVisibility(View.VISIBLE);
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
