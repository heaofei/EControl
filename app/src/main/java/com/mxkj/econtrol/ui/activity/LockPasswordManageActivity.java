package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.view.activity.LockSubmitVerificationActivity;
import com.mxkj.econtrol.view.activity.SearchLockListActivity;

/***
 *  门锁密码管理
 */
public class LockPasswordManageActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private RelativeLayout rl_forget_psw;
    private RelativeLayout rl_regist_psw;

    private String lockId; // 门锁id
    private boolean isOwner; // 是否业主

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lock_password_manage);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText("密码管理");

        rl_forget_psw = findView(R.id.rl_forget_psw);
        rl_regist_psw = findView(R.id.rl_regist_psw);

        lockId = getIntent().getStringExtra("lockId");
        isOwner = getIntent().getBooleanExtra("isOwner",false);
        if (isOwner){
            rl_forget_psw.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        rl_forget_psw.setOnClickListener(this);
        rl_regist_psw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_forget_psw:  //  提交身份证验证

                Intent intent01 = new Intent(this, LockSubmitVerificationActivity.class);
                intent01.putExtra("lockId", lockId); // 锁id
                startActivity(intent01);

                break;
            case R.id.rl_regist_psw:  // 修改手势密码
                Intent intent = new Intent(this, LockGesturePasswordActivity.class);
                intent.putExtra("partPasswordChange", true); // 修改密码
                intent.putExtra("lockId", lockId); // 锁id
                startActivity(intent);
                break;
        }
    }


    /**
     * 处理eventBus消息
     *
     * @param msg
     */
    protected void handleMessage(EventBusMessage msg) {
        if (msg.getMsgType() == Msg.LOCK_APPLY_SUCCESS) { // 门锁审核提交后
            finish();
        }else  if (msg.getMsgType() == Msg.LOCK_PESSWORD_GRANT_ERROR) { // 手势密码错误5次
            finish();
        }

    }
}
