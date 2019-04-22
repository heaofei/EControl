package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;

/***
 * 关于我们
 */
public class AboutActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText("公司介绍");

    }

    @Override
    protected void initData() {

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
}
