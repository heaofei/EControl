package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.contract.InformationContract;

public class InformationInfoActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;

    private RelativeLayout rl_info;
    private ImageView imv_header;
    private ImageView iv_edit;
    private TextView tv_nickname;

    private RelativeLayout rl_comment; // 评论
    private RelativeLayout rl_collectionup; // 收藏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_information_info);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
        tv_title_content.setText("我的");

        rl_comment = findView(R.id.rl_comment);
        rl_collectionup = findView(R.id.rl_collectionup);

        rl_info = findView(R.id.rl_info);
        imv_header = findView(R.id.imv_header);
        iv_edit = findView(R.id.iv_edit);
        tv_nickname = findView(R.id.tv_nickname);

    }

    @Override
    protected void initData() {
        tv_nickname.setText(APP.user.getNiceName());
        if (APP.user.getHeadPicture() != null && !TextUtils.isEmpty(APP.user.getHeadPicture())) {
            Glide.with(this)
                    .load(Config.BASE_PIC_URL + APP.user.getHeadPicture())
                    .error(R.drawable.ic_no_head)
                    .into(imv_header);
        }

    }

    @Override
    protected void initListener() {
        rl_info.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        rl_comment.setOnClickListener(this);
        rl_collectionup.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_info:
                startActivity(new Intent(InformationInfoActivity.this, UserInfoModifyActivity.class));
                break;
            case R.id.rl_comment: // 评论
                startActivity(new Intent(InformationInfoActivity.this, InformationCommentActivity.class));
                break;
            case R.id.rl_collectionup:  // 收藏
                startActivity(new Intent(InformationInfoActivity.this, InformationCollectionActivity.class));
                break;
        }
    }


}
