package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.bean.response.SmartPartState;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartStateTCP;
import com.mxkj.econtrol.contract.LightSettingContract;
import com.mxkj.econtrol.model.LightSettingModel;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.LightSettingPresenter;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.ClearEditText;
import com.mxkj.econtrol.widget.ColorPickerView;

import org.greenrobot.eventbus.EventBus;

/***
 * Created by ${  chenjun  } on 2017/7/31.
 * 灯光设置
 */
public class LightSettingActivity extends BaseActivity implements LightSettingContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;

    private ClearEditText ed_name;
    private ImageView iv_timing;
    private RelativeLayout rl_open;
    private RelativeLayout rl_close;
    private LinearLayout ll_timing;
    //调光
    private LinearLayout ll_light;
    private SeekBar seekbar_light;
    private ImageView iv_seekbar_point01, iv_seekbar_point04;
    private ImageView iv_stall01, iv_stall04;
    private int mProgress = 0;//  SeekBar 进度值
    // 调色
    private LinearLayout ll_color;
    private ColorPickerView color_picker_view;
    private ImageView iv_light_color_01, iv_light_color_02, iv_light_color_03, iv_light_color_04, iv_light_color_05, iv_light_color_06, iv_light_color_07;


    private boolean isOpen = false; // 开关状态
    //    private ResGetScenePartDetail mResGetScenePartDetail;
    private LightSettingContract.Presenter mPresenter;
    private String openOperatorId;// 开、列表业务id
    private String closeOperatorId;// 关、列表业务id
    private SmartPart smartPart;
    private boolean isChange = false; // 是否有修改过
    private boolean TimeeOpenListSize = false; // 定时打开列表>0
    private boolean TimeeCloseListSize = false;// 定时关闭列表>0


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_light_setting);
        super.onCreate(savedInstanceState);

    }

    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setText("保存");
        tv_title_content.setText("灯光设置");


        ed_name = (ClearEditText) findViewById(R.id.ed_name);

        iv_timing = (ImageView) findViewById(R.id.iv_timing);
        rl_open = (RelativeLayout) findViewById(R.id.rl_open);
        rl_close = (RelativeLayout) findViewById(R.id.rl_close);
        ll_timing = (LinearLayout) findViewById(R.id.ll_timing);

        ll_light = findView(R.id.ll_light);
        seekbar_light = findView(R.id.seekbar_light);
        iv_seekbar_point01 = findView(R.id.iv_seekbar_point01);
        iv_seekbar_point04 = findView(R.id.iv_seekbar_point04);
        iv_stall01 = findView(R.id.iv_stall01);
        iv_stall04 = findView(R.id.iv_stall04);
        ll_color = findView(R.id.ll_color);
        color_picker_view = findView(R.id.color_picker_view);
//        color_picker_view.setIndicatorColor(color_picker_view.getColor());
        color_picker_view.showDefaultColorTable();
//        color_picker_view.setPosition(-100,0);

        iv_light_color_01 = findView(R.id.iv_light_color_01);
        iv_light_color_02 = findView(R.id.iv_light_color_02);
        iv_light_color_03 = findView(R.id.iv_light_color_03);
        iv_light_color_04 = findView(R.id.iv_light_color_04);
        iv_light_color_05 = findView(R.id.iv_light_color_05);
        iv_light_color_06 = findView(R.id.iv_light_color_06);
        iv_light_color_07 = findView(R.id.iv_light_color_07);

        smartPart = (SmartPart) getIntent().getSerializableExtra("smartPart");

        if (null == smartPart) {
            finish();
            showToast("部件信息加载失败!");
        }

        initLightState(); // 初始化显示灯光状态视图

        mPresenter = new LightSettingPresenter(this, new LightSettingModel());
        if (APP.isLogin) {
            mPresenter.getScenePartDetail(getDeviceId());  // 获取灯设置详情
        }

    }

    private void initLightState() {
        String lightName = smartPart.getName();
        if (!TextUtils.isEmpty(lightName)) {
            ed_name.setText(lightName);
            ed_name.setClearIconVisible(false);
        }
        int d = smartPart.getFunctions().getD(); // 判断灯光有没有调光功能
        int rgb = smartPart.getFunctions().getRgb(); // 判断灯光有没有调色功能
        if (d == 1) {
            ll_light.setVisibility(View.VISIBLE);
        }
        if (rgb == 1) {
            ll_color.setVisibility(View.VISIBLE);
        }
    }


    protected void initData() {


    }

    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        iv_timing.setOnClickListener(this);
        rl_open.setOnClickListener(this);
        rl_close.setOnClickListener(this);
        iv_light_color_01.setOnClickListener(this);
        iv_light_color_02.setOnClickListener(this);
        iv_light_color_03.setOnClickListener(this);
        iv_light_color_04.setOnClickListener(this);
        iv_light_color_05.setOnClickListener(this);
        iv_light_color_06.setOnClickListener(this);
        iv_light_color_07.setOnClickListener(this);
        ed_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    isChange = true;
                    ed_name.setText(ed_name.getText().toString());
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        // 档位选择监听
        seekbar_light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                iv_seekbar_point01.setVisibility(View.VISIBLE);
                iv_seekbar_point04.setVisibility(View.VISIBLE);
                if (progress >= 0 && progress <= 99) {
                    if (progress >= 0 && progress <= 9) {
                        if (mProgress==0) return;
                        mProgress = 0;
                    } else if (progress > 9 && progress <= 19) {
                        if (mProgress==1) return;
                        mProgress = 1;
                    } else if (progress > 19 && progress <= 29) {
                        if (mProgress==2) return;
                        mProgress = 2;
                    } else if (progress > 29 && progress <= 39) {
                        if (mProgress==3) return;
                        mProgress = 3;
                    } else if (progress > 39 && progress <= 49) {
                        if (mProgress==4) return;
                        mProgress = 4;
                    } else if (progress > 49 && progress <= 59) {
                        if (mProgress==5) return;
                        mProgress = 5;
                    } else if (progress > 59 && progress <= 69) {
                        if (mProgress==6) return;
                        mProgress = 6;
                    } else if (progress > 69 && progress <= 79) {
                        if (mProgress==7) return;
                        mProgress = 7;
                    } else if (progress > 79 && progress <= 89) {
                        if (mProgress==8) return;
                        mProgress = 8;
                    } else if (progress > 89 && progress <= 99) {
                        if (mProgress==9) return;
                        mProgress = 9;
                    }
                  onRefreshStall(mProgress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                openDimming("mimming", "0" + mProgress);
            }
        });
        // 颜色选择监听
        color_picker_view.setOnColorPickerChangeListener(new ColorPickerView.OnColorPickerChangeListener() {
            @Override
            public void onColorChanged(ColorPickerView picker, int color) {
                color_picker_view.showIndicatorEnable(true);
                ChangeColor(0);
                color_picker_view.setIndicatorColor(color);
            }

            @Override
            public void onStartTrackingTouch(ColorPickerView picker) {
            }

            @Override
            public void onStopTrackingTouch(ColorPickerView picker, int color) {
                isChange = true;
                String red = String.valueOf(Color.red(color));
                String green = String.valueOf(Color.green(color));
                String blue = String.valueOf(Color.blue(color));
                if (red.length() == 1) {
                    red = "00" + red;
                } else if (red.length() == 2) {
                    red = "0" + red;
                }
                if (green.length() == 1) {
                    green = "00" + green;
                } else if (green.length() == 2) {
                    green = "0" + green;
                }
                if (blue.length() == 1) {
                    blue = "00" + blue;
                } else if (blue.length() == 2) {
                    blue = "0" + blue;
                }
                openDimming("color", red + "," + green + "," + blue);
            }
        });
    }

    /**
     * 更新档位图片
     */
    private void onRefreshStall(int stall) {
        iv_stall01.setImageResource(R.drawable.light1_default);
        iv_stall04.setImageResource(R.drawable.light4_default);
        if (stall == 0) {
            iv_stall01.setImageResource(R.drawable.light1_selected);
            iv_seekbar_point04.setVisibility(View.VISIBLE);
            iv_seekbar_point01.setVisibility(View.INVISIBLE);
        } else if (stall == 9) {
            iv_stall04.setImageResource(R.drawable.light4_selected);
            iv_seekbar_point01.setVisibility(View.VISIBLE);
            iv_seekbar_point04.setVisibility(View.INVISIBLE);
        }else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_left:
                if (isChange) { // 有修改过设备状态、信息
                    showTipDialog(); // 放弃弹窗提醒
                } else {
                    finish();
                }
                break;
            case R.id.tv_title_right:
                if (TextUtils.isEmpty(ed_name.getText().toString().trim())) {
                    showToast("灯光名称不能为空");
                    return;
                }
                if (APP.isExperience) { // 体验状态
                    Intent intent = new Intent();
                    intent.putExtra("lightName", ed_name.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                if (isOpen) {
                    mPresenter.scenePartEdit("1");
                } else {
                    mPresenter.scenePartEdit("0");
                }
                break;
            case R.id.iv_timing:
                if (isOpen) {
                    isOpen = false;
                    iv_timing.setImageResource(R.drawable.icon_btn_off);
                    ll_timing.setVisibility(View.GONE);
                } else {
                    isOpen = true;
                    iv_timing.setImageResource(R.drawable.icon_btn_on);
                    ll_timing.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_open:

                if (TimeeOpenListSize) {
                    Intent intent = new Intent(this, LightTimingListActivity.class);
                    intent.putExtra("operatorId", openOperatorId);// 业务id
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, LightTimingEditActivity.class);
                    intent.putExtra("activity", "mLightSettingActivity");
                    intent.putExtra("type", "NEW");
                    intent.putExtra("operatorId", openOperatorId);// 业务id
                    startActivity(intent);
                }
                break;
            case R.id.rl_close:
                if (TimeeCloseListSize) {
                    Intent intent1 = new Intent(this, LightTimingListActivity.class);
                    intent1.putExtra("operatorId", closeOperatorId);// 业务id
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(this, LightTimingEditActivity.class);
                    intent1.putExtra("activity", "mLightSettingActivity");
                    intent1.putExtra("type", "NEW");
                    intent1.putExtra("operatorId", closeOperatorId);// 业务id
                    startActivity(intent1);
                }

                break;
            case R.id.iv_light_color_01:
                ChangeColor(1);
                break;
            case R.id.iv_light_color_02:
                ChangeColor(2);
                break;
            case R.id.iv_light_color_03:
                ChangeColor(3);
                break;
            case R.id.iv_light_color_04:
                ChangeColor(4);
                break;
            case R.id.iv_light_color_05:
                ChangeColor(5);
                break;
            case R.id.iv_light_color_06:
                ChangeColor(6);
                break;
            case R.id.iv_light_color_07:
                ChangeColor(7);
                break;
        }
    }

    private void ChangeColor(int color) {
        /***
         * 255,255,255
         * 255,107,107
         * 247,153,255
         * 130,148,255
         * 144,222,255
         * 107,255,255
         * 211,255,153
         */
        iv_light_color_01.setImageResource(R.drawable.ic_light_color_ordinary);
        iv_light_color_02.setImageResource(R.drawable.ic_light_color_red);
        iv_light_color_03.setImageResource(R.drawable.ic_light_color_pink);
        iv_light_color_04.setImageResource(R.drawable.ic_light_color_purple);
        iv_light_color_05.setImageResource(R.drawable.ic_light_color_blue);
        iv_light_color_06.setImageResource(R.drawable.ic_light_color_cyan);
        iv_light_color_07.setImageResource(R.drawable.ic_light_color_green);

        if (color == 1) {
            openDimming("color", "255,255,255");
            iv_light_color_01.setImageResource(R.drawable.ic_light_color_ordinary_selected);
        } else if (color == 2) {
            openDimming("color", "255,107,107");
            iv_light_color_02.setImageResource(R.drawable.ic_light_color_red_selected);
        } else if (color == 3) {
            openDimming("color", "247,153,255");
            iv_light_color_03.setImageResource(R.drawable.ic_light_color_pink_selected);
        } else if (color == 4) {
            openDimming("color", "130,148,255");
            iv_light_color_04.setImageResource(R.drawable.ic_light_color_purple_selected);
        } else if (color == 5) {
            openDimming("color", "144,222,255");
            iv_light_color_05.setImageResource(R.drawable.ic_light_color_blue_selected);
        } else if (color == 6) {
            openDimming("color", "107,255,255");
            iv_light_color_06.setImageResource(R.drawable.ic_light_color_cyan_selected);
        } else if (color == 7) {
            openDimming("color", "211,255,153");
            iv_light_color_07.setImageResource(R.drawable.ic_light_color_green_selected);
        }
    }


    public void openDimming(String type, String content) {
        final Command command = new Command();
        command.setType("01");
        final SmartPartState state = smartPart.getState();
        command.setN(smartPart.getCode());
        if (type.equals("mimming")) {
            command.setD(content);
        } else {
            command.setRgb(content);
        }


        openDimming(command);

       /* if (APP.isLogin) {
            EventBus.getDefault().post(new EventBusUIMessage(Msg.ACTIVITY_LIGHT_CHANGE, command));
        }
        */
    }


    /***
     * 显示推出编辑弹窗
     */
    private void showTipDialog() {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                finish();
            }
        };
        dialog.showTipDialog(getString(R.string.giveup_edit), "");
    }

    @Override
    public String getDeviceId() {
        return getIntent().getStringExtra("deviceId");
    }

    @Override
    public String getDeviceName() {
        return ed_name.getText().toString().trim();
    }

    @Override
    public void getScenePartDetailSuccess(ResGetScenePartDetail resGetScenePartDetail) {
        String content = new Gson().toJson(resGetScenePartDetail);

        if (!TextUtils.isEmpty(resGetScenePartDetail.getPart().getState().getD())) {
            int ddd = Integer.valueOf(resGetScenePartDetail.getPart().getState().getD());
            if (ddd==0) {
                seekbar_light.setProgress(1);
            }else if (ddd==1) {
                seekbar_light.setProgress(15);
            }else if (ddd==2) {
                seekbar_light.setProgress(25);
            }else if (ddd==3) {
                seekbar_light.setProgress(35);
            }else if (ddd==4) {
                seekbar_light.setProgress(45);
            }else if (ddd==5) {
                seekbar_light.setProgress(55);
            }else if (ddd==6) {
                seekbar_light.setProgress(65);
            }else if (ddd==7) {
                seekbar_light.setProgress(75);
            }else if (ddd==8) {
                seekbar_light.setProgress(85);
            }else if (ddd==9) {
                seekbar_light.setProgress(99);
            }
            onRefreshStall(ddd);
        }
        /***
         * 255,255,255
         * 255,107,107
         * 247,153,255
         * 130,148,255
         * 144,222,255
         * 107,255,255
         * 211,255,153
         */
        if (resGetScenePartDetail.getPart().getState().getRgb().equals("255,255,255")) {
            ChangeColor(1);
        } else if (resGetScenePartDetail.getPart().getState().getRgb().equals("255,107,107")) {
            ChangeColor(2);
        } else if (resGetScenePartDetail.getPart().getState().getRgb().equals("247,153,255")) {
            ChangeColor(3);
        } else if (resGetScenePartDetail.getPart().getState().getRgb().equals("130,148,255")) {
            ChangeColor(4);
        } else if (resGetScenePartDetail.getPart().getState().getRgb().equals("144,222,255")) {
            ChangeColor(5);
        } else if (resGetScenePartDetail.getPart().getState().getRgb().equals("107,255,255")) {
            ChangeColor(6);
        } else if (resGetScenePartDetail.getPart().getState().getRgb().equals("211,255,153")) {
            ChangeColor(7);
        } else {
            ChangeColor(0);
        }

         /*  if (resGetScenePartDetail.getTimerState().equals("1")) {  // 开
            isOpen = true;
            iv_timing.setImageResource(R.drawable.icon_btn_on);
            ll_timing.setVisibility(View.VISIBLE);
        } else {                                                // 关
            isOpen = false;
            iv_timing.setImageResource(R.drawable.icon_btn_off);
            ll_timing.setVisibility(View.GONE);
        }
        */

       /* for (int i = 0; i < mResGetScenePartDetail.getPartOperators().size(); i++) {
            if (mResGetScenePartDetail.getPartOperators().get(i).getCode().equals("a-01")) {  // 定时打开
                openOperatorId = mResGetScenePartDetail.getPartOperators().get(i).getId();
                mPresenter.getTimerOpenList(openOperatorId);
            }
        }
        for (int i = 0; i < mResGetScenePartDetail.getPartOperators().size(); i++) {
            if (mResGetScenePartDetail.getPartOperators().get(i).getCode().equals("a-00")) {  // 定时关闭
                closeOperatorId = mResGetScenePartDetail.getPartOperators().get(i).getId();
                mPresenter.getTimerCloseList(closeOperatorId);
            }
        }*/

    }

    @Override
    public void getScenePartDetailFail(String msg) {
        showToast(msg);
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

        Intent intent = new Intent();
        intent.putExtra("lightName", ed_name.getText().toString().trim());
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void scenePartEditFail(String msg) {
        showToast(msg);
    }

    @Override
    public void getTimerOpenListSuccess(ResGetScenePartOperatorTimerList resGetScenePartOperatorTimerList) {
        if (resGetScenePartOperatorTimerList.getList() != null && resGetScenePartOperatorTimerList.getList().size() > 0) {
            TimeeOpenListSize = true;
        } else {
            TimeeOpenListSize = false;
        }
    }

    @Override
    public void getTimerCloseListSuccess(ResGetScenePartOperatorTimerList resGetScenePartOperatorTimerList) {
        if (resGetScenePartOperatorTimerList.getList() != null && resGetScenePartOperatorTimerList.getList().size() > 0) {
            TimeeCloseListSize = true;
        } else {
            TimeeCloseListSize = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(openOperatorId)) {
            mPresenter.getTimerOpenList(openOperatorId);
        }
        if (!TextUtils.isEmpty(closeOperatorId)) {
            mPresenter.getTimerCloseList(closeOperatorId);
        }
    }

    @Override
    public void setPresenter(LightSettingContract.Presenter presenter) {

    }


    private void openDimming(final Command command) {
        SmartPartCMD smartPartCMD = new SmartPartCMD();
        smartPartCMD.setCommand(command);
        smartPartCMD.setPartId(smartPart.getId());
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
            }

            @Override
            public void onFail(String msg) {
//                showToast(msg);
            }
        });
    }

//    boolean isGetState = false;

    @Override
    public void HandleUIMessage(EventBusUIMessage msg) {
        super.HandleUIMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.SAMRT_PART_STATE_CHANGE:
                /*LogUtil.i("state:" + msg.getData());
                SmartPartStateTCP  smartPartStateTCP   = (SmartPartStateTCP) msg.getData();
                if (mTcpCmdResultMap != null && mTcpCmdResultMap.containsKey(smartPartStateTCP.getSerial())) {

                }else {
                    lightStateChange((SmartPartStateTCP) msg.getData());
                }*/
                break;
        }
    }

    public void lightStateChange(SmartPartStateTCP state) {
        if (!APP.DeviceId.equals(state.getDevice())) {
            //当中控设备不是当前房子的中控设备
            return;
        }
        Command command = state.getCommand();
        if (command.getN().equals(smartPart.getCode())) {
            showToast("收到亮度" + command.getD());
            int pro = Integer.valueOf(command.getD());
            if (pro==0){
                mProgress=0;
            }else if(pro==9){
                mProgress=99;
            }else {
                mProgress=pro+10;
            }
            seekbar_light.setProgress(mProgress);
        }
    }
    public static String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(APP.getInstance(), "houseId", "");
    }
}
