package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetScenePartList;
import com.mxkj.econtrol.bean.response.ResAppPushMessageReply;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetScenePartList;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.contract.SceneEditContract;
import com.mxkj.econtrol.model.SceneEditModel;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.SceneEditPresenter;
import com.mxkj.econtrol.ui.adapter.SceneDeviceEditAdapter;
import com.mxkj.econtrol.ui.adapter.SceneEditListAdapter;
import com.mxkj.econtrol.utils.DeviceOperationUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.MyLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景的部件关联编辑
 */
public class SceneDeviceEditActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private RecyclerView recyclerView_list;
    private MyLinearLayout ll_status;
    private TextView tv_keep;

    private SceneDeviceEditAdapter sceneEditListAdapter;
    private List<SmartPart> deviceList = new ArrayList();
    private String typeCode; // 模式编号，用来编辑模式保存时发送tcp (后面改了不需要这个编号)
    private String editCode; // 房间模式对应编辑编号，用来编辑模式保存时发送tcp
    private int mProgress = 0;//  SeekBar 进度值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scene_device_edit);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))) {
            tv_title_content.setText(getIntent().getStringExtra("name"));
        }
        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);
        recyclerView_list = findView(R.id.recyclerView_list);
        tv_keep = findView(R.id.tv_keep);
        recyclerView_list.setLayoutManager(new LinearLayoutManager(this));

        sceneEditListAdapter = new SceneDeviceEditAdapter(deviceList, R.layout.adapter_scene_edit_device_item, this);
        recyclerView_list.setAdapter(sceneEditListAdapter);


        /*sceneEditListAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });*/
    }

    /***
     * 设置空状态View
     **/
    private void setEmptyView() {
        tv_title_right.setText("");
        ll_status.setImage(R.drawable.ic_no_light);
        ll_status.setText("该房间尚未添加灯光设备");
        View view = ll_status.showEmpty(R.layout.custom_empty_view);
        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        initAdapter();

        String roomId = getIntent().getStringExtra("roomId");
        typeCode = getIntent().getStringExtra("typeCode");
        editCode = getIntent().getStringExtra("editCode");

       /* if (typeCode!=null && typeCode.length()>3) {
             typeCode= typeCode.substring(typeCode.length()-3);
        }*/

        /***获取房间内设备列表*/
        ReqGetScenePartList reqGetScenePartList = new ReqGetScenePartList(roomId);
        RetrofitUtil.getInstance().getmApiService()
                .getScenePartList(reqGetScenePartList.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartList>())
                .subscribe(new APISubcriber<ResGetScenePartList>() {
                    @Override
                    public void onFail(ResGetScenePartList baseResponse, String msg) {
                        showToast(msg);
                    }
                    @Override
                    public void onSuccess(ResGetScenePartList response) {
                        deviceList = response.getLightList();
                        sceneEditListAdapter.upDateAdapter(deviceList);
                    }
                });
    }


    private void initAdapter() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_keep.setOnClickListener(this);
        sceneEditListAdapter.setOnItemChrldListner(new SceneDeviceEditAdapter.OnSeekBarChangeListner() {
            @Override
            public void onCall(View view, int progress, String driveCode, String partId) {
                // 在这里做发送调光进度
                if (progress >= 0 && progress <= 99) {
                    if (progress >= 0 && progress <= 9) {
                        if (mProgress == 0) return;
                        mProgress = 0;
                    } else if (progress > 9 && progress <= 19) {
                        if (mProgress == 1) return;
                        mProgress = 1;
                    } else if (progress > 19 && progress <= 29) {
                        if (mProgress == 2) return;
                        mProgress = 2;
                    } else if (progress > 29 && progress <= 39) {
                        if (mProgress == 3) return;
                        mProgress = 3;
                    } else if (progress > 39 && progress <= 49) {
                        if (mProgress == 4) return;
                        mProgress = 4;
                    } else if (progress > 49 && progress <= 59) {
                        if (mProgress == 5) return;
                        mProgress = 5;
                    } else if (progress > 59 && progress <= 69) {
                        if (mProgress == 6) return;
                        mProgress = 6;
                    } else if (progress > 69 && progress <= 79) {
                        if (mProgress == 7) return;
                        mProgress = 7;
                    } else if (progress > 79 && progress <= 89) {
                        if (mProgress == 8) return;
                        mProgress = 8;
                    } else if (progress > 89 && progress <= 99) {
                        if (mProgress == 9) return;
                        mProgress = 9;
                    }
                    openDimming("0" + mProgress, driveCode, partId);
                    for (int i = 0; i < deviceList.size(); i++) { // 这里是为了保证这个deviceList 列表数据手动改过之后，几时更新数据
                        if (deviceList.get(i).getCode().equals(driveCode)) {
                            deviceList.get(i).getState().setD("0" + mProgress);
                            deviceList.get(i).getState().setA("01");
                        }
                    }
                }
            }
        });
    }

    /****
     * 调光亮度指令发送
     * **/
    private void openDimming(String progress, String driveCode, String partId) {
        final Command command = new Command();
        command.setD(progress);
        command.setN(driveCode);
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(partId);
        smartPartCMD.setHouseId(getHouseId());
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
               /* if (state.getA().equals("00")) {
                    state.setA("01");
                } else {
                    state.setA("00");
                }
                mAdapter.notifyDataSetChanged();*/
//               showToast("===="+result);
//              showToast("===="+result);
            }

            @Override
            public void onFail(String msg) {
//                showToast(msg);
            }
        });

    }

    public static String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(APP.getInstance(), "houseId", "");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_keep:
                sendCMD();
                break;
            case R.id.seekbar_light:

                break;
            case R.id.iv_close:  // 打开设备/关闭设备
                SmartPart smartPart1 = (SmartPart) v.getTag();

                //----后面改成本地更新--star----------------------------------------------------------------------------------------------------------
                if (smartPart1.getType().equals("1") && smartPart1.getFunctions().getD() == 1) { // 调光的灯   并且 支持调光模块
                    if (smartPart1.getState().getA().equals("01") || !smartPart1.getState().getD().equals("00")) { // 不是有亮度的情况下，
                        deviceList.get(smartPart1.getPosition()).getState().setD("00");
                        openDimming("00", smartPart1.getCode(), smartPart1.getId());
                    } else {
                        if (!TextUtils.isEmpty(smartPart1.getState().getD()) && !smartPart1.getState().getD().equals("00")) {
                            deviceList.get(smartPart1.getPosition()).getState().setD("00");
                            openDimming("00", smartPart1.getCode(), smartPart1.getId());
                        } else {
                            deviceList.get(smartPart1.getPosition()).getState().setA("00");
                            deviceList.get(smartPart1.getPosition()).getState().setD("09");
                            openDimming("09", smartPart1.getCode(), smartPart1.getId());
                        }
                    }
                } else {
                    if (smartPart1.getState().getA().equals("00")) {
                        deviceList.get(smartPart1.getPosition()).getState().setA("01");
                        openA("01", smartPart1.getCode(), smartPart1.getId());
                    } else {
                        deviceList.get(smartPart1.getPosition()).getState().setA("00");
                        openA("00", smartPart1.getCode(), smartPart1.getId());
                    }
                }
                sceneEditListAdapter.upDateAdapter(deviceList);

//                DeviceOperationUtil.openDevice(this, smartPart1);
                //-----后面改成本地更新--end-----------------------------------------------------------------------------------------------------------
                break;


        }
    }


    /****
     * 调光亮度指令发送
     * **/
    private void openA(String comA, String driveCode, String partId) {
        final Command command = new Command();
        command.setA(comA);
        command.setN(driveCode);
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(partId);
        smartPartCMD.setHouseId(getHouseId());
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
            }
            @Override
            public void onFail(String msg) {
            }
        });

    }

    private void sendCMD() {

        final SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setN("MS" + editCode); // MS 字段为保存场景字段，
        command.setA("01");
        smartPartCMD.setCommand(command);

        /*if (APP.isExperience) {  // 体验账号下， 不做实际指令发送
            return;
        }*/
        TcpClient.getInstacne().sendCMD(this, smartPartCMD, new TCPCMDResult() {
            @Override
            public void onSuccess(String result) {
                showToast("已保存");
                finish();
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

}
