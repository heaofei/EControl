package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.contract.RoomContract;
import com.mxkj.econtrol.ui.adapter.SmartPartFragmentPagerAdapter;
import com.mxkj.econtrol.ui.fragment.AGFragment;
import com.mxkj.econtrol.ui.fragment.AirConditionerFragment;
import com.mxkj.econtrol.ui.fragment.BlowerFragment;
import com.mxkj.econtrol.ui.fragment.CurtainsFragment;
import com.mxkj.econtrol.ui.fragment.FloorHeatFragment;
import com.mxkj.econtrol.ui.fragment.LightFragment;
import com.mxkj.econtrol.ui.fragment.LockFragment;
import com.mxkj.econtrol.utils.ResourceUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

/****
 * 全部设备界面，
 * 之前用的是Fragment， 现在用activity装载之前的所有fragment
 *
 * **/

public class DeviceFragmentActivity extends BaseActivity {


    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;
    //智能切换
    private ViewPager mVpSmartPart;

    private SmartPart smartPart; // 部件信息
    //智能部件
    private List<Fragment> mSMFragments = new ArrayList<>();
    private List<String> mFragmentTitle  = new ArrayList<>();
    private SmartPartFragmentPagerAdapter mSMFragmentPagerAdapter;

    private LightFragment lightFragment; // 灯光Fragment
    private BlowerFragment blowerFragment;// 新风Fragment
    private AirConditionerFragment airConditionerFragment;// 空调Fragment
    private CurtainsFragment curtainsFragment;// = new CurtainsFragment();// 窗帘Fragment
    private LockFragment lockFragment;     // 门锁Fragment
    private List<LockFragment> mLockList = new ArrayList<>();
    private AGFragment mAGFragment;     // 雾化玻璃Fragment
    private FloorHeatFragment floorHeatFragment;     // 地暖Fragment


    private boolean isOwner;// 是否是业主

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_device_fragment);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
//        tv_title_content.setText("设备");

        mVpSmartPart = findView(R.id.vp_smart_part);


    }

    @Override
    protected void initData() {
        smartPart = (SmartPart) getIntent().getSerializableExtra("smartPart");
        isOwner = getIntent().getBooleanExtra("isOwner", false);
        tv_title_content.setText(TextUtil.setTest(smartPart.getName()));


        if (smartPart.getType().equals("1")) {         // 灯光
            
        } else if (smartPart.getType().equals("2")) { // 空调
            airConditionerFragment = new AirConditionerFragment();
            mSMFragments.add(airConditionerFragment);
            Bundle data = new Bundle();
            data.putString("device", smartPart.getId());
            data.putSerializable("smartPart", smartPart);
            airConditionerFragment.setArguments(data);

        } else if (smartPart.getType().equals("3")) { // 新风
            blowerFragment = new BlowerFragment();
            mSMFragments.add(blowerFragment);
            Bundle blowerData = new Bundle();
            blowerData.putString("device", smartPart.getId());
            blowerData.putSerializable("smartPart", smartPart);
            blowerFragment.setArguments(blowerData);
        } else if (smartPart.getType().equals("4")) { // 窗帘
            curtainsFragment = new CurtainsFragment();
            mSMFragments.add(curtainsFragment);// 窗帘
            Bundle data = new Bundle();
            data.putString("device", smartPart.getId());
            data.putSerializable("smartPart", smartPart);
            curtainsFragment.setArguments(data);
        } else if (smartPart.getType().equals("5")) {// 门锁
            lockFragment = new LockFragment();
            mLockList.add(lockFragment);
            mSMFragments.add(lockFragment); // 门锁
            Bundle data = new Bundle();
            data.putString("device", smartPart.getId());
            data.putString("houseId", getHouseId());
            data.putBoolean("isOwner", isOwner);
            data.putSerializable("smartPart", smartPart);
            lockFragment.setArguments(data);
        } else if (smartPart.getType().equals("6")) {// 雾化玻璃
        } else if (smartPart.getType().equals("7")) {// 地暖
            floorHeatFragment = new FloorHeatFragment();
            mSMFragments.add(floorHeatFragment);
            Bundle data = new Bundle();
            data.putString("device", smartPart.getId());
            data.putSerializable("smartPart", smartPart);
            floorHeatFragment.setArguments(data);
        }

        mFragmentTitle.add("");
        FragmentManager fm = getSupportFragmentManager();
        mSMFragmentPagerAdapter = new SmartPartFragmentPagerAdapter(fm, mSMFragments,mFragmentTitle);
        mVpSmartPart.setAdapter(mSMFragmentPagerAdapter);

        
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


    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
    }


}
