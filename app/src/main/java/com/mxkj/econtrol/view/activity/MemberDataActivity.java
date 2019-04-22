package com.mxkj.econtrol.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.ResGethouseUserInfo;
import com.mxkj.econtrol.contract.HouseUserInfoContract;
import com.mxkj.econtrol.contract.HouseUserListContract;
import com.mxkj.econtrol.model.HouseUserInfoModel;
import com.mxkj.econtrol.model.HouseUserListModel;
import com.mxkj.econtrol.presenter.HouseUserInfoPresenter;
import com.mxkj.econtrol.presenter.HouseUserListPresenter;
import com.mxkj.econtrol.widget.CircleImageView;

import java.util.List;

/**
 * 家庭成员资料
 */
public class MemberDataActivity extends BaseActivity implements HouseUserInfoContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private TextView tv_name;
    private TextView tv_nickname;
    private TextView tv_both;
    private TextView tv_address;
    private TextView tv_gender;
    private CircleImageView imv_header;
    private ImageView imv_male, imv_female;

    private String userId;
    private HouseUserInfoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menber_data);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText("详细资料");
        imv_male = findView(R.id.imv_male);
        imv_female = findView(R.id.imv_female);
        tv_nickname = findView(R.id.tv_nickname);
        tv_name = findView(R.id.tv_name);
        tv_both = findView(R.id.tv_both);
        tv_address = findView(R.id.tv_address);
        tv_gender = findView(R.id.tv_gender);
        imv_header = findView(R.id.imv_header);

        userId = getIntent().getStringExtra("userId");
        mPresenter = new HouseUserInfoPresenter(this, new HouseUserInfoModel());
        mPresenter.getHouseUserInfo(userId);
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

    @Override
    public void getHouseUserInfoSuccess(ResGethouseUserInfo resGethouseUserInfo) {
        String content = new Gson().toJson(resGethouseUserInfo);

        tv_nickname.setText(resGethouseUserInfo.getNiceName());
        tv_name.setText(resGethouseUserInfo.getName());
        tv_both.setText(resGethouseUserInfo.getBorn());
        tv_address.setText(resGethouseUserInfo.getAddress());
        if (resGethouseUserInfo.getSex().equals("0")) {
//            imv_female.setImageResource(R.drawable.center_userinfo_choose_selected);
//            imv_male.setImageResource(R.drawable.center_userinfo_choose_default);
            tv_gender.setText("女");

        } else {
//            imv_male.setImageResource(R.drawable.center_userinfo_choose_selected);
//            imv_female.setImageResource(R.drawable.center_userinfo_choose_default);
            tv_gender.setText("男");

        }
        if (resGethouseUserInfo.getHeadPicture() != null && !TextUtils.isEmpty(resGethouseUserInfo.getHeadPicture())) {
            Glide.with(this).load(Config.BASE_PIC_URL + resGethouseUserInfo.getHeadPicture()).error(R.drawable.ic_no_head).into(imv_header);
        } else {
            imv_header.setImageResource(R.drawable.ic_no_head);
        }
    }

    @Override
    public void getHouseUserInfoFail(String msg) {
        showToast("获取详细资料失败!");
    }

    @Override
    public void setPresenter(HouseUserInfoContract.Presenter presenter) {

    }

}
