package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.view.activity.SearchLockListActivity;

import pl.droidsonroids.gif.GifImageView;

/***
 * 添加锁提示页
 */
public class LockAddTipsActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_tips_setting;
    private ImageView iv_imageView;
    private boolean isShowTips1 = true;
    private boolean isShowTips2 = true;

    private Button btn_next;
    private String mPartId;// 门锁部件id
    private String mGatewaySn;// 网关sn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lock_add_tips);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_tips_setting = findView(R.id.tv_tips_setting);
//        tv_title_content.setText("锁添加提示");

        iv_imageView = findView(R.id.iv_imageView);
        btn_next = findView(R.id.btn_next);
        mPartId = getIntent().getStringExtra("partId");
        mGatewaySn = getIntent().getStringExtra("GatewaySn");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_tips_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.btn_next:
                if (isShowTips1) {
                    iv_imageView.setImageResource(R.drawable.ic_bound_doorlock2);
                    tv_tips_setting.setVisibility(View.INVISIBLE);
                    isShowTips1 = false;
                } else if (isShowTips2) {
                    iv_imageView.setImageResource(R.drawable.ic_bound_doorlock3);
                    isShowTips2 = false;
                } else {
                    Intent intent = new Intent(this, SearchLockListActivity.class);
                    intent.putExtra("partId", mPartId);
                    intent.putExtra("GatewaySn", mGatewaySn);
                    startToActivity(intent);
                    finish();
                }
                break;
            case R.id.tv_tips_setting:
                if (isShowTips1) {
                    iv_imageView.setImageResource(R.drawable.ic_bound_doorlock2);
                    tv_tips_setting.setVisibility(View.INVISIBLE);
                    isShowTips1 = false;
                }
                break;
        }
    }

    /**
     * 处理eventBus消息
     *
     * @param msg
     */
    protected void handleMessage(EventBusMessage msg) {
        if (msg.getMsgType() == Msg.LOCK_ADD_SUCCESS) { // 门锁添加成功
            finish();
        }

    }
}
