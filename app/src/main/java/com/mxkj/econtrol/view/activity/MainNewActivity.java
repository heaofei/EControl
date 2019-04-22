package com.mxkj.econtrol.view.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseFragmentPagerAdapter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseTCPCMDRequest;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResRoomListBean;
import com.mxkj.econtrol.bean.response.Room;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.contract.MainNewContract;
import com.mxkj.econtrol.model.MainNewModel;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.MainNewPresenter;
import com.mxkj.econtrol.ui.adapter.RoomAdapter;
import com.mxkj.econtrol.utils.AppUpdateHelper;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.LoginUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.StatusBarUtils;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.view.fragment.FragmentInformation;
import com.mxkj.econtrol.view.fragment.FragmentMain;
import com.mxkj.econtrol.view.fragment.FragmentMy;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;


/**
 * 新APP主页
 */
public class MainNewActivity extends BaseActivity implements View.OnClickListener, MainNewContract.View {

    private com.mxkj.econtrol.widget.MyViewPager viewpager;
    private LinearLayout ll_bottom;
    private RelativeLayout rl_home;
    private ImageView iv_home;
    private RelativeLayout rl_information;
    private ImageView iv_informatio;
    private RelativeLayout rl_center;
    private ImageView iv_center;
    private RelativeLayout rl_my;
    private ImageView iv_my;
    private TextView tv_home, tv_informatio, tv_center, tv_my;
    private TextView tv_count;// 个人中心未读消息数量
    // 房间列表
    private List<Room> roomlist = new ArrayList<Room>();
    private RoomAdapter roomAdapter;
    private int mChoiceRoomPosition = 0; // 选中房间的位置

    private FragmentMain fragmentMain;
    private FragmentInformation fragmentInformation;
    private FragmentMy fragmentMy;
    private List<Fragment> fragmentList;
    private BaseFragmentPagerAdapter baseFragmentPagerAdapter;

    private DrawerLayout drawerLayout;
    private CoordinatorLayout right;
    private RelativeLayout left;
    private TextView tv_house_name; // 侧栏显示的房间名称
    private String editRoomName = ""; // 编辑房间的名字
    private int mPosition = 0; // 编辑房间的位置
    private RecyclerView recycleview_room;
    private TextView tv_menu_edit;
    private TextView tv_menu_finish;
    private boolean isEditRoom = false;// 房间编辑状态
    private boolean isDrawer = false;// 侧栏状态
    private MainNewContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_new);
        super.onCreate(savedInstanceState);

        StatusBarUtils.with(this)  // 状态栏，设置颜色
                .setDrawerLayoutContentId(true, R.id.rl_content)
//                .setColor(getResources().getColor(R.color.text_orangereg02))
                .setDrawable(getResources().getDrawable(R.drawable.gradient_background))
                .init();

    }

    @Override
    public void setStatusBar(boolean isShow, int drawable) {
        super.setStatusBar(false, drawable);
    }

    @Override
    protected void initView() {
        viewpager = (com.mxkj.econtrol.widget.MyViewPager) findViewById(R.id.viewpager);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        rl_home = (RelativeLayout) findViewById(R.id.rl_home);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        rl_center = (RelativeLayout) findViewById(R.id.rl_center);
        iv_center = (ImageView) findViewById(R.id.iv_center);
        rl_my = (RelativeLayout) findViewById(R.id.rl_my);
        iv_my = (ImageView) findViewById(R.id.iv_my);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_my = (TextView) findViewById(R.id.tv_my);
        tv_count = (TextView) findViewById(R.id.tv_count);
        rl_information = (RelativeLayout) findViewById(R.id.rl_information);
        iv_informatio = (ImageView) findViewById(R.id.iv_informatio);
        tv_informatio = (TextView) findViewById(R.id.tv_informatio);
        tv_menu_edit = (TextView) findViewById(R.id.tv_menu_edit);
        tv_menu_finish = (TextView) findViewById(R.id.tv_menu_finish);


        fragmentList = new ArrayList<>();
        fragmentMain = new FragmentMain();
//        fragmentStatistics = new FragmentStatistics();
        fragmentInformation = new FragmentInformation();
        fragmentMy = new FragmentMy();
        fragmentList.add(fragmentMain);
        fragmentList.add(fragmentInformation);
//        fragmentList.add(fragmentStatistics);
        fragmentList.add(fragmentMy);

   /*     fragmentList.add(new FragmentMain());
        fragmentList.add(new FragmentMy());*/

        baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewpager.setAdapter(baseFragmentPagerAdapter);
        viewpager.setNoScroll(true);
        viewpager.setOffscreenPageLimit(3);
        if (APP.isExperience || APP.isLogin) {
            viewpager.setCurrentItem(0);
            iv_home.setImageResource(R.drawable.tabbar_icon_home_selected);
            iv_my.setImageResource(R.drawable.tabbar_icon_center_default);
            iv_informatio.setImageResource(R.drawable.ic_information_main_default);
            tv_home.setTextColor(getResources().getColor(R.color.text_orangereg02));
            tv_informatio.setTextColor(getResources().getColor(R.color.text_black_666));
            tv_my.setTextColor(getResources().getColor(R.color.text_black_666));
        } else {
            viewpager.setCurrentItem(1);
            iv_home.setImageResource(R.drawable.tabbar_icon_home_default);
            iv_my.setImageResource(R.drawable.tabbar_icon_center_default);
            iv_informatio.setImageResource(R.drawable.ic_information_main_selected);
            tv_home.setTextColor(getResources().getColor(R.color.text_black_666));
            tv_informatio.setTextColor(getResources().getColor(R.color.text_orangereg02));
            tv_my.setTextColor(getResources().getColor(R.color.text_black_666));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);// 设置禁止滑动弹出
        right = (CoordinatorLayout) findViewById(R.id.right);
        left = (RelativeLayout) findViewById(R.id.ll_left);

        tv_house_name = (TextView) findViewById(R.id.tv_house_name);
        recycleview_room = (RecyclerView) findViewById(R.id.recycleview_room);
        recycleview_room.setLayoutManager(new LinearLayoutManager(this));
        tv_house_name.setOnClickListener(this);

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isDrawer) {
                    return left.dispatchTouchEvent(motionEvent);
                } else {
                    return false;
                }
            }
        });
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer = true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                right.layout(left.getRight(), 0, left.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });


    }

    @Override
    protected void initData() {

        //连接成功后,如果已登录了就立刻注册tcp
        if (APP.user != null) {
            BaseTCPCMDRequest regist = new BaseTCPCMDRequest();
            regist.setAction("REGIST");
            TcpClient.getInstacne().sendCMD(regist);
        }
        mPresenter = new MainNewPresenter(this, new MainNewModel());

        //只有当系统版本大于等于6.0时才需要
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断该应用是否有相机权限，如果没有再去申请
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, 123);
                // 切记在Fragment里面 不要使用 ActivityCompat.requestPermissions 不然就不能再fragment里面回调onRequestPermissionsResult方法
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            } else {
                checkUpdate();
            }
        } else {
            checkUpdate();
        }

        //设置侧边栏房间
        roomAdapter = new RoomAdapter(roomlist, R.layout.layout_room_item);
        roomAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isEditRoom) {  // 编辑状态
                    if (roomlist.get(position).getId().equals("常用")) {
                        return; // 常用不做修改名字
                    } else {
                        mPosition = position;
                        showEditRoomDialog(roomlist.get(position).getId(), roomlist.get(position).getName());
                    }
                } else {  // 选择房间状态
                    for (int i = 0; i < roomlist.size(); i++) {
                        roomlist.get(i).setClick(false);
                    }
                    mChoiceRoomPosition = position;
                    roomlist.get(position).setClick(true);
                    roomAdapter.upDateAdapter(roomlist, isEditRoom);
                    drawerLayout.closeDrawer(GravityCompat.START); // 把侧栏收起
                    if (fragmentMain != null) {
                        if (roomlist.get(position).getId().equals("常用")) {
                            fragmentMain.changeDeviceList(true, position, roomlist.get(position).getName(), ""); //
                        } else {
                            fragmentMain.changeDeviceList(false, position, roomlist.get(position).getName(), roomlist.get(position).getCode()); //
                        }
                    }
                }
            }
        });
        recycleview_room.setAdapter(roomAdapter);
    }


    /***
     * 编辑房间名
     ***/
    private void showEditRoomDialog(final String smartPartId, String smartPartName) {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
            }
        };
        dialog.setInputDialogClicklistener(new MyDialogUtil.showInputDialogOnClickInterface() {
            @Override
            public void doEdit(String content) {
                editRoomName = content;
                if (APP.isLogin) {
                    mPresenter.sceneEdit(smartPartId, content); // 修改设备名字
                }
                if (APP.isExperience) { // 体验，修改本地
                    roomlist.get(mPosition).setName(editRoomName);
                    roomAdapter.upDateAdapter(roomlist, true);
                    if (roomlist.get(mChoiceRoomPosition).isClick()) {
                        if (fragmentMain != null) {//
                            fragmentMain.changeRoomName(editRoomName); //
                        }
                    }
                }
            }
        });
        dialog.showInputDialog("编辑房间", "请修改新的房间名称", smartPartName);
    }

    private void checkUpdate() {
        boolean hasPermission;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            hasPermission = false;
        } else {
            hasPermission = true;
        }
        if (APP.appVersion != null) {
            if (AppUpdateHelper.checkAppVersion(this, APP.appVersion, false, hasPermission)) {   // APP 更新Dialog显示判断
                return;
            }
        }

    }

    @Override
    protected void initListener() {
        rl_home.setOnClickListener(this);
        rl_information.setOnClickListener(this);
        rl_center.setOnClickListener(this);
        rl_my.setOnClickListener(this);
        tv_menu_edit.setOnClickListener(this);
        tv_menu_finish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_home:
                if (LoginUtil.isExperience(this) || LoginUtil.isLogin(this)) {  // 体验或登录状态，都可以进去主页
                    viewpager.setCurrentItem(0);
                    iv_home.setImageResource(R.drawable.tabbar_icon_home_selected);
                    iv_my.setImageResource(R.drawable.tabbar_icon_center_default);
//                iv_center.setImageResource(R.drawable.tabbar_icon_default);
                    iv_informatio.setImageResource(R.drawable.ic_information_main_default);

                    tv_home.setTextColor(getResources().getColor(R.color.text_orangereg02));
                    tv_informatio.setTextColor(getResources().getColor(R.color.text_black_666));
                    tv_my.setTextColor(getResources().getColor(R.color.text_black_666));
                    setImageAnimator(iv_home);
                }
                break;
            case R.id.rl_information:
                viewpager.setCurrentItem(1);
                iv_home.setImageResource(R.drawable.tabbar_icon_home_default);
                iv_my.setImageResource(R.drawable.tabbar_icon_center_default);
                iv_informatio.setImageResource(R.drawable.ic_information_main_selected);

                tv_home.setTextColor(getResources().getColor(R.color.text_black_666));
                tv_informatio.setTextColor(getResources().getColor(R.color.text_orangereg02));
                tv_my.setTextColor(getResources().getColor(R.color.text_black_666));
                setImageAnimator(iv_informatio);
                break;
           /* case R.id.rl_center:
                viewpager.setCurrentItem(2);
                iv_home.setImageResource(R.drawable.tabbar_icon_home_default);
                iv_center.setImageResource(R.drawable.tabbar_icon_selected);
                iv_my.setImageResource(R.drawable.tabbar_icon_center_default);
                tv_home.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
                tv_informatio.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
                tv_center.setTextColor(getResources().getColor(R.color.text_orangereg02));
                tv_my.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
                setImageAnimator(iv_center);
                break;*/
            case R.id.rl_my:
                if (LoginUtil.isExperience(this) || LoginUtil.isLogin(this)) {  // 体验或登录状态，都可以进去个人中心
                    viewpager.setCurrentItem(2);
//                iv_center.setImageResource(R.drawable.tabbar_icon_default);
//                tv_center.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
                    iv_home.setImageResource(R.drawable.tabbar_icon_home_default);
                    iv_informatio.setImageResource(R.drawable.ic_information_main_default);
                    iv_my.setImageResource(R.drawable.tabbar_icon_center_selected);
                    tv_home.setTextColor(getResources().getColor(R.color.text_black_666));
                    tv_informatio.setTextColor(getResources().getColor(R.color.text_black_666));
                    tv_my.setTextColor(getResources().getColor(R.color.text_orangereg02));
                    setImageAnimator(iv_my);
                    if (fragmentMy != null) {// 获取刷新房子成员审核数据
                        fragmentMy.getApplyBindHouseList();//获取绑定房子审核消息
                    }
                }
                break;
            case R.id.tv_house_name: // 关闭侧栏
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.tv_menu_edit: // 侧栏编辑房间名
                tv_menu_edit.setVisibility(View.GONE);
                tv_menu_finish.setVisibility(View.VISIBLE);
                isEditRoom = true;
                roomAdapter.upDateAdapter(roomlist, isEditRoom);
                break;
            case R.id.tv_menu_finish: // 完成编辑
                tv_menu_finish.setVisibility(View.GONE);
                tv_menu_edit.setVisibility(View.VISIBLE);
                isEditRoom = false;
                roomAdapter.upDateAdapter(roomlist, isEditRoom);
                break;
        }
    }


    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) { // 这个是视频播放的时候，取消全屏
            return;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            //System.exit(0);
        }
    }

    /**
     * 处理eventBus消息
     *
     * @param msg
     */
    @Override
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        if (msg.getMsgType() == Msg.FRAGMENT_MY_MESSAGE_TOTAL) { // 修改了用户信息
            String totalCount = (String) msg.getData();
            if (!TextUtils.isEmpty(totalCount) && !totalCount.equals("0")) {
                tv_count.setVisibility(View.VISIBLE);
//                tv_count.setText("" + totalCount);
            } else {
                tv_count.setVisibility(View.GONE);
            }
        } else if (msg.getMsgType() == Msg.EVENBUS_OPEN_CHELAN_MESSAGE) { // 收到来自fragment的消息，打开侧栏
            drawerLayout.openDrawer(left);
        }
//        else if (msg.getMsgType() == Msg.EVENBUS_UPDE__DATELIST) { // 收到来自主页消息，更新常用列表数据
//
//            List<SmartPart> partTotal = (List<SmartPart>) msg.getData();
//            if (partTotal != null && partTotal.size() > 0) {
//                Room room = new Room();
//                room.setName("常用");
//                room.setId("常用");
//                room.setClick(true);
//                roomlist.clear();
//                roomlist.add(0, room);
//            } else {
//                roomlist.clear();
//            }
//        }
        else if (msg.getMsgType() == Msg.SWITCH_HOUSE) { // 收到来自主页消息，更新房间列表

            ResRoomListBean resRoomListBean = (ResRoomListBean) msg.getData();
            List<Room> smInfoBean = resRoomListBean.getSmInfoBean();

            Room room = new Room();
            room.setName("常用");
            room.setId("常用");
            room.setClick(true);
            roomlist.clear();
            roomlist.add(0, room);// 默认常用这栏默认显示

            if (smInfoBean != null && smInfoBean.size() > 0) {
                if (smInfoBean.get(0).getId().equals("体验常用")) {
                } else {
                }
                roomlist.addAll(smInfoBean);
            }
            if (fragmentMain != null) {// 防止没有数据时还加载旧数据
                if (resRoomListBean.isScenePartTotalisNull()) { // 常用列表数据是否为空
                    if (roomlist.size() > 1) {
                        fragmentMain.changeDeviceList(false, 1, roomlist.get(1).getName(), roomlist.get(1).getCode()); //
                        roomlist.get(0).setClick(false);
                        roomlist.get(1).setClick(true);
                    }
                } else {
                    fragmentMain.changeDeviceList(true, 0, roomlist.get(0).getName(), ""); ////默认加载常用列表数据
                }
            }
            roomAdapter.upDateAdapter(roomlist, isEditRoom);
        } else if (msg.getMsgType() == Msg.EVENBUS_UPDE__HOUSE_NAME) { // 收到来自主页消息，更新房间信息
            ResGetRoomList.House house = (ResGetRoomList.House) msg.getData();
            tv_house_name.setText(TextUtil.setTest(house.getEstate() + "-" + house.getBuiding() + "-" + house.getHouseNo()));// 显示选择的房子名称
        }
    }

    @Override
    public void HandleUIMessage(EventBusUIMessage msg) {
        super.HandleUIMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.showtcp:
                String data = (String) msg.getData();
                showToast(data);
                Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    // 调用requestPermissions会弹出对话框，用户做出选择之后的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length >= 1) {
                //因为我们只申请了一个权限，所以这个数组只有一个
                int writeResult = grantResults[0];
                //判断是否授权，也就是用户点击的是拒绝还是接受
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;
                if (writeGranted) {
                    //用户点击了接受，可以进行写入内存卡处理
                    checkUpdate();
                } else {
                    //用户点击了拒绝，可以进行相应处理
//                    Toast.makeText(this,"请到，设置-应用管理-权限设置中，开启存储权限",Toast.LENGTH_LONG).show();
                    checkUpdate();
                }
            }
        }
    }

    private AnimatorSet mAnimatorSet;

    public void setImageAnimator(View view) {// 主页四个菜单的动画
        if (mAnimatorSet == null) {
        }
        mAnimatorSet = generateScaleAnim(view, 1f, 1.1f, 0.8f, 1f);
        mAnimatorSet.start();
        view.clearAnimation();
    }

    /**
     * 生成一个缩放动画 X轴和Y轴
     *
     * @param view       需要播放动画的View
     * @param scaleValue 缩放轨迹
     * @return AnimatorSet 动画对象
     */
    public static AnimatorSet generateScaleAnim(View view, float... scaleValue) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.SCALE_X, scaleValue);
        animatorX.setDuration(500);

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, View.SCALE_Y, scaleValue);
        animatorY.setDuration(500);

        List<Animator> animatorList = new ArrayList<>(2);
        animatorList.add(animatorX);
        animatorList.add(animatorY);
        animatorSet.playTogether(animatorList);
        return animatorSet;
    }


    @Override
    public void setPresenter(MainNewContract.Presenter presenter) {
    }

    @Override
    public void sceneEditSuccess(BaseResponse baseResponse) {
        roomlist.get(mPosition).setName(editRoomName);
        roomAdapter.upDateAdapter(roomlist, true);

        if (roomlist.get(mChoiceRoomPosition).isClick()) {
            if (fragmentMain != null) {//
                fragmentMain.changeRoomName(editRoomName); //
            }
        }

    }

    @Override
    public void sceneEditFali(String msg) {
    }

}
