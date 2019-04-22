package com.mxkj.econtrol.view.activity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.contract.HousePartInfoContract;
import com.mxkj.econtrol.contract.InitailContract;
import com.mxkj.econtrol.model.HousePartInfoModel;
import com.mxkj.econtrol.presenter.HousePartInfoPresenter;

public class HousePartInfoActivity extends BaseActivity implements HousePartInfoContract.View {

    private HousePartInfoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_house_part_info);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter = new HousePartInfoPresenter(this, new HousePartInfoModel());
        mPresenter.getHouseAllPartList("ff8080815cc99db0015d4f3be3bf02d9");
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void setPresenter(InitailContract.Presenter presenter) {

    }

    @Override
    public void getHouseAllPartListSuccess(ResGetHouseAllPartList resGetHouseAllPartList) {
        String content = new Gson().toJson(resGetHouseAllPartList);

    }

    @Override
    public void getHouseAllPartListFail(String msg) {

    }
}
