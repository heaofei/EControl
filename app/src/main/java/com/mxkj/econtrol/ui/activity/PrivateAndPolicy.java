package com.mxkj.econtrol.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;

/**
 * 服务协议
 *
 */
public class PrivateAndPolicy extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_private_and_policy);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        mWebView = findView(R.id.web_view);

        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);


    }

    @Override
    protected void initData() {
        String type = getIntent().getStringExtra("type");
        if (type.equals("agreement")) {
            tv_title_content.setText("服务协议");
            mWebView.loadUrl("file:///android_asset/agreement.html");
        } else if (type.equals("privatePolicy")) {
            tv_title_content.setText("隐私政策");
            mWebView.loadUrl("file:///android_asset/privatePolicy.html");
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
}
