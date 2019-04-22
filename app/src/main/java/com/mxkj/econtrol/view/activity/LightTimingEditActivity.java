package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;
import com.mxkj.econtrol.contract.LightTimingEditContract;
import com.mxkj.econtrol.model.LightTimingEditModel;
import com.mxkj.econtrol.presenter.LightTimingEditPresenter;
import com.mxkj.econtrol.ui.adapter.TimingListAdapter;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.widget.ItemRemoveRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/***
 * Created by ${  chenjun  } on 2017/7/31.
 * 灯光定时编辑， 添加
 */
public class LightTimingEditActivity extends BaseActivity implements View.OnClickListener, LightTimingEditContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;

    private RelativeLayout rl_keep;
    private RelativeLayout rl_delete;
    private FrameLayout mFrameLayout;
    private TimePickerView pvTime;

    private LightTimingEditContract.Presenter mPresenter;
    private String operatorId;
    private String id;
    private String hh = "00";
    private String mm = "00";
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_timing_add);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_left.setCompoundDrawables(null, null, null, null);
        tv_title_left.setText("取消");

        rl_keep = findView(R.id.rl_keep);
        rl_delete = findView(R.id.rl_delete);
        mFrameLayout = (FrameLayout) findViewById(R.id.fragmen_fragment);

        if (getIntent().getStringExtra("type").equals("EDIT")) {   // 定时编辑
            rl_delete.setVisibility(View.VISIBLE);
            tv_title_content.setText("编辑定时");
            operatorId = getIntent().getStringExtra("operatorId");
            time = getIntent().getStringExtra("time");
            id = getIntent().getStringExtra("id");

            int index = time.indexOf(":");
            hh = time.substring(0, index);
            mm = time.substring(index + 1, time.length());
        } else {                                                   // 新增定时
            rl_delete.setVisibility(View.GONE);
            tv_title_content.setText("新增定时");
            operatorId = getIntent().getStringExtra("operatorId");
        }
        mPresenter = new LightTimingEditPresenter(this, new LightTimingEditModel());


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
                .setDecorView(mFrameLayout)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .build();
        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
        if (pvTime != null) {
            // pvTime.setDate(Calendar.getInstance());
           /* pvTime.show(); //show timePicker*/
            pvTime.show(rl_keep, false);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view

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
        rl_keep.setOnClickListener(this);
        rl_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_keep:
                if (TextUtils.isEmpty(operatorId)) {
                    showToast("关联部件不能为空");
                    return;
                }

                pvTime.returnData();
                ReqScenePartOperatorTimerSave request = new ReqScenePartOperatorTimerSave();
                request.setId(id);
                request.setOperatorId(operatorId); // 上一级传过来的业务id（定时开或定时关的id）
                request.setStatus("1"); // 添加的时候，默认开的状态
                request.setTime(time);
                mPresenter.scenePartOperatorTimerSave(request);

                break;
            case R.id.rl_delete:
                mPresenter.scenePartOperatorTimerDelete(id);
                break;

        }
    }


    @Override
    public void scenePartOperatorTimerSaveSuccess(BaseResponse baseResponse) {
//        String content = new Gson().toJson(baseResponse);

        String name = getIntent().getStringExtra("activity");
        if (name!=null && name.equals("mLightSettingActivity")) {
            Intent intent = new Intent(this, LightTimingListActivity.class);
            intent.putExtra("operatorId", operatorId);// 业务id
            startActivity(intent);
        }
        this.finish();

    }

    @Override
    public void scenePartOperatorTimerSaveFail(String msg) {
        showToast(""+msg);
    }

    @Override
    public void scenePartOperatorTimerDeleteSuccess(BaseResponse baseResponse) {
        String content = new Gson().toJson(baseResponse);
        // 修改成功后刷新数据
        showToast("删除成功");
        finish();
    }

    @Override
    public void scenePartOperatorTimerDeleteFail(String msg) {
        showToast(msg);
    }

    @Override
    public void setPresenter(LightTimingEditContract.Presenter presenter) {

    }
}
