package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.contract.HousePartInfoContract;
import com.mxkj.econtrol.contract.InitailContract;
import com.mxkj.econtrol.model.HousePartInfoModel;
import com.mxkj.econtrol.presenter.HousePartInfoPresenter;
import com.mxkj.econtrol.utils.MyDialogUtil;

/**
 * 消息中心
 */
public class MessageCenterActivity extends BaseActivity  {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;

    private RelativeLayout rl_message;
    private RelativeLayout rl_notice;
    private ImageView iv_message_new;
    private ImageView iv_notice_new;
    private int systemNotify = 0; // 系统通知条数
    private int messageNotify = 0; // 消息提醒条数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_center);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_content.setText(getString(R.string.message_center));


        rl_message = (RelativeLayout) findViewById(R.id.rl_message);
        rl_notice = (RelativeLayout) findViewById(R.id.rl_notice);
        iv_message_new = (ImageView) findViewById(R.id.iv_message_new);
        iv_notice_new = (ImageView) findViewById(R.id.iv_notice_new);

        systemNotify = getIntent().getIntExtra("systemNotify",0);
        messageNotify = getIntent().getIntExtra("messageNotify",0);

        if (systemNotify>0) {
            iv_notice_new.setVisibility(View.VISIBLE);
        }
        if (messageNotify>0) {
            iv_message_new.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        rl_notice.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_message:

                Intent intent  = new Intent(this,MessageListActivity.class);
                intent.putExtra("type","MESSAGE");
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_notice:

                Intent intent02  = new Intent(this,MessageListActivity.class);
                intent02.putExtra("type","NOTICE");

                startActivityForResult(intent02, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1) {
            iv_message_new.setVisibility(View.INVISIBLE);
        }
        if (requestCode==2) {
            iv_notice_new.setVisibility(View.INVISIBLE);
        }
    }
}
