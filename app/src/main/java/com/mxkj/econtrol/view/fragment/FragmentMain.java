package com.mxkj.econtrol.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.MyHouse;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTotal;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;
import com.mxkj.econtrol.bean.response.ResRoomListBean;
import com.mxkj.econtrol.bean.response.Room;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.TcpResHouseRespones;
import com.mxkj.econtrol.bean.response.TcpResPatternRespones;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.RegistCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.contract.FragmentMainContract;
import com.mxkj.econtrol.model.FragmentMainModel;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.FragmentMainPresenter;
import com.mxkj.econtrol.ui.adapter.DeviceListAdapter;
import com.mxkj.econtrol.ui.adapter.FragmentMainHouseListAdapter;
import com.mxkj.econtrol.ui.adapter.PatternListAdapter;
import com.mxkj.econtrol.utils.DeviceOperationUtil;
import com.mxkj.econtrol.utils.LocationUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.LoginUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.view.activity.DeviceFragmentActivity;
import com.mxkj.econtrol.view.activity.DeviceTimingActivity;
import com.mxkj.econtrol.view.activity.LightSettingActivity;
import com.mxkj.econtrol.view.activity.NewHouseActivity;
import com.mxkj.econtrol.view.activity.SceneEditActivity;
import com.mxkj.econtrol.view.activity.SceneNewActivity;
import com.mxkj.econtrol.widget.MyLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/7/26.
 */

public class FragmentMain extends BaseMainFragment implements View.OnClickListener, FragmentMainContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;
    private LinearLayout ll_more;

    private RecyclerView recycleview_scene;
    private RecyclerView recycleview_device;
    private String roomName = ""; // 默认房间名
    private LinearLayout ll_title;
    private MyLinearLayout ll_status;
    private Context context;

    private FragmentMainContract.Presenter mPresenter;

    //房子列表
    private List<MyHouse> houselist = new ArrayList<MyHouse>();
    private FragmentMainHouseListAdapter houseAdapter;

    // 场景列表
    private List<ResGetPatternList.ListBeaner> sceneTotalList = new ArrayList<ResGetPatternList.ListBeaner>(); // 当前房子的所有场景列表
    private List<ResGetPatternList.ListBeaner> scenelist = new ArrayList<ResGetPatternList.ListBeaner>();      // 某个区域的场景列表集合
    private PatternListAdapter patternListAdapter;
    private String sceneTypeCode = "";
    // 设备列表
    private List<SmartPart> deviceList = new ArrayList<SmartPart>();
    private DeviceListAdapter deviceListAdapter;
    // 常用设备列表
    private List<SmartPart> partTotalList = new ArrayList<>(); // 常用设备集合
    private List<List<SmartPart>> roomPart = new ArrayList<>(); // 全部房间设备集合
    private List<ResGetRoomList.SmInfoBeanBean.RoomBean> room = new ArrayList<>(); // 全部房间集合
    private String roomId; // 房间Id
    private ResGetRoomList resGetRoomList; // 房间，设备总数据实体类
    private ResGetPatternList resGetPatternList; // 场景模式列表总数据实体类

    private boolean isChangyong = false; // 是否常用的数据列表
    private boolean isEditDevice = false; // 编辑部件状态
    private int mEditDevicePosition = 0; // 编辑部件的位置
    private int mChoicePosition = 0; // 侧栏房间的位置
    private String editDeviceName = ""; // 编辑部件的名字
    private boolean isOwner = false; // 是否是业主

    //控件是否已经初始化
    private boolean isCreateView = false;
    //数据是否已被加载过一次
    private boolean mHasLoadOnce = false;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            isCreateView = true;
            initView(rootView);
            initData();
            initListener();
        }
        return rootView;


    }

    public void initView(View view) {
        context = getActivity();
        tv_title_left = (TextView) view.findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) view.findViewById(R.id.tv_title_content);
        iv_more = (ImageView) view.findViewById(R.id.iv_more);
        tv_title_right = (TextView) view.findViewById(R.id.tv_title_right);
        tv_title_right.setText("设置");
        ll_more = (LinearLayout) view.findViewById(R.id.ll_more);
        recycleview_scene = (RecyclerView) view.findViewById(R.id.recycleview_scene);
        ll_title = (LinearLayout) view.findViewById(R.id.ll_title);
        ll_status = (MyLinearLayout) view.findViewById(R.id.ll_status);
        recycleview_device = (RecyclerView) view.findViewById(R.id.recycleview_device);
        tv_title_content.setText(roomName);
    }

    @Override
    public void onStart() {
        super.onStart();
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isCreateView || mHasLoadOnce) {
            return;
        }
        try {
            if (!APP.isLogin) {
                getExperienceList();
            } else {
                if (null == APP.user.getDefaultHouseId() || TextUtils.isEmpty(APP.user.getDefaultHouseId())) {
//                    showEmpty();
                    mPresenter.getHoursList();
                } else {
                    refreshHouser(); // 刷新主页房子数据
                    SharedPreferencesUtils.setParam(getActivity(), "houseId", APP.user.getDefaultHouseId());//  选中房子id
//                    mHasLoadOnce = true;
                }
            }
            mHasLoadOnce = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initView() {

    }

    public void initData() {
        mPresenter = new FragmentMainPresenter(this, new FragmentMainModel());
        setHouseListAdapter();
        setSenceListAdapter();
        setDeviceAdapter();
    }

    /***设备列表Adapter**/
    private void setDeviceAdapter() {
        recycleview_device.setLayoutManager(new LinearLayoutManager(getActivity()));
        deviceListAdapter = new DeviceListAdapter(deviceList, R.layout.layout_home_device_item, this);
        recycleview_device.setAdapter(deviceListAdapter);
    }

    /***
     * 设置场景Adapter
     **/
    private void setSenceListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleview_scene.setLayoutManager(linearLayoutManager);
        patternListAdapter = new PatternListAdapter(sceneTotalList, R.layout.layout_sences_item);
        recycleview_scene.setAdapter(patternListAdapter);

        patternListAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (APP.isExperience) {  // 体验模式下，虚拟操作
                    for (int i = 0; i < scenelist.size(); i++) {
                        scenelist.get(i).setStatus("0"); // 模式状态：status 0 未应用，1-已应用
                    }
                    scenelist.get(position).setStatus("1");
                    patternListAdapter.upDateAdapter(scenelist); // 改变模式视图
                    changeDeviceStatus(position, "01");                // 改变部件视图

                } else {                // 登陆状态下，真实操作
                    // 打开或关闭
                   /* if (sceneTotalList.get(position).getType().equals("0") && sceneTotalList.get(position).getStatus().equals("0")) { // 打开全关模式
                        showAllCloseDialog(position);
                    } else {
                        if (sceneTotalList.get(position).getStatus().equals("0")) { // 只有在关的状态下，才能点开
                            // 开关逻辑，在后台处理， 只要把模式的id传过去
                            mPresenter.patternActive(sceneTotalList.get(position).getId());
                        }
                    }*/
                      /*  mPresenter.patternActive(scenelist.get(position).getId());
                    sendCMD(scenelist.get(position).getTypeCode());*/
                    if (scenelist.get(position).getType().equals("1")) { // 回家模式
                        sendCMD(scenelist.get(position).getNum(), "01");
                    } else if (scenelist.get(position).getType().equals("0")) { // 离家模式
                        showAllCloseDialog(scenelist.get(position).getNum(), "00");
                    } else {
                        sendCMD(scenelist.get(position).getNum(), "01");
                    }
                }
            }
        });
    }

    private void sendCMD(String typeCode, String comA) {

        /*if (typeCode != null && typeCode.length() > 3) {
            typeCode = typeCode.substring(typeCode.length() - 3);
        }*/

        final SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setN("MD" + typeCode);
        command.setA(comA);
        smartPartCMD.setCommand(command);

        if (APP.isExperience) {  // 体验账号下， 不做实际指令发送
            return;
        }
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }


    /****
     * 房子列表Adapter
     **/
    private void setHouseListAdapter() {
        houseAdapter = new FragmentMainHouseListAdapter(houselist, R.layout.adapter_mian_house_item);
        houseAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setAninmation(2);// 结束
                if (popupWindow != null && popupWindow.isShowing()) { // popwindow消失
                    popupWindow.dismiss();
                }
                sceneTypeCode = null;
                roomName = houselist.get(position).getHousingEstate() + "-" + houselist.get(position).getBuilding() + "-" + houselist.get(position).getHouseNo();
                tv_title_content.setText(roomName);// 显示选择的房子
                SharedPreferencesUtils.setParam(getActivity(), "houseId", houselist.get(position).getId());//  选中房子id
                refreshHouser(); // 刷新主页房子数据

                APP.user.setDefaultEstateName(houselist.get(position).getHousingEstate());
                APP.user.setDefaultBuidingName(houselist.get(position).getBuilding());
                APP.user.setDefaultHouseName(houselist.get(position).getHouseNo());
                if (houselist.get(position).getBindType().equals("0")) {//绑定类型，0为管理员，1为普通用户
                    APP.user.setOwner(true);
                } else {
                    APP.user.setOwner(false);
                }

                APP.user.setDefaultHouseName(houselist.get(position).getHouseNo());

//                EventBus.getDefault().post(new EventBusUIMessage(Msg.SWITCH_HOUSE)); // 刷新使用习惯数据
            }
        });
    }


    public void initListener() {
        ll_more.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
    }

    private int mPosition; //操作的位置

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_right:
                if (isEditDevice) {
                    isEditDevice = false;
                    partTotalList.clear();// 清除常用数据
                    mPresenter.scenePartTotal();  // 获取常用设备列表
                    mPresenter.getRoomList();  // 重新获取房间、部件列表
                    tv_title_right.setText("设置");
                } else {
                    showSettingPopWindow();
                }
                break;
            case R.id.tv_title_left:

                EventBusUIMessage message = new EventBusUIMessage();
                message.setMsgType(Msg.EVENBUS_OPEN_CHELAN_MESSAGE);
                EventBus.getDefault().post(message); // 发送evenbus

               /* tv_title_left.setText("");
                if (isEditRoom) {
                    roomAdapter.upDateAdapter(roomlist, false);
                    isEditRoom = false;
                    tv_room_more.setVisibility(View.VISIBLE);
                    tv_scene_more.setVisibility(View.VISIBLE);
                }
                if (EditSceneType != PatternListAdapter.ISDEFAULF) {
                    patternListAdapter.upDateAdapter(scenelist, PatternListAdapter.ISDEFAULF); // 默认模式
                    EditSceneType = PatternListAdapter.ISDEFAULF;
                    tv_scene_more.setVisibility(View.VISIBLE);
                    tv_room_more.setVisibility(View.VISIBLE);
                }*/
                break;
            case R.id.ll_more:
                if (APP.isExperience) return;
                setAninmation(1); // 开始
                showPopWindow();
                break;
            /*** 设备操作部分 ************** STAR *************************************************/

            case R.id.rl_item:
                if (isEditDevice) {  // 编辑部件
                    SmartPart smartPart = (SmartPart) v.getTag();
                    mEditDevicePosition = smartPart.getPosition();
                    showEditDeviceDialog(smartPart);
                }
                break;
            case R.id.iv_open:  // 打开设备
                SmartPart smartPart1 = (SmartPart) v.getTag();
                if (smartPart1.getType().equals("4")) {//4-窗帘
                    if (deviceList.get(smartPart1.getPosition()).getTypeName().equals("opening")) {
                        deviceList.get(smartPart1.getPosition()).setTypeName("stop");
                        if (!APP.isExperience) {
                            DeviceOperationUtil.openCU((BaseActivity) getActivity(), smartPart1, "stop");
                        }
                    } else {
                        if (!APP.isExperience) {
                            DeviceOperationUtil.openCU((BaseActivity) getActivity(), smartPart1, "opening");
                        }
                        deviceList.get(smartPart1.getPosition()).setTypeName("opening");
                    }
                    deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
                } else { // 非窗帘
                    if (APP.isExperience) {
                        DeviceOperationUtil.openExperienceDevice((BaseActivity) getActivity(), smartPart1);
                    } else {
                        DeviceOperationUtil.openDevice((BaseActivity) getActivity(), smartPart1);
                        //----后面改成本地更新--star----------------------------------------------------------------------------------------------------------
                        if (smartPart1.getType().equals("1") && smartPart1.getFunctions().getD() == 1) { // 调光的灯   并且 支持调光模块
                            if (smartPart1.getState().getA().equals("01") || !smartPart1.getState().getD().equals("00")) { // 不是有亮度的情况下，
                                deviceList.get(smartPart1.getPosition()).getState().setD("00");
                            } else {
                                if (!TextUtils.isEmpty(smartPart1.getState().getD()) && !smartPart1.getState().getD().equals("00")) {
                                    deviceList.get(smartPart1.getPosition()).getState().setD("00");
                                } else {
                                    deviceList.get(smartPart1.getPosition()).getState().setD("09");
                                }
                            }
                        } else {
                            if (smartPart1.getState().getA().equals("00")) {
                                deviceList.get(smartPart1.getPosition()).getState().setA("01");
                            } else {
                                deviceList.get(smartPart1.getPosition()).getState().setA("00");
                            }
                        }
                        deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
                        //-----后面改成本地更新--end-----------------------------------------------------------------------------------------------------------
                    }
                }
                break;
            case R.id.iv_close:  // 关闭设备
                SmartPart smartPart2 = (SmartPart) v.getTag();
                if (smartPart2.getType().equals("4")) { //4-窗帘
                    if (deviceList.get(smartPart2.getPosition()).getTypeName().equals("closeding")) {
                        if (!APP.isExperience) {
                            DeviceOperationUtil.openCU((BaseActivity) getActivity(), smartPart2, "stop");
                        }
                        deviceList.get(smartPart2.getPosition()).setTypeName("stop");
                    } else {
                        if (!APP.isExperience) {
                            DeviceOperationUtil.openCU((BaseActivity) getActivity(), smartPart2, "closeding");
                        }
                        deviceList.get(smartPart2.getPosition()).setTypeName("closeding");
                    }
                    deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
                }

                break;
            case R.id.iv_settting:
                if (isEditDevice) {  // 编辑部件
                    SmartPart smartPart = (SmartPart) v.getTag();
                    mEditDevicePosition = smartPart.getPosition();
                    showEditDeviceDialog(smartPart);
                } else {
                    SmartPart smartPart3 = (SmartPart) v.getTag();
                    if (smartPart3.getType().equals("1")) { // 灯光设备
                        if (smartPart3.getFunctions().getD() == 1 || smartPart3.getFunctions().getRgb() == 1) {// 判断灯光有没有调光功能 // 判断灯光有没有调色功能
                            Intent intent = new Intent(getActivity(), LightSettingActivity.class);
                            intent.putExtra("deviceId", smartPart3.getId());
                            intent.putExtra("smartPart", smartPart3); // 把部件信息传过去
                            startActivity(intent);
                        }
                    } else if (smartPart3.getType().equals("6")) { // 玻璃  ，不做任何跳转
                    } else if (smartPart3.getType().equals("4")) { // 窗帘  ，不做任何跳转
                    } else {  // 其他设备均进入设备操作界面
                        Intent intent = new Intent(getActivity(), DeviceFragmentActivity.class);
                        intent.putExtra("deviceId", smartPart3.getId());
                        intent.putExtra("isOwner", isOwner);
                        intent.putExtra("smartPart", smartPart3); // 把部件信息传过去
                        startActivity(intent);
                    }
                }
                break;

            /*** 设备操作部分 ************** END *************************************************/
        }
    }


    /***
     * 编辑房间名
     ***/
    private void showEditDeviceDialog(final SmartPart smartPart) {
        MyDialogUtil dialog = new MyDialogUtil(getActivity()) {
            @Override
            public void confirm() {
            }
        };
        dialog.setInputDialogClicklistener(new MyDialogUtil.showInputDialogOnClickInterface() {
            @Override
            public void doEdit(String content) {
                editDeviceName = content;
                mPresenter.scenePartEdit(smartPart.getId(), content); // 修改设备名字
            }
        });
        dialog.showInputDialog("编辑部件", "请修改新的部件名称", smartPart.getName());
    }

    /***
     * 全关模式提示弹窗
     * @param typeCode
     */
    private void showAllCloseDialog(final String typeCode, String comA) {
        MyDialogUtil dialog = new MyDialogUtil(getActivity()) {
            @Override
            public void confirm() {
                sendCMD(typeCode, "00");
            }
        };
        dialog.showTipDialog(getString(R.string.fragment_main_all_close1), getString(R.string.fragment_main_all_close2));
    }

    /***
     * 显示更多房子弹窗
     */
    private PopupWindow popupWindow;

    private void showPopWindow() {
        View view = null;
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getActivity());
          /*  ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.gray_transparent));
            popupWindow.setBackgroundDrawable(dw);
*/
            setBackgroundAlpha(0.5f);//设置屏幕透明度
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
            view = getActivity().getLayoutInflater().inflate(R.layout.popwindow_layout_more_newhouse, null);
            RecyclerView recyclerView_myhours = (RecyclerView) view.findViewById(R.id.recyclerView_myhours);
            RelativeLayout rl_add_hours = (RelativeLayout) view.findViewById(R.id.rl_add_hours);
            LinearLayout ll_item = (LinearLayout) view.findViewById(R.id.ll_item);

            recyclerView_myhours.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView_myhours.setAdapter(houseAdapter);
            houseAdapter.upDateAdapter(houselist);

            popupWindow.setContentView(view);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popupWindow = null;
                    setAninmation(2);// 结束
                    // popupWindow隐藏时恢复屏幕正常透明度
                    setBackgroundAlpha(1.0f);
                }
            });
            rl_add_hours.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    startActivity(new Intent(getActivity(), NewHouseActivity.class));
                }
            });
            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            if (Build.VERSION.SDK_INT >= 24) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
                int[] location = new int[2]; // 记录anchor在屏幕中的位置
                ll_more.getLocationOnScreen(location);
                int offsetY = location[1] + ll_more.getHeight();
//                if (Build.VERSION.SDK_INT == 25 || Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27) { // Android 7.1中 和 8.0 ，PopupWindow高度为 match_parent 时，会占据整个屏幕
                if (Build.VERSION.SDK_INT >= 25) { // Android 7.1中 和 8.0 ，PopupWindow高度为 match_parent 时，会占据整个屏幕
                    // 故而需要在 Android 7.1上再做特殊处理
                    int screenHeight = APP.screenHeight;// 获取屏幕高度
                    popupWindow.setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
                }
                popupWindow.showAtLocation(ll_more, Gravity.NO_GRAVITY, 0, offsetY);

            } else {
                popupWindow.showAsDropDown(ll_more);
            }

        } else {

            if (Build.VERSION.SDK_INT >= 24) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
                int[] location = new int[2]; // 记录anchor在屏幕中的位置
                ll_more.getLocationOnScreen(location);
                int offsetY = location[1] + ll_more.getHeight();
                if (Build.VERSION.SDK_INT >= 25) { // Android 7.1中 和 8.0 ，PopupWindow高度为 match_parent 时，会占据整个屏幕
                    // 故而需要在 Android 7.1上再做特殊处理
                    int screenHeight = APP.screenHeight;// 获取屏幕高度
                    popupWindow.setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
                }
                popupWindow.showAtLocation(ll_more, Gravity.NO_GRAVITY, 0, offsetY);
            } else {
                popupWindow.showAsDropDown(ll_more);
            }
        }
    }

    /***
     * 设置弹窗
     */
    private PopupWindow setingPopupWindow;

    private void showSettingPopWindow() {
        setingPopupWindow = null;
        View view = null;
        if (setingPopupWindow == null) {
            setingPopupWindow = new PopupWindow(getActivity());
            setBackgroundAlpha(0.5f);//设置屏幕透明度
            setingPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            setingPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            setingPopupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);

            view = getActivity().getLayoutInflater().inflate(R.layout.popwindow_layout_home_seting, null);
            RelativeLayout rl_scene_edit = (RelativeLayout) view.findViewById(R.id.rl_scene_edit); // 编辑模式
            RelativeLayout rl_scene_new = (RelativeLayout) view.findViewById(R.id.rl_scene_new); // 新增模式
            RelativeLayout rl_edit_device = (RelativeLayout) view.findViewById(R.id.rl_edit_device); // 编辑设备名字
            RelativeLayout rl_timing = (RelativeLayout) view.findViewById(R.id.rl_timing);// 定时
            LinearLayout ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
            setingPopupWindow.setContentView(view);
            setingPopupWindow.setFocusable(true);
            setingPopupWindow.setOutsideTouchable(true);

            if (isChangyong) { // 常用时隐藏了编辑场景这个操作
                rl_scene_edit.setVisibility(View.GONE);
            } else {
                rl_scene_edit.setVisibility(View.VISIBLE);
            }

            rl_scene_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setingPopupWindow.dismiss();
                    Intent intent = new Intent(getActivity(), SceneEditActivity.class);
                    intent.putExtra("type", "EDIT");
                    intent.putExtra("roomId", roomId);
                    intent.putExtra("sceneTypeCode", sceneTypeCode);
                    context.startActivity(intent);
                }
            });
            rl_scene_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setingPopupWindow.dismiss();
                    Intent intent = new Intent(getActivity(), SceneNewActivity.class);
                    intent.putExtra("type", "NEW");
                    startActivity(intent);
                }
            });
            rl_timing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setingPopupWindow.dismiss();
                    Intent intent = new Intent(getActivity(), DeviceTimingActivity.class);
                    startActivity(intent);
                }
            });
            rl_edit_device.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_title_right.setText("完成");
                    isEditDevice = true;
                    deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
                    setingPopupWindow.dismiss();
                }
            });
            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setingPopupWindow.dismiss();
                }
            });
            if (Build.VERSION.SDK_INT >= 24) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
                int[] location = new int[2]; // 记录anchor在屏幕中的位置
                tv_title_right.getLocationOnScreen(location);
                int offsetY = location[1] + tv_title_right.getHeight();
                if (Build.VERSION.SDK_INT >= 25) { // Android 7.1中 和 8.0 ，PopupWindow高度为 match_parent 时，会占据整个屏幕
                    // 故而需要在 Android 7.1上再做特殊处理
                    int screenHeight = APP.screenHeight;// 获取屏幕高度
                    setingPopupWindow.setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
                }
                setingPopupWindow.showAtLocation(tv_title_right, Gravity.NO_GRAVITY, 0, offsetY);
            } else {
                setingPopupWindow.showAsDropDown(tv_title_right);
            }
        } else {
            if (Build.VERSION.SDK_INT >= 24) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
                int[] location = new int[2]; // 记录anchor在屏幕中的位置
                tv_title_right.getLocationOnScreen(location);
                int offsetY = location[1] + tv_title_right.getHeight();
                if (Build.VERSION.SDK_INT >= 25) { // Android 7.1中 和 8.0 ，PopupWindow高度为 match_parent 时，会占据整个屏幕
                    // 故而需要在 Android 7.1 和 8.0上再做特殊处理
                    int screenHeight = APP.screenHeight;// 获取屏幕高度
                    setingPopupWindow.setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
                }
                setingPopupWindow.showAtLocation(tv_title_right, Gravity.NO_GRAVITY, 0, offsetY);
            } else {
                setingPopupWindow.showAsDropDown(tv_title_right);
            }
        }
    }


    private RotateAnimation rotateAnimation; // 旋转动画

    public void setAninmation(int aninmation) {
       /* if (aninmation == 1) {
            rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            iv_more.clearAnimation();
            rotateAnimation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
        }

        rotateAnimation.setInterpolator(new LinearInterpolator());// 不停顿
        rotateAnimation.setFillAfter(true);// 停在最后
        rotateAnimation.setDuration(300);
        iv_more.startAnimation(rotateAnimation);*/
        if (aninmation == 1) {
            Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.forward);
            operatingAnim.setFillAfter(true);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            iv_more.startAnimation(operatingAnim);
        } else {
            Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.reversal);
            operatingAnim.setFillAfter(true);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            iv_more.startAnimation(operatingAnim);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
      /*  WindowManager.LayoutParams lp = (getActivity()).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (getActivity()).getWindow().setAttributes(lp);*/
    }

    @Override
    public void getPatternListSuccess(ResGetPatternList resGetPatternList) {
//        Gson gson = new Gson();
//        String content = gson.toJson(resGetPatternList);
        /*isEditRoom = false;
        tv_title_left.setText("");
        roomAdapter.upDateAdapter(roomlist, false); // 更新界面*/

        showPatternList(resGetPatternList);// 显示最新的场景模式列表
    }

    private void showPatternList(ResGetPatternList resGetPatternList) {
        if (resGetPatternList.getList().size() > 0) {
            sceneTotalList = resGetPatternList.getList();
        }


       /* if (sceneTypeCode!=null && !TextUtils.isEmpty(sceneTypeCode)) {
            scenelist.clear();
            for (int i = 0; i <sceneTotalList.size() ; i++) {
                if (sceneTotalList.get(i).getTypeCode()!=null  &&  sceneTotalList.get(i).getTypeCode().equals(sceneTypeCode)) {
                    scenelist.add(sceneTotalList.get(i));
                }
            }
                patternListAdapter.upDateAdapter(scenelist);
        }*/

    }

    @Override
    public void getPatternListFali(String msg) {
//        showToast(msg);
    }

    @Override
    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(getActivity(), "houseId", "");// 传房子id
    }

    @Override
    public void getRoomListSuccess(final ResGetRoomList resGetRoomList) {

//        Gson gson = new Gson();
//        String content = gson.toJson(resGetRoomList);

        showMeunList(resGetRoomList);

        roomName = resGetRoomList.getHouse().getEstate() + "-" + resGetRoomList.getHouse().getBuiding() + "-" + resGetRoomList.getHouse().getHouseNo();
        tv_title_content.setText(roomName);// 显示选择的房子
        if (resGetRoomList.getSceneList().size() > 0) {
            APP.DeviceId = resGetRoomList.getSceneList().get(0).getDevice();
            //尝试更新位置
            LocationUtil.updateLocation(getActivity());
            //向tcp服务器注册
            RegistCMD registCMD = new RegistCMD();
            TcpClient.getInstacne().sendCMD(registCMD);
        }
        if (resGetRoomList.getMaster().getUserId().equals(APP.user.getUserId())) {
            // 业主
            isOwner = true;
        } else {
            // 非业主
            isOwner = false;
        }
    }


    private void showMeunList(ResGetRoomList resGetRoomList) {
        roomPart = resGetRoomList.getSmInfoBean().getRoomPart();
        room = resGetRoomList.getSmInfoBean().getRoom();

        /****更新侧栏信息*************************************/
        // 更新侧栏常用列表
       /* EventBusUIMessage message = new EventBusUIMessage();
        message.setMsgType(Msg.EVENBUS_UPDE__DATELIST);
        message.setData(partTotalList);
        EventBus.getDefault().post(message);*/
        // 更新侧栏房间列表
        ResRoomListBean resRoomListBean = new ResRoomListBean();
        if (partTotalList.size() > 0) {
            resRoomListBean.setScenePartTotalisNull(false);
        } else {
            resRoomListBean.setScenePartTotalisNull(true);
        }
        resRoomListBean.setSmInfoBean(resGetRoomList.getSceneList());// 所有房子以及部件信息
        // 选择了房子发生变化，更新房间、部件信息
        EventBusUIMessage message1 = new EventBusUIMessage();
        message1.setMsgType(Msg.SWITCH_HOUSE);
        message1.setData(resRoomListBean);
        EventBus.getDefault().post(message1);
        // 更新侧栏房子名字
        EventBusUIMessage message2 = new EventBusUIMessage();
        message2.setMsgType(Msg.EVENBUS_UPDE__HOUSE_NAME);
        message2.setData(resGetRoomList.getHouse());
        EventBus.getDefault().post(message2);


    }


    @Override
    public void getRoomListFali(String msg) {
//        showToast("获取房间列表失败");
    }

    @Override
    public void getHoursListSuccess(ResGetUserHouseList resGetUserHouseList) {
        String content = new Gson().toJson(resGetUserHouseList);
        houselist.clear();
        boolean hasExamine =false; // 有待审核的房子
        for (int i = 0; i < resGetUserHouseList.getHouseList().size(); i++) {
            if (resGetUserHouseList.getHouseList().get(i).getState().equals("1")) { //状态：0、未审核，1、审核通过，2、拒绝
                houselist.add(resGetUserHouseList.getHouseList().get(i));
            }
            if (resGetUserHouseList.getHouseList().get(i).getState().equals("0")) { //状态：0、未审核，1、审核通过，2、拒绝
                hasExamine=true;
            }
        }


//        houselist = resGetUserHouseList.getHouseList();
        if (houselist.size() > 0) {
            ll_title.setVisibility(View.VISIBLE);
            iv_more.setVisibility(View.VISIBLE);
            tv_title_content.setClickable(false);
            houseAdapter.upDateAdapter(houselist);
        } else if(hasExamine){
            showExamineView(); // 加载提示审核状态
        }else {
            showEmpty(); // 加载空view
        }

    }

    @Override
    public void getHoursListFali(String msg) {
//        showToast(getString(R.string.fragment_main_get_house_fail));
    }

    /***获取设备常用列表**/
    @Override
    public void scenePartTotalSuccess(ResGetScenePartTotal resGetScenePartTotal) {
        partTotalList = resGetScenePartTotal.getPartTotal();
        /*if (resGetScenePartTotal.getPartTotal() != null && resGetScenePartTotal.getPartTotal().size() > 0) {
            deviceList.clear();
            deviceList.addAll(resGetScenePartTotal.getPartTotal());
            deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
        }*/
    }

    @Override
    public void scenePartTotalFali(String msg) {

    }

    @Override
    public void patternActiveSuccess(BaseResponse baseResponse) {
        mPresenter.getPatternList(); // 获取模式列表
    }

    @Override
    public void patternActiveFali(String msg) {
//        showToast(msg);
    }

    @Override
    public void scenePartEditSuccess(BaseResponse baseResponse) {
        // 修改成功后， 改变本地UI显示
       /* if (isOpen) {  // 开
            isOpen = false;
            iv_timing.setImageResource(R.drawable.icon_btn_off);
            ll_timing.setVisibility(View.GONE);
        } else {                                                // 关
            isOpen = true;
            iv_timing.setImageResource(R.drawable.icon_btn_on);
            ll_timing.setVisibility(View.VISIBLE);
        }
        */
        deviceList.get(mEditDevicePosition).setName(editDeviceName);
        deviceListAdapter.upDateAdapter(deviceList, isEditDevice);

    }

    @Override
    public void scenePartEditFail(String msg) {
        showToast(msg);
    }

    @Override
    public void getUserInfoSuccess(ResGetUserInfo resGetUserInfo) {
        String content = new Gson().toJson(resGetUserInfo);
        APP.user.setDefaultBuidingName(resGetUserInfo.getDefaultBuidingName());
        APP.user.setDefaultEstateName(resGetUserInfo.getDefaultEstateName());
        APP.user.setDefaultHouseName(resGetUserInfo.getDefaultHouseName());
        String mHouseId = resGetUserInfo.getDefaultHouseId();
        if (resGetUserInfo.getBindType().equals("0")) {//绑定类型，0为管理员，1为普通用户
            APP.user.setOwner(true);
        } else {
            APP.user.setOwner(false);
        }
        if (TextUtils.isEmpty(mHouseId)) {
            showEmpty();
            SharedPreferencesUtils.setParam(getActivity(), "houseId", "");// 初始化的时候，把默认房子id肤赋值给，当前用户房子id
            APP.user.setDefaultHouseId("");
//            EventBus.getDefault().post(new EventBusUIMessage(Msg.SWITCH_HOUSE)); // 刷新使用习惯数据
        } else {
            APP.user.setDefaultHouseId(resGetUserInfo.getDefaultHouseId());
            SharedPreferencesUtils.setParam(getActivity(), "houseId", mHouseId);// 初始化的时候，把默认房子id肤赋值给，当前用户房子id
            refreshHouser(); // 刷新全部
        }
    }

    @Override
    public void getUserInfoFali(String msg) {
//        showToast(msg);
    }

    @Override
    public void setPresenter(FragmentMainContract.Presenter presenter) {

    }


    /****更改主页部件列表显示***/
    public void changeDeviceList(boolean ischangyong, int position, String roomName, String typeCode) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_home_scena_name_next); // 设置房间名，图片
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_title_left.setCompoundDrawables(null, null, drawable, null);
        tv_title_left.setText(TextUtil.setTest(roomName));

        mChoicePosition = position;
        sceneTypeCode = typeCode;
        isChangyong = ischangyong;
        if (isChangyong) { // 常用列表数据
            deviceList.clear();
            if (partTotalList.size() > 0) {
                deviceList.addAll(partTotalList);
                deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
                ll_status.showData();
            } else {
                View view = ll_status.showEmpty(R.layout.custom_empty_view2);
                ImageView iv = (ImageView) view.findViewById(R.id.iv);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText("暂无常用部件");
            }



            /*ResGetPatternList.ListBeaner listBeaner = new ResGetPatternList.ListBeaner();
            listBeaner.setStatus("0");
            listBeaner.setClick(false);
            listBeaner.setName("回家模式");
            listBeaner.setId("回家模式");
            listBeaner.setNum("001");
            listBeaner.setType("1");

            ResGetPatternList.ListBeaner listBeaner2 = new ResGetPatternList.ListBeaner();
            listBeaner2.setStatus("0");
            listBeaner2.setClick(false);
            listBeaner2.setName("离家模式");
            listBeaner2.setId("离家模式");
            listBeaner2.setNum("001");
            listBeaner2.setType("0");

            scenelist.add(listBeaner);
            scenelist.add(listBeaner2);
*/

            if (sceneTotalList.size() >= 2) {
                scenelist.clear();
                scenelist.add(sceneTotalList.get(0));
                scenelist.add(sceneTotalList.get(1));
                patternListAdapter.upDateAdapter(scenelist);
                ll_title.setVisibility(View.VISIBLE);
            }


        } else {              // 非常用列表数据
            scenelist.clear();
            for (int i = 0; i < sceneTotalList.size(); i++) {
                if (sceneTotalList.get(i).getTypeCode() != null && sceneTotalList.get(i).getTypeCode().equals(typeCode)) {
                    scenelist.add(sceneTotalList.get(i));
                }
            }
            if (scenelist.size() > 0) {
                ll_title.setVisibility(View.VISIBLE);
                patternListAdapter.upDateAdapter(scenelist);
            } else {
                ll_title.setVisibility(View.GONE);
            }
            deviceList.clear();
            roomId = room.get(position - 1).getId();
            deviceList.addAll(roomPart.get(position - 1));//这里减1是因为这个position是侧栏房间位置，常用固定了，减去1变成对用房间对用下面部件的位置
            if (deviceList.size() > 0) {
                ll_status.showData();
                deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
            } else {
                View view = ll_status.showEmpty(R.layout.custom_empty_view2);
                ImageView iv = (ImageView) view.findViewById(R.id.iv);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText("该房间无部件");
            }
        }
    }

    /***侧栏通知改了选中的名字***/
    public void changeRoomName(String roomName) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_home_scena_name_next); // 设置房间名，图片
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_title_left.setCompoundDrawables(null, null, drawable, null);
        tv_title_left.setText(TextUtil.setTest(roomName));
    }

    /**
     * 处理eventBus消息
     *
     * @param msg
     */
    protected void handleMessage(EventBusMessage msg) {
        if (msg.getMsgType() == Msg.MY_HOUSE_CHANGE) { // 房子信息、数量发生变化
//            refreshHouser(); // 刷新主页房子数据
            TcpResHouseRespones response = (TcpResHouseRespones) msg.getData();
            String result = response.getResult();
            if ("3334".equals(result)) { // 业主移除家庭成员--》》 将移除消息推送给具体移除的家庭成员
                String id = response.getExtraData().getHouseId();
                String state = response.getExtraData().getState();
                if (id.equals(getHouseId())) {             /**被移除结果通知，判断被移除房子id与当前房子id是否一致，**/
                    mPresenter.getUserInfo();/// 重新获取用户信息（获取默认房子id）
                } else {
                    mPresenter.getHoursList();// 只刷新房子列表
                }
            } else if ("3335".equals(result)) {  // 业主审核成员结果--》》 将审核结果推送给提交审核者
                String id = response.getExtraData().getHouseId();
                if (TextUtils.isEmpty(APP.user.getDefaultHouseId())) {  /**申请加入房子结果通知，判断当前登录或初始化的默认房子id是否为空，**/
                    mPresenter.getUserInfo();/// 重新获取用户信息（获取默认房子id）
                } else {
                    mPresenter.getHoursList();// 只刷新房子列表
                }
            }
         /*       // 自己主动退出，或删除房子、修改默认房子触发（ 5553、5554、 是自己在本地定义的）
            else if (Msg.EVENBUS_HOUSE_MESSAGE_5553.equals(result)) {  // 自己退出默认房子，刷新当天界面
                mPresenter.getUserInfo();/// 重新获取用户信息（获取默认房子id）
            } else if (Msg.EVENBUS_HOUSE_MESSAGE_5554.equals(result)) {  // 修改默认房子
            }*/
        } else if (msg.getMsgType() == Msg.EVENBUS_HOUSE_MESSAGE_5553) {// 自己退出默认房子，刷新当天界面
            mPresenter.getUserInfo();/// 重新获取用户信息（获取默认房子id）
        } else if (msg.getMsgType() == Msg.EVENBUS_HOUSE_MESSAGE_5554) {// 修改默认房子
            mPresenter.getUserInfo();/// 重新获取用户信息（获取默认房子id）
        } else if (msg.getMsgType() == Msg.MY_PART_CHANGE) {   // 模式发生变化
            SmartPartStateTCP response = (SmartPartStateTCP) msg.getData();
            if (response.getCommand() == null) return;
            if (response.getCommand().getN() == null) return;
            String commandName = response.getCommand().getN();
            if (commandName.length() > 3) {
                commandName = commandName.substring(commandName.length() - 3);
            }


            String device = response.getDevice();
            boolean ishave = false;
            if (!TextUtils.isEmpty(device) && APP.DeviceId.equals(device)) { // 当前房子id 下才更新当前房子的模式列表
                for (int i = 0; i < scenelist.size(); i++) {
                    if (scenelist.get(i).getNum().equals(commandName)) {
                        ishave = true;
                    }
                }
                if (ishave) {
                    // 改变模式状态
                    for (int i = 0; i < scenelist.size(); i++) {
                        if (scenelist.get(i).getNum().equals(commandName)) {
                            scenelist.get(i).setStatus("1");
                            if (commandName.equals("001")) {// 因为001 是回家模式跟离家模式共用的
                                if (response.getCommand().getA().equals("00")) { // 离家模式
                                    scenelist.get(0).setStatus("1");
                                    scenelist.get(1).setStatus("0");
                                } else {                                           // 回家模式
                                    scenelist.get(0).setStatus("0");
                                    scenelist.get(1).setStatus("1");
                                }
                            }
                        } else {
                            scenelist.get(i).setStatus("0");
                        }
                    }
                    patternListAdapter.upDateAdapter(scenelist); // 改变模式视图
                }
                mPresenter.getPatternList(); // 刷新模式列表信息
            }

        } else if (msg.getMsgType() == Msg.SAMRT_PART_STATE_CHANGE) {   // 设备状态发生变化
            SmartPartStateTCP smartPartStateTCP = (SmartPartStateTCP) msg.getData();
            if (deviceList == null || !APP.DeviceId.equals(smartPartStateTCP.getDevice())) {//当中控设备不是当前房子的中控设备
                return;
            }
            String deviceName = smartPartStateTCP.getCommand().getN();
            if (deviceName.indexOf("AC") != -1) { // 空调状态更新
                SmartPartStateTCP smartPartAir = (SmartPartStateTCP) msg.getData();
                Command command = smartPartAir.getCommand();
                for (SmartPart sm : deviceList) {
                    //找出状态改变的部件
                    if (sm.getCode().equals(command.getN())) {
                        if (command != null) {
                            if (command.getA() != null) {
                                sm.getState().setA(command.getA());
                            }
                            if (command.getF() != null) {
                                sm.getState().setF(command.getF());
                            }
                            if (command.getM() != null) {
                                sm.getState().setM(command.getM());
                            }
                            if (command.getT() != null) {
                                sm.getState().setT(Integer.valueOf(command.getT()));
                            }
                            deviceListAdapter.notifyDataSetChanged();
                        }
                    }
                }
                allDeviceStateChange((SmartPartStateTCP) msg.getData());
            } else if (deviceName.indexOf("CU") != -1) {  // 窗帘状态更新
                SmartPartStateTCP smartPartAir = (SmartPartStateTCP) msg.getData();
                Command command = smartPartAir.getCommand();
                for (SmartPart sm : deviceList) {
                    //找出状态改变的部件
                    if (sm.getCode().equals(command.getN())) {
                        if (command != null) {
                            if (command.getA() != null && command.getA().equals("02")) {
                                sm.setTypeName("stop");
                                deviceListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            } else {
                lightStateChange((SmartPartStateTCP) msg.getData());
                allDeviceStateChange((SmartPartStateTCP) msg.getData());
            }

           /* if (deviceName.indexOf("LI") != -1) { // 灯光状态更新
            } else
                else if (deviceName.indexOf("NF") != -1) {// 新风状态更新
            } else if (deviceName.indexOf("CU") != -1) {// 窗帘状态更新
            } else if (deviceName.indexOf("LK") != -1) {// 门锁状态更新
            } else if (deviceName.indexOf("AG") != -1) {// 雾化玻璃状态更新
            } else if (deviceName.indexOf("FH") != -1) {// 窗帘状态更新
            }*/


        }
    }

/*    @Override
    public void HandleUIMessage(EventBusUIMessage msg) {
//        super.HandleUIMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.SAMRT_PART_STATE_CHANGE:
                SmartPartStateTCP smartPartStateTCP = (SmartPartStateTCP) msg.getData();
                String deviceName = smartPartStateTCP.getCommand().getN();
                lightStateChange((SmartPartStateTCP) msg.getData());


                if (deviceName.indexOf("LI") != -1) { // 灯光状态更新
                } else if (deviceName.indexOf("LI") != -1) { //空调状态更新
                } else if (deviceName.indexOf("AC") != -1) { // 新风状态更新
                } else if (deviceName.indexOf("NF") != -1) {// 新风状态更新
                } else if (deviceName.indexOf("CU") != -1) {// 窗帘状态更新
                } else if (deviceName.indexOf("LK") != -1) {// 门锁状态更新
                } else if (deviceName.indexOf("AG") != -1) {// 雾化玻璃状态更新
                } else if (deviceName.indexOf("FH") != -1) {// 窗帘状态更新
                }
                break;
        }
    }*/


    public void lightStateChange(SmartPartStateTCP state) {
        if (deviceList == null || !APP.DeviceId.equals(state.getDevice())) {
            //当中控设备不是当前房子的中控设备
            return;
        }
        Command command = state.getCommand();
        for (SmartPart sm : deviceList) {
            //找出状态改变的部件
            if (sm.getCode().equals(command.getN())) {
                //如果状态和现在的不一样
                if (command.getA() != null && !command.getA().equals(sm.getState().getA())) {
                    sm.getState().setA(command.getA());
                    deviceListAdapter.notifyDataSetChanged();
                }
                if (sm.getFunctions().getD() == 1) { // 灯光调光
                    if (command.getD().equals("00")) {
                        sm.getState().setA("00");
                        sm.getState().setD(command.getD());
                        deviceListAdapter.notifyDataSetChanged();
                    } else {
                        if (sm.getState().getA() != null && sm.getState().getA().equals("00")) {
                            sm.getState().setA("01");
                            sm.getState().setD(command.getD());
                            deviceListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }

    public void allDeviceStateChange(SmartPartStateTCP state) {
        if (!APP.DeviceId.equals(state.getDevice())) {
            //当中控设备不是当前房子的中控设备
            return;
        }
        Command command = state.getCommand();
        for (SmartPart sm : partTotalList) {
            String deviceName = state.getCommand().getN();
            if (deviceName.indexOf("AC") != -1) { // 空调状态更新
                //找出状态改变的部件
                if (sm.getCode().equals(command.getN())) {
                    if (command != null) {
                        if (command.getA() != null) {
                            sm.getState().setA(command.getA());
                        }
                        if (command.getF() != null) {
                            sm.getState().setF(command.getF());
                        }
                        if (command.getM() != null) {
                            sm.getState().setM(command.getM());
                        }
                        if (command.getT() != null) {
                            sm.getState().setT(Integer.valueOf(command.getT()));
                        }
                    }
                }
            } else {
                //找出状态改变的部件
                if (sm.getCode().equals(command.getN())) {
                    //如果状态和现在的不一样
                    if (command.getA() != null && !command.getA().equals(sm.getState().getA())) {
                        sm.getState().setA(command.getA());
                    }
                    if (sm.getFunctions().getD() == 1) { // 灯光调光
                        if (command.getD().equals("00")) {
                            sm.getState().setA("00");
                            sm.getState().setD(command.getD());
                        } else {
                            if (sm.getState().getA() != null && sm.getState().getA().equals("00")) {
                                sm.getState().setA("01");
                                sm.getState().setD(command.getD());
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < roomPart.size(); i++) {
            for (SmartPart sm : roomPart.get(i)) {
                String deviceName = state.getCommand().getN();
                if (deviceName.indexOf("AC") != -1) { // 空调状态更新
                    //找出状态改变的部件
                    if (sm.getCode().equals(command.getN())) {
                        if (command != null) {
                            if (command.getA() != null) {
                                sm.getState().setA(command.getA());
                            }
                            if (command.getF() != null) {
                                sm.getState().setF(command.getF());
                            }
                            if (command.getM() != null) {
                                sm.getState().setM(command.getM());
                            }
                            if (command.getT() != null) {
                                sm.getState().setT(Integer.valueOf(command.getT()));
                            }
                        }
                    }
                } else {
                    //找出状态改变的部件
                    if (sm.getCode().equals(command.getN())) {
                        //如果状态和现在的不一样
                        if (command.getA() != null && !command.getA().equals(sm.getState().getA())) {
                            sm.getState().setA(command.getA());
                        }
                        if (sm.getFunctions().getD() == 1) { // 灯光调光
                            if (command.getD().equals("00")) {
                                sm.getState().setA("00");
                                sm.getState().setD(command.getD());
                            } else {
                                if (sm.getState().getA() != null && sm.getState().getA().equals("00")) {
                                    sm.getState().setA("01");
                                    sm.getState().setD(command.getD());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*****将string数据转换成json****/
    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /***
     * 加载提示审核的View
     */
    private void showExamineView() {
        ll_title.setVisibility(View.GONE);// 把场景模块隐藏
        // 没有默认房子或默认房子id为空
        ll_status.setImage(R.drawable.ic_no_house);
        tv_title_left.setCompoundDrawables(null, null, null, null);
        tv_title_left.setText("");
        tv_title_content.setCompoundDrawables(null, null, null, null);
        tv_title_content.setText("我的住宅");
        iv_more.setVisibility(View.GONE);
        tv_title_content.setClickable(true);
        ll_status.setText("您的申请已提交，请等待审核");
        View view = ll_status.showEmpty(R.layout.custom_empty_view);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        iv.setImageResource(R.drawable.ic_show_empty_view);
        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setVisibility(View.GONE);
    }
    /***
     * 加载空的View
     */
    private void showEmpty() {
        ll_title.setVisibility(View.GONE);// 把场景模块隐藏
        // 没有默认房子或默认房子id为空
        ll_status.setImage(R.drawable.ic_no_house);
        tv_title_left.setCompoundDrawables(null, null, null, null);
        tv_title_left.setText("");
        tv_title_content.setCompoundDrawables(null, null, null, null);
        tv_title_content.setText(getString(R.string.fragment_main_newhouse));
        iv_more.setVisibility(View.GONE);
        tv_title_content.setClickable(true);
        ll_status.setText(getString(R.string.fragment_main_nohouse));
        View view = ll_status.showEmpty(R.layout.custom_empty_view);
        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewHouseActivity.class));
            }
        });
    }

    /***
     * 刷新房子，模式，场景数据
     **/
    private void refreshHouser() {
        partTotalList.clear();// 清除常用数据
        mPresenter.scenePartTotal();  // 获取常用设备列表
        mPresenter.getPatternList(); // 获取模式列表
        mPresenter.getRoomList();  // 重新获取房间、部件列表
        mPresenter.getHoursList();
    }

    /****体验账号下，修改场景模式，改变部件本地状态*****/
    private void changeDeviceStatus(int position, String open) {
        if (scenelist.get(position).getName().equals("回家模式")) {
            for (int j = 0; j < resGetRoomList.getSmInfoBean().getRoomPart().size(); j++) {
                for (int i = 0; i < resGetRoomList.getSmInfoBean().getRoomPart().get(j).size(); i++) {
                    resGetRoomList.getSmInfoBean().getRoomPart().get(j).get(i).getState().setA("01");
                }
            }
            for (int i = 0; i < deviceList.size(); i++) {
                deviceList.get(i).getState().setA("01");
            }
        } else if (scenelist.get(position).getName().equals("离家模式")) {
            for (int j = 0; j < resGetRoomList.getSmInfoBean().getRoomPart().size(); j++) {
                for (int i = 0; i < resGetRoomList.getSmInfoBean().getRoomPart().get(j).size(); i++) {
                    resGetRoomList.getSmInfoBean().getRoomPart().get(j).get(i).getState().setA("00");
                }
            }
            for (int i = 0; i < deviceList.size(); i++) {
                deviceList.get(i).getState().setA("00");
            }
        } else if (scenelist.get(position).getName().equals("全关模式")) {
            for (int i = 0; i < deviceList.size(); i++) {
                deviceList.get(i).getState().setA("00");
            }
        } else if (scenelist.get(position).getName().equals("全开模式")) {
            for (int i = 0; i < deviceList.size(); i++) {
                deviceList.get(i).getState().setA("01");
            }
        } else if (scenelist.get(position).getName().equals("迎宾模式")) {
            for (int i = 0; i < deviceList.size(); i++) {
                if (deviceList.get(i).getName().equals("客厅射灯") ||
                        deviceList.get(i).getName().equals("客厅吊灯") ||
                        deviceList.get(i).getName().equals("玄关射灯")) {
                    deviceList.get(i).getState().setA("01");
                } else {
                    deviceList.get(i).getState().setA("00");
                }
            }
        } else if (scenelist.get(position).getName().equals("日常模式")) {
            for (int i = 0; i < deviceList.size(); i++) {
                if (deviceList.get(i).getName().equals("餐厅灯带") ||
                        deviceList.get(i).getName().equals("客厅吊灯") ||
                        deviceList.get(i).getName().equals("客厅壁灯") ||
                        deviceList.get(i).getName().equals("客厅灯带")) {
                    deviceList.get(i).getState().setA("01");
                } else {
                    deviceList.get(i).getState().setA("00");
                }
            }
        } else if (scenelist.get(position).getName().equals("就餐模式")) {
            for (int i = 0; i < deviceList.size(); i++) {
                if (deviceList.get(i).getName().equals("餐厅吊灯")) {
                    deviceList.get(i).getState().setA("01");
                } else {
                    deviceList.get(i).getState().setA("00");
                }
            }
        } else if (scenelist.get(position).getName().equals("晨起模式")) {
            for (int i = 0; i < deviceList.size(); i++) {
                if (deviceList.get(i).getName().equals("主卧灯带") || deviceList.get(i).getName().equals("主卧吊灯")) {
                    deviceList.get(i).getState().setA("01");
                } else {
                    deviceList.get(i).getState().setA("00");
                }
            }
        } else if (scenelist.get(position).getName().equals("夜起模式")) {
            for (int i = 0; i < deviceList.size(); i++) {
                if (deviceList.get(i).getName().equals("主卧灯带") || deviceList.get(i).getName().equals("主卧浴室灯")) {
                    deviceList.get(i).getState().setA("01");
                } else {
                    deviceList.get(i).getState().setA("00");
                }
            }
        } else if (scenelist.get(position).getName().equals("阅读模式")) {
            for (int i = 0; i < deviceList.size(); i++) {
                if (deviceList.get(i).getName().equals("主卧阅读灯")) {
                    deviceList.get(i).getState().setA("01");
                } else {
                    deviceList.get(i).getState().setA("00");
                }
            }
        }
        deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
    }


    /****加载体验数据*****/
    private void getExperienceList() {
        tv_title_content.setCompoundDrawables(null, null, null, null);
        tv_title_content.setText("体验区");
        iv_more.setVisibility(View.GONE);
        tv_title_content.setClickable(true);
        tv_title_right.setVisibility(View.GONE);
        partTotalList.clear();// 清除常用数据
        try {
            String JsonData = getJson(getActivity(), "device_experience.json");//获取assets目录下的json文件数据
            resGetRoomList = new Gson().fromJson(JsonData, ResGetRoomList.class);//用Gson 转成实体

            String resGetPatternData = getJson(getActivity(), "pattern_experience.json");//获取assets目录下的json文件数据
            resGetPatternList = new Gson().fromJson(resGetPatternData, ResGetPatternList.class);//用Gson 转成实体
            sceneTotalList = resGetPatternList.getList();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // 这几个是为了在把常用数据部分区分开来，之前是在一份device_experience.json 数据下的
        partTotalList = resGetRoomList.getSmInfoBean().getRoomPart().get(0);
        resGetRoomList.getSmInfoBean().getRoomPart().remove(0);
        resGetRoomList.getSmInfoBean().getRoom().remove(0);
        resGetRoomList.getSceneList().remove(0);

        showMeunList(resGetRoomList); // 设备列表
        showPatternList(resGetPatternList); // 场景模式列表
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data) { // 体验状态下编辑部件名字
            String lightName = data.getStringExtra("lightName");
            deviceList.get(mPosition).setName(lightName);
            deviceListAdapter.upDateAdapter(deviceList, isEditDevice);
        }
    }
}
