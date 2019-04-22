package com.mxkj.econtrol.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageReply;
import com.mxkj.econtrol.bean.request.ReqFeedbackSubmit;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResAppPushMessageReply;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

/***
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private EditText et_content, et_phone;
    private TextView tv_error;
    private Button bt_submit;
    private LinearLayout ll_title;
    private ViewStub stub_import;
    private boolean isSubmit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_helper);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText(getString(R.string.feed_back));

        et_content = findView(R.id.et_content);
        et_phone = findView(R.id.et_phone);
        tv_error = findView(R.id.tv_error);
        ll_title = findView(R.id.ll_title);
        bt_submit = findView(R.id.bt_submit);

        stub_import = findView(R.id.stub_import);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_content.getText().toString().trim().length() >= 10) {
                    tv_error.setVisibility(View.INVISIBLE);
                } else {
                    tv_error.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(et_content.getText().toString().trim()) && !TextUtils.isEmpty(et_phone.getText().toString().trim()) && et_content.getText().toString().trim().length() >= 10) {
                    bt_submit.setBackgroundResource(R.drawable.login_regist_bg);
                    bt_submit.setEnabled(true);
                } else {
                    bt_submit.setBackgroundResource(R.drawable.login_regist_bg_50opaque);
                    bt_submit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(et_content.getText().toString().trim()) && !TextUtils.isEmpty(et_phone.getText().toString().trim()) && et_content.getText().toString().trim().length() >= 10) {
                    bt_submit.setBackgroundResource(R.drawable.login_regist_bg);
                    bt_submit.setEnabled(true);
                } else {
                    bt_submit.setBackgroundResource(R.drawable.login_regist_bg_50opaque);
                    bt_submit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//        et_content.setText("感谢您使用方胜智能APP，使用过程中有任何意见或建议请反馈给我们。");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                if (isSubmit){
                    finish();
                }else {
                     showTipDialog();
                }
                break;
            case R.id.bt_submit:

              /*  SharedPreferencesUtils.setParam(this, "newIp", et_phone.getText().toString().trim());//
                finish();*/

                submitFeedBack(et_content.getText().toString().trim(),et_phone.getText().toString().trim());

                break;
        }
    }

    /***
     * 显示推出编辑弹窗
     */
    private void showTipDialog() {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                finish();
            }
        };
        dialog.showTipDialog(getString(R.string.giveup_edit), "");
    }

    private void submitFeedBack(String content, String phone) {
        final ReqFeedbackSubmit request = new ReqFeedbackSubmit(content, phone);
        RetrofitUtil.getInstance().getmApiService()
                .feedbackSubmit(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        showToast(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse response) {
                        String content = new Gson().toJson(response);
                        stub_import.setVisibility(View.VISIBLE);
                        ll_title.setVisibility(View.GONE);
                        bt_submit.setVisibility(View.GONE);
                        isSubmit = true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (isSubmit){
            finish();
        }else {
            showTipDialog();
        }
//        super.onBackPressed();
    }
}
