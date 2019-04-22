package com.mxkj.econtrol.view.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragmentActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.response.ResGetScenePartList;
import com.mxkj.econtrol.bean.response.Room;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.tcpcmd.MessageNotify;
import com.mxkj.econtrol.bean.tcpcmd.RegistCMD;
import com.mxkj.econtrol.contract.RoomContract;
import com.mxkj.econtrol.model.RoomModel;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.RoomPresenter;
import com.mxkj.econtrol.ui.adapter.SmartPartFragmentPagerAdapter;
import com.mxkj.econtrol.ui.fragment.AGFragment;
import com.mxkj.econtrol.ui.fragment.AirConditionerFragment;
import com.mxkj.econtrol.ui.fragment.BlowerFragment;
import com.mxkj.econtrol.ui.fragment.CurtainsFragment;
import com.mxkj.econtrol.ui.fragment.FloorHeatFragment;
import com.mxkj.econtrol.ui.fragment.LightFragment;
import com.mxkj.econtrol.ui.fragment.LockFragment;
import com.mxkj.econtrol.ui.fragment.TestFragment;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by liangshan on 2017/3/31.
 *
 * @Description: 房间activity
 */

public class RoomActivity extends BaseFragmentActivity implements RoomContract.View, CommonAdapter.OnItemClickListener,AGFragment.CleanEditInterface {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_title_right;

    //智能切换
    private ViewPager mVpSmartPart;
    //场景切换
    private XTabLayout tabLayout;
    //智能部件
    private List<Fragment> mSMFragments;
    private List<String> mFragmentTitle;
    private RoomContract.Presenter mPresenter;
    private SmartPartFragmentPagerAdapter mSMFragmentPagerAdapter;
    //初始化场景索引
    private int initIndex;
    private String houseId;
    private String mDevice;// 中控设备id
    private boolean isOwner;// 是否是业主


    private List<Room> mScenes;
    private LightFragment lightFragment; // 灯光Fragment
    private BlowerFragment blowerFragment;// 新风Fragment
    private AirConditionerFragment airConditionerFragment;// 空调Fragment
    private CurtainsFragment curtainsFragment;// = new CurtainsFragment();// 窗帘Fragment
    private LockFragment lockFragment;     // 门锁Fragment
    private List<LockFragment> mLockList = new ArrayList<>();
    private AGFragment mAGFragment;     // 雾化玻璃Fragment
    private FloorHeatFragment floorHeatFragment;     // 地暖Fragment
    private TestFragment testFragment;     // 测试Fragment

    private ResGetScenePartList deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scene);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);

        iv_title_right.setVisibility(View.GONE);

        mVpSmartPart = findView(R.id.vp_smart_part);
        tabLayout = (XTabLayout) findViewById(R.id.tabLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

    }

    @Override
    protected void initData() {
        mPresenter = new RoomPresenter(this, new RoomModel());
        mScenes = (List<Room>) getIntent().getSerializableExtra("scenes");
        isOwner = getIntent().getBooleanExtra("isOwner", false);
//        isOwner = true;

        if (mScenes != null && mScenes.size() > 0) {
            mDevice = mScenes.get(0).getDevice();
        }
        initIndex = getIntent().getIntExtra("index", 0);
        houseId = getIntent().getStringExtra("houseId");
        mSMFragments = new ArrayList<>();
        mFragmentTitle = new ArrayList<>();


        //向tcp服务器注册
        RegistCMD registCMD = new RegistCMD();
        TcpClient.getInstacne().sendCMD(registCMD);

        /***获取房间内设备列表*/
        mPresenter.getScenePartList(mScenes.get(initIndex).getId());
        tv_title_content.setText(mScenes.get(initIndex).getName());
    }


    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        iv_title_right.setOnClickListener(this);
        mVpSmartPart.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               /* if ( lightFragment.getUserVisibleHint()){  // getUserVisibleHint方法不起作用
                    iv_title_right.setVisibility(View.VISIBLE);
                }else {
                    iv_title_right.setVisibility(View.GONE);
                }*/
                // 以上判断Fragment是否显示的判断不起作用， 才判断，灯设备的列表是否显示“设置”按钮
               /* if (deviceList!=null && deviceList.getLightList().size() > 0 && mVpSmartPart.getCurrentItem() ==0) {
                    iv_title_right.setVisibility(View.VISIBLE);
                }else {
                    iv_title_right.setVisibility(View.GONE);
                }*/
                if (state == 0) { // state==0 是切换完成的状态
                    if (mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof LightFragment) {
                        iv_title_right.setImageResource(R.drawable.home_control_navbar_setting);
                        iv_title_right.setVisibility(View.GONE);
                    } else if ( mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof AGFragment) {
                        iv_title_right.setImageResource(R.drawable.home_control_navbar_setting);
                        iv_title_right.setVisibility(View.VISIBLE);
                    }else if (isOwner && mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof LockFragment) {
                        iv_title_right.setImageResource(R.drawable.ic_lock_power_setting);
                        iv_title_right.setVisibility(View.VISIBLE);
                    } else {
                        iv_title_right.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void setPresenter(RoomContract.Presenter presenter) {
    }


    @Override
    public void getScenePartListSuccess(ResGetScenePartList resGetScenePartList) {
        deviceList = resGetScenePartList;
        Gson gson = new Gson();
        String content = gson.toJson(resGetScenePartList);
        mSMFragments.clear();

            /* testFragment = new TestFragment();
                mSMFragments.add(testFragment);
                mFragmentTitle.add("测试Fragment");*/

        for (int i = 0; i < resGetScenePartList.getSmPartList().size(); i++) {
            String type = resGetScenePartList.getSmPartList().get(i).getType();
            SmartPart sm = resGetScenePartList.getSmPartList().get(i);
            if (resGetScenePartList.getSmPartList().get(i).getType().equals("1")) {         // 灯光
                if (resGetScenePartList.getLightList().size() > 0 && lightFragment == null) {// 所有的灯光都只创建一个Fragment操作
                    lightFragment = new LightFragment();
                    mSMFragments.add(lightFragment);
                    mFragmentTitle.add(ResourceUtil.getString(R.string.light));
                    Bundle bundle = new Bundle();
                    bundle.putString("device", mDevice);
                    bundle.putSerializable("data", (ArrayList) resGetScenePartList.getLightList());
                    lightFragment.setArguments(bundle);
//                    iv_title_right.setVisibility(View.VISIBLE);
                }
            } else if (resGetScenePartList.getSmPartList().get(i).getType().equals("2")) { // 空调
                airConditionerFragment = new AirConditionerFragment();
                mSMFragments.add(airConditionerFragment);
                mFragmentTitle.add(resGetScenePartList.getSmPartList().get(i).getName());
                Bundle data = new Bundle();
                data.putString("device", mDevice);
                data.putSerializable("smartPart", sm);
                airConditionerFragment.setArguments(data);

            } else if (resGetScenePartList.getSmPartList().get(i).getType().equals("3")) { // 新风
                blowerFragment = new BlowerFragment();
                mSMFragments.add(blowerFragment);
                mFragmentTitle.add(resGetScenePartList.getSmPartList().get(i).getName());
                Bundle blowerData = new Bundle();
                blowerData.putString("device", mDevice);
                blowerData.putSerializable("smartPart", sm);
                blowerFragment.setArguments(blowerData);
            } else if (resGetScenePartList.getSmPartList().get(i).getType().equals("4")) { // 窗帘
                curtainsFragment = new CurtainsFragment();
                mSMFragments.add(curtainsFragment);// 窗帘
                mFragmentTitle.add(resGetScenePartList.getSmPartList().get(i).getName());
                Bundle data = new Bundle();
                data.putString("device", mDevice);
                data.putSerializable("smartPart", sm);
                curtainsFragment.setArguments(data);
            } else if (resGetScenePartList.getSmPartList().get(i).getType().equals("5")) {// 门锁
                lockFragment = new LockFragment();
                mLockList.add(lockFragment);
                mSMFragments.add(lockFragment); // 门锁
                mFragmentTitle.add(resGetScenePartList.getSmPartList().get(i).getName());
                Bundle data = new Bundle();
                data.putString("device", mDevice);
                data.putString("houseId", houseId);
                data.putBoolean("isOwner", isOwner);
                data.putSerializable("smartPart", sm);
                lockFragment.setArguments(data);
            } else if (resGetScenePartList.getSmPartList().get(i).getType().equals("6")) {// 雾化玻璃
                if (resGetScenePartList.getAGList().size() > 0 && mAGFragment == null) { // 所有的玻璃都只创建一个Fragment操作
                    mAGFragment = new AGFragment();
                    mSMFragments.add(mAGFragment); // 雾化玻璃
                    mFragmentTitle.add(ResourceUtil.getString(R.string.boli));
                    Bundle data = new Bundle();
                    data.putString("device", mDevice);
                    data.putSerializable("smartPart", sm);
                    data.putSerializable("data", (ArrayList) resGetScenePartList.getAGList());
                    mAGFragment.setArguments(data);
                }
            } else if (resGetScenePartList.getSmPartList().get(i).getType().equals("7")) {// 地暖
                floorHeatFragment = new FloorHeatFragment();
                mSMFragments.add(floorHeatFragment);
                mFragmentTitle.add(resGetScenePartList.getSmPartList().get(i).getName());
                Bundle data = new Bundle();
                data.putString("device", mDevice);
                data.putSerializable("smartPart", sm);
                floorHeatFragment.setArguments(data);
            }
        }


/*
        if (resGetScenePartList.getLightList().size() > 0) {   // 灯光
            lightFragment = new LightFragment();
            mSMFragments.add(lightFragment);
            Bundle bundle = new Bundle();
            bundle.putString("device", mDevice);
            bundle.putSerializable("data", (ArrayList) resGetScenePartList.getLightList());
            lightFragment.setArguments(bundle);
            iv_title_right.setVisibility(View.VISIBLE);
        }
        for (SmartPart sm : resGetScenePartList.getBlowerList()) { // 新风
            blowerFragment = new BlowerFragment();
            mSMFragments.add(blowerFragment);
            Bundle blowerData = new Bundle();
            blowerData.putString("device", mDevice);
            blowerData.putSerializable("smartPart", sm);
            blowerFragment.setArguments(blowerData);
        }

        for (SmartPart sm : resGetScenePartList.getAirConditionerList()) { // 空调
            airConditionerFragment = new AirConditionerFragment();
            mSMFragments.add(airConditionerFragment);
            Bundle data = new Bundle();
            data.putString("device", mDevice);
            data.putSerializable("smartPart", sm);
            airConditionerFragment.setArguments(data);

        }
        for (SmartPart sm : resGetScenePartList.getCurtainsList()) { // 窗帘
            curtainsFragment = new CurtainsFragment();
            mSMFragments.add(curtainsFragment);// 窗帘
            Bundle data = new Bundle();
            data.putString("device", mDevice);
            data.putSerializable("smartPart", sm);
            curtainsFragment.setArguments(data);
        }
        for (SmartPart sm : resGetScenePartList.getAGList()) {  // 雾化玻璃
            mAGFragment = new AGFragment();
            mSMFragments.add(mAGFragment); // 雾化玻璃
            Bundle data = new Bundle();
            data.putString("device", mDevice);
            data.putSerializable("smartPart", sm);
            mAGFragment.setArguments(data);
        }
        for (SmartPart sm : resGetScenePartList.getLockList()) {  // 门锁
                lockFragment = new LockFragment();
                mSMFragments.add(lockFragment); // 门锁
            Bundle data = new Bundle();
            data.putString("device", mDevice);
            data.putBoolean("isOwner", isOwner);
            data.putSerializable("smartPart", sm);
            lockFragment.setArguments(data);
        }
*/

        FragmentManager fm = getSupportFragmentManager();
        mSMFragmentPagerAdapter = new SmartPartFragmentPagerAdapter(fm, mSMFragments,mFragmentTitle);
        mVpSmartPart.setAdapter(mSMFragmentPagerAdapter);
        tabLayout.setupWithViewPager(mVpSmartPart);

        //  mVpSmartPart 设置完adapter之后，再判断一次显示设置图标
       /* if (mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof LightFragment) { // 灯光
            iv_title_right.setImageResource(R.drawable.home_control_navbar_setting);
            iv_title_right.setVisibility(View.VISIBLE);
        } else
            */
            if ( mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof AGFragment) { // 雾化玻璃
            iv_title_right.setImageResource(R.drawable.home_control_navbar_setting);
            iv_title_right.setVisibility(View.VISIBLE);
        }else if (isOwner && mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof LockFragment) {
            iv_title_right.setImageResource(R.drawable.ic_lock_power_setting);
            iv_title_right.setVisibility(View.VISIBLE);
        } else {
            iv_title_right.setVisibility(View.GONE);
        }

    }


    @Override
    public void getScenePartListFail(String msg) {
        LogUtil.i("===获取房间设备失败==" + msg);
    }


    @Override
    public void onItemClick(View view, int position) {


    }


    /****
     * 显示通知栏信息
     */
    @Override
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.MESSAGE_NOTIFY:
                LogUtil.i("收到消息");
                MessageNotify data = (MessageNotify) msg.getData();
//                mPresenter.getAtHomeUserList();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notify = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_no_head)
                        .setContentTitle(data.getTitle())
                        .setContentText(data.getTitle()).getNotification();
                //      .setContentIntent(pendingIntent).setNumber(1).getNotification();
                notify.flags = Notification.FLAG_AUTO_CANCEL; // FL
                notify.defaults = Notification.DEFAULT_ALL;
                manager.notify(1, notify);
                break;
            case Msg.AT_HOME_STATE_CHANGE:
                //在家人数变化
//                mPresenter.getAtHomeUserList();
                break;

        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                if (tv_title_left.getText().toString().trim().equals("取消")) {
                    if (mAGFragment!=null) {
                    mAGFragment.showAgEdit(false);// 取消玻璃编辑
                        iv_title_right.setVisibility(View.VISIBLE);
                    }
                    tv_title_left.setText("返回");
                    Drawable drawable = getResources().getDrawable(R.drawable.center_back);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_title_left.setCompoundDrawables(drawable, null, null, null);
//                    tv_title_left.setCompoundDrawablePadding(20);
                } else {
                    finish();
                }
                break;
            case R.id.iv_title_right:
                // 灯光设置
              /*  if (mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof LightFragment) {
                    tv_title_left.setText("取消");
                    tv_title_left.setCompoundDrawables(null, null, null, null);
                    lightFragment.showLightEdit(true);// 显示灯光编辑
                } else
                    */
                    // 门锁设置
                    if (mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof LockFragment) {

                  /*  MyDialogUtil dialogUtil02 = new MyDialogUtil(this) {
                        @Override
                        public void confirm() {
                            if (mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof LockFragment) {
                                int currentItem = mVpSmartPart.getCurrentItem();

                                Intent intent1 = new Intent(RoomActivity.this, LockPowerListActivity.class);
                                intent1.putExtra("lockId", mLockList.get(currentItem).getLockId()); // 锁id
                                startActivity(intent1);
                            }
                        }
                    };
                    dialogUtil02.showLockAlertTipDialog();*/

                        int currentItem = mVpSmartPart.getCurrentItem();

                        Intent intent1 = new Intent(RoomActivity.this, LockPowerListActivity.class);
                        intent1.putExtra("lockId", mLockList.get(currentItem).getLockId()); // 锁id
                        intent1.putExtra("lockStatue", mLockList.get(currentItem).getLockStatue()); // 锁id
                        intent1.putExtra("gatewayStatue", mLockList.get(currentItem).getGatewayStatue()); // 锁id
                        intent1.putExtra("gatewaySn", mLockList.get(currentItem).getGatewaySn()); // 网关sn
                        intent1.putExtra("lockName", mLockList.get(currentItem).getlockName()); // 锁名
                        startActivity(intent1);

                } else if (mSMFragmentPagerAdapter.instantiateItem(mVpSmartPart, mVpSmartPart.getCurrentItem()) instanceof AGFragment){
                    // 雾化玻璃编辑
                    tv_title_left.setText("取消");
                    tv_title_left.setCompoundDrawables(null, null, null, null);
                    mAGFragment.showAgEdit(true);// 显示灯光编辑
                        iv_title_right.setVisibility(View.GONE);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int type = data.getIntExtra("type", 0);
            if (type == 1) {
                //恢复默认场景图片
//                mPresenter.userScencePicDelete();
                return;
            }
            String patch = data.getStringExtra("patch");
            if (TextUtils.isEmpty(patch)) {
                return;
            }
//            mPresenter.userScencePicModify();
        }
    }

    public ViewPager getViewPager() {
        return mVpSmartPart;
    }


    @Override
    public void onBackPressed() {

       /* if (lightFragment != null && lightFragment.getEditType()) { // ture 灯光编辑状态中
            lightFragment.showLightEdit(false);// 取消灯光编辑
            tv_title_left.setText("返回");
            Drawable drawable = getResources().getDrawable(R.drawable.center_back);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_title_left.setCompoundDrawables(drawable, null, null, null);
        } else*/

            if(mAGFragment != null && mAGFragment.getEditType()){      // ture 灯光编辑状态中
            mAGFragment.showAgEdit(false);// 取消玻璃编辑
            tv_title_left.setText("返回");
            Drawable drawable = getResources().getDrawable(R.drawable.center_back);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_title_left.setCompoundDrawables(drawable, null, null, null);
        } else {     // 不在编辑状态中，可退出app
            finish();
        }
    }

    @Override
    public void cleanEdit(boolean clean) { // 在雾化玻璃Fragment里面调用接口取消编辑的时候触发
        if (clean) {
            tv_title_left.setText("返回");
            Drawable drawable = getResources().getDrawable(R.drawable.center_back);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_title_left.setCompoundDrawables(drawable, null, null, null);
        }
    }

}
