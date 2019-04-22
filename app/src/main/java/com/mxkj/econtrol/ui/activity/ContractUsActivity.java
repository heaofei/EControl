package com.mxkj.econtrol.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;

public class ContractUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contract_us);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findView(R.id.imv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();

    }
}
