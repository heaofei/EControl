package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;

/**
 * 历史记录
 * ***/
public class HistoryActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private RelativeLayout rl_controllog; // 操作历史
    private RelativeLayout rl_use_num;      // 使用次数
    private RelativeLayout rl_use_time;      // 使用时长

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_history);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);

        tv_title_content.setText("历史记录");


        rl_controllog = findView(R.id.rl_controllog);
        rl_use_num = findView(R.id.rl_use_num);
        rl_use_time = findView(R.id.rl_use_time);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        rl_controllog.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        rl_use_num.setOnClickListener(this);
        rl_use_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_controllog:// 操作历史
                startActivity(new Intent(this, HouseControlLogActivity.class));
                break;
            case R.id.rl_use_num:// 使用次数

                startActivity(new Intent(this, HabitActivity.class));
//                startActivity(new Intent(this, UsageStatisticsActivity.class));
                break;
            case R.id.rl_use_time:// 使用时长
                startActivity(new Intent(this, UsageStatisticsActivity.class));
                break;
        }
    }



}
