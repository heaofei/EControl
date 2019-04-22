package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseFragmentPagerAdapter;
import com.mxkj.econtrol.view.fragment.UseNumberFragment;
import com.mxkj.econtrol.view.fragment.UseTimeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用习惯
 * ***/
public class HabitActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;


    private XTabLayout tabLayout;
    private ViewPager viewPager;
    private UseNumberFragment useNumberFragment;
    private UseTimeFragment useTimeFragment;
    private List<Fragment> fragmentList;
    private BaseFragmentPagerAdapter baseFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_habit);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);

        tv_title_content.setText("使用习惯");

        tabLayout = (XTabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        fragmentList = new ArrayList<>();
        useNumberFragment = new UseNumberFragment();
        useTimeFragment = new UseTimeFragment();
        fragmentList.add(useNumberFragment);
//        fragmentList.add(useTimeFragment);
        baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(baseFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


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
