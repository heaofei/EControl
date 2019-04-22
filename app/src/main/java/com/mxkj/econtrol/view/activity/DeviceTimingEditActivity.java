package com.mxkj.econtrol.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;
import com.mxkj.econtrol.bean.request.ReqScenePartTimerSave;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetScenePartTimerList;
import com.mxkj.econtrol.contract.DiviceTimingEditContract;
import com.mxkj.econtrol.contract.LightTimingEditContract;
import com.mxkj.econtrol.model.DeviceTimingEditModel;
import com.mxkj.econtrol.model.DeviceTimingListModel;
import com.mxkj.econtrol.model.LightTimingEditModel;
import com.mxkj.econtrol.presenter.DeviceTimingEditPresenter;
import com.mxkj.econtrol.presenter.LightTimingEditPresenter;
import com.mxkj.econtrol.utils.ChoiceWeekDialogUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.TextUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/***
 * Created by ${  chenjun  } on 2017/7/31.
 * 灯光定时编辑， 添加
 */
public class DeviceTimingEditActivity extends BaseActivity implements View.OnClickListener, DiviceTimingEditContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;

    private TextView tv_relation;
    private RelativeLayout rl_relation;
    private RelativeLayout rl_repeat;
    private FrameLayout fragmen_fragment;
    private TextView tv_week;
    private TextView tv_renwu;
    private TextView tv_open;
    private TextView tv_close;
    private TextView btn_delete;

    private TimePickerView pvTime; // 时间选择空间
    private DiviceTimingEditContract.Presenter mPresenter;
    private String operatorId;
    private String mPartId; // 选中的部件Id
    private String mPartName = ""; // 选中的部件名称
    private ResGetScenePartTimerList.TimerListBean timerListBean01; // 选中的部件
    private int open = 1; // 开关选项
    private String hh = "00";
    private String mm = "00";
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_device_timing_edit);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
//        tv_title_left.setCompoundDrawables(null, null, null, null);
        tv_title_right.setText("保存");

        fragmen_fragment = (FrameLayout) findViewById(R.id.fragmen_fragment);
        rl_relation = (RelativeLayout) findViewById(R.id.rl_relation);
        rl_repeat = (RelativeLayout) findViewById(R.id.rl_repeat);
        tv_relation = (TextView) findViewById(R.id.tv_relation);
        tv_week = (TextView) findViewById(R.id.tv_week);
        tv_renwu = (TextView) findViewById(R.id.tv_renwu);
        tv_open = (TextView) findViewById(R.id.tv_open);
        tv_close = (TextView) findViewById(R.id.tv_close);
        btn_delete = (TextView) findViewById(R.id.btn_delete);

        if (getIntent().getStringExtra("type").equals("EDIT")) {   // 定时编辑
            btn_delete.setVisibility(View.VISIBLE);
            tv_title_content.setText("编辑定时");

            timerListBean01 = (ResGetScenePartTimerList.TimerListBean) getIntent().getSerializableExtra("TimerListBean");
            operatorId = getIntent().getStringExtra("operatorId");
            mPartId = getIntent().getStringExtra("partId");
            mPartName = getIntent().getStringExtra("partName");
            week = getIntent().getStringExtra("week");
            if (week.indexOf("周一") != -1) {
                newWeekList.add(true);
            } else {
                newWeekList.add(false);
            }
            if (week.indexOf("周二") != -1) {
                newWeekList.add(true);
            } else {
                newWeekList.add(false);
            }
            if (week.indexOf("周三") != -1) {
                newWeekList.add(true);
            } else {
                newWeekList.add(false);
            }
            if (week.indexOf("周四") != -1) {
                newWeekList.add(true);
            } else {
                newWeekList.add(false);
            }
            if (week.indexOf("周五") != -1) {
                newWeekList.add(true);
            } else {
                newWeekList.add(false);
            }
            if (week.indexOf("周六") != -1) {
                newWeekList.add(true);
            } else {
                newWeekList.add(false);
            }
            if (week.indexOf("周日") != -1) {
                newWeekList.add(true);
            } else {
                newWeekList.add(false);
            }
            tv_week.setText(week);
            tv_relation.setText(mPartName);
            time = getIntent().getStringExtra("time");
            open = getIntent().getIntExtra("open", 1);
            if (open == 1) {
                Drawable drawable = getResources().getDrawable(R.drawable.home_edit_choose_selected32);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_open.setCompoundDrawables(drawable, null, null, null);
                drawable = getResources().getDrawable(R.drawable.home_edit_choose_default32);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_close.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable2 = getResources().getDrawable(R.drawable.home_edit_choose_selected32);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                tv_close.setCompoundDrawables(drawable2, null, null, null);
                drawable2 = getResources().getDrawable(R.drawable.home_edit_choose_default32);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                tv_open.setCompoundDrawables(drawable2, null, null, null);
            }
            int index = time.indexOf(":");
            hh = time.substring(0, index);
            mm = time.substring(index + 1, time.length());

        } else {                                                   // 新增定时
            btn_delete.setVisibility(View.GONE);
            tv_title_content.setText("新增定时");
            operatorId = getIntent().getStringExtra("operatorId");
            timerListBean01 = new ResGetScenePartTimerList.TimerListBean();
        }
        mPresenter = new DeviceTimingEditPresenter(this, new DeviceTimingEditModel());


    }

    @Override
    protected void initData() {

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(2013, 0, 23, Integer.valueOf(hh), Integer.valueOf(mm));
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                RelativeLayout rl = (RelativeLayout) v;
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                time = df.format(date);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final RelativeLayout rl_item = (RelativeLayout) v.findViewById(R.id.rl_item);
//                        rl_item.setVisibility(View.GONE);
                    }
                })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{false, false, false, true, true, false})
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .isCyclic(true)//是否循环滚动
//                .setRangDate(startDate, endDate)
                .setDecorView(fragmen_fragment)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .build();
        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
        if (pvTime != null) {
            // pvTime.setDate(Calendar.getInstance());
           /* pvTime.show(); //show timePicker*/
            pvTime.show(rl_relation, false);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view

        }


    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        tv_open.setOnClickListener(this);
        tv_close.setOnClickListener(this);
        rl_repeat.setOnClickListener(this);
        rl_relation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                showTipDialog(); // 放弃编辑弹窗
                break;
            case R.id.tv_title_right:

                if (TextUtils.isEmpty(mPartId)) {
                    showToast("关联部件不能为空");
                    return;
                }
                if (TextUtils.isEmpty(tv_week.getText().toString().trim())) {
                    showToast("重复时间不能为空");
                    return;
                }
                pvTime.returnData();
               /* ReqScenePartOperatorTimerSave request = new ReqScenePartOperatorTimerSave();
                request.setId(id);
                request.setOperatorId(operatorId); // 上一级传过来的业务id（定时开或定时关的id）
                request.setStatus("1"); // 添加的时候，默认开的状态
                request.setTime(time);*/

                //   参数 open  0  -关，1-开  time 定时时间  houseId partId  week 重复周一，周二，id定时任务
                ReqScenePartTimerSave reqScenePartTimerSave = new ReqScenePartTimerSave();
                reqScenePartTimerSave.setId(operatorId);
                reqScenePartTimerSave.setHouseId(getHouseId());
                reqScenePartTimerSave.setOpen(open);
                reqScenePartTimerSave.setPartId(mPartId);
                reqScenePartTimerSave.setWeek(tv_week.getText().toString().trim());
                reqScenePartTimerSave.setTime(time);
                mPresenter.scenePartTimerSave(reqScenePartTimerSave);

                break;
            case R.id.tv_open:
                Drawable drawable = getResources().getDrawable(R.drawable.home_edit_choose_selected32);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_open.setCompoundDrawables(drawable, null, null, null);
                drawable = getResources().getDrawable(R.drawable.home_edit_choose_default32);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_close.setCompoundDrawables(drawable, null, null, null);
                open = 1;
                break;
            case R.id.tv_close:
                open = 0;
                Drawable drawable2 = getResources().getDrawable(R.drawable.home_edit_choose_selected32);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                tv_close.setCompoundDrawables(drawable2, null, null, null);
                drawable2 = getResources().getDrawable(R.drawable.home_edit_choose_default32);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                tv_open.setCompoundDrawables(drawable2, null, null, null);
                break;
            case R.id.btn_delete: // 删除定时
                mPresenter.scenePartTimerDelete(operatorId);
                break;
            case R.id.rl_repeat: // 选择重复时间
                showRepeatDialog();
                break;
            case R.id.rl_relation: // 选择关联部件
                Intent intent = new Intent(this, EditDeviceListActivity.class);
                if (getIntent().getStringExtra("type").equals("EDIT")) {   // 定时编辑
                    intent.putExtra("type", "TimingEdit");
                } else {
                    intent.putExtra("type", "TimingNew");
                }
                intent.putExtra("TimerListBean", (Serializable) timerListBean01);
                startActivityForResult(intent, 0);

                break;

        }
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
    public void onBackPressed() {
        showTipDialog();
//        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 0) {
                List<ResGetPatternDetail.ScenePartBean> scenePartBean = (List<ResGetPatternDetail.ScenePartBean>) data.getSerializableExtra("dataList");
                mPartName = "";
                mPartId = "";
                List<ResGetScenePartTimerList.TimerListBean.PartListBean> partList = new ArrayList<>();
                for (int i = 0; i < scenePartBean.size(); i++) {
                    for (int j = 0; j < scenePartBean.get(i).getPartSetting().size(); j++) {
                        if (scenePartBean.get(i).getPartSetting().get(j).getSwitchStatus().equals("1")) { // 拿选中的出来
                            ResGetScenePartTimerList.TimerListBean.PartListBean partListBean = new ResGetScenePartTimerList.TimerListBean.PartListBean();
                            partListBean.setPartId(scenePartBean.get(i).getPartSetting().get(j).getPartId());
                            partListBean.setPartName(scenePartBean.get(i).getPartSetting().get(j).getPartName());
                            partListBean.setPartType(scenePartBean.get(i).getPartSetting().get(j).getPartType());
                            partList.add(partListBean);
                            mPartName += scenePartBean.get(i).getPartSetting().get(j).getPartName() + ",";
                            mPartId += scenePartBean.get(i).getPartSetting().get(j).getPartId() + ",";
                        }
                    }
                }
                timerListBean01.setPartList(partList); // 替换掉选中这个部件
                mPartId = mPartId.replaceAll("null", "");
                tv_relation.setText(mPartName);

            }
        }

    }

    /***选择重复时间弹窗***/
    ChoiceWeekDialogUtil choiceWeekDialogUtil;
    String week = "";
    List<Boolean> newWeekList = new ArrayList<>();

    private void showRepeatDialog() {
        choiceWeekDialogUtil = new ChoiceWeekDialogUtil(this) {
            @Override
            public void confirm(List<Boolean> weekList) {
                week = "";
                for (int i = 0; i < weekList.size(); i++) {
                    if (i == 0 && weekList.get(0)) {
                        week += "周一,";
                    }
                    if (i == 1 && weekList.get(1)) {
                        week += "周二,";
                    }
                    if (i == 2 && weekList.get(2)) {
                        week += "周三,";
                    }
                    if (i == 3 && weekList.get(3)) {
                        week += "周四,";
                    }
                    if (i == 4 && weekList.get(4)) {
                        week += "周五,";
                    }
                    if (i == 5 && weekList.get(5)) {
                        week += "周六,";
                    }
                    if (i == 6 && weekList.get(6)) {
                        week += "周日,";
                    }
                }
                tv_week.setText(week);
                newWeekList = weekList;
            }
        };
        choiceWeekDialogUtil.showChoiceWeekDialog(newWeekList);


    }

    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");
    }

    @Override
    public void scenePartTimerSaveSuccess(BaseResponse baseResponse) {
//        showToast(baseResponse.getMsg());
        finish();
    }

    @Override
    public void scenePartTimerSaveFail(String msg) {
        showToast(msg);
    }

    @Override
    public void scenePartTimerDeleteSuccess(BaseResponse baseResponse) {
        finish();
    }

    @Override
    public void scenePartTimerDeleteFail(String msg) {
        showToast(msg);
    }

    @Override
    public void setPresenter(DiviceTimingEditContract.Presenter presenter) {

    }
}
