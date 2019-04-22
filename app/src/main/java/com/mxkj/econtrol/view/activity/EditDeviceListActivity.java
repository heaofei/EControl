package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.PartSettingsBean;
import com.mxkj.econtrol.bean.request.ReqPatternAddBean;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTimerList;
import com.mxkj.econtrol.bean.response.TcpResPatternRespones;
import com.mxkj.econtrol.contract.EditDeviceListContract;
import com.mxkj.econtrol.contract.SceneEditContract;
import com.mxkj.econtrol.model.EditDeviceListModel;
import com.mxkj.econtrol.presenter.EditDeviceListPresenter;
import com.mxkj.econtrol.ui.activity.HouseControlLogActivity;
import com.mxkj.econtrol.ui.adapter.EditDeviceListAdapter;
import com.mxkj.econtrol.ui.adapter.HouseControlLogListAdapter;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.widget.ClearEditText;
import com.mxkj.econtrol.widget.NestedExpandaleListView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*****
 *
 * 定时选择设备界面
 *
 */
public class EditDeviceListActivity extends BaseActivity implements EditDeviceListContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;
    private NestedExpandaleListView expandableListView;
    private RelativeLayout rl_edit_name;
    private ClearEditText ed_name;
    private TextView btn_delete;
    private ResGetScenePartTimerList.TimerListBean timerListBean01; // 编辑定时选中的部件

    private EditDeviceListContract.Presenter mPresenter;
    private List<ResGetPatternDetail.ScenePartBean> mScenePartBeanList = new ArrayList<>();
    private String mPatternId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_device_list);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        tv_title_right = findView(R.id.tv_title_right);

        expandableListView = findView(R.id.expandableListView);
        rl_edit_name = findView(R.id.rl_edit_name);
        btn_delete = findView(R.id.btn_delete);
        ed_name = findView(R.id.ed_name);

    }

    @Override
    protected void initData() {
        mPresenter = new EditDeviceListPresenter(this, new EditDeviceListModel());
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type")) && getIntent().getStringExtra("type").equals("SCANEEDIT")) {  // 编辑场景设备
            btn_delete.setVisibility(View.VISIBLE);
            mPatternId = getIntent().getStringExtra("id");
            String name = getIntent().getStringExtra("name");
            ed_name.setText(getIntent().getStringExtra("name"));
            mPresenter.getPatternDetail(mPatternId);
            tv_title_content.setText("编辑场景");
            rl_edit_name.setVisibility(View.VISIBLE);
            btn_delete.setText("删除场景");
            tv_title_right.setText("保存");
            tv_title_right.setOnClickListener(this);
        } else {                                                                                                                       // 编辑定时设备
            if (getIntent().getStringExtra("type").equals("TimingEdit")) {
            }
                timerListBean01 = (ResGetScenePartTimerList.TimerListBean) getIntent().getSerializableExtra("TimerListBean");
            rl_edit_name.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
            tv_title_content.setText("选择部件");
            mPresenter.getHouseAllPartList(getHouseId());
        }


    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
//
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                Intent intent = new Intent();
                intent.putExtra("dataList", (Serializable) mScenePartBeanList);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_title_right:

                if (!TextUtils.isEmpty(getIntent().getStringExtra("type")) && getIntent().getStringExtra("type").equals("SCANEEDIT")) { // 场景编辑


                    ReqPatternAddBean reqPatternAddBean; // 编辑或新增模式装载状态数据的实体类
                    PartSettingsBean partSettingsBean;  // 编辑或新增模式装载状态数据的实体类
                    List<PartSettingsBean> partList = new ArrayList<>();  // 编辑或新增模式装载状态数据的实体类

                    reqPatternAddBean = new ReqPatternAddBean();
                    for (int i = 0; i < mScenePartBeanList.size(); i++) {
                        for (int j = 0; j < mScenePartBeanList.get(i).getPartSetting().size(); j++) {
                            partSettingsBean = new PartSettingsBean();
                            partSettingsBean.setPartId(mScenePartBeanList.get(i).getPartSetting().get(j).getPartId());
                            partSettingsBean.setSwitchStatus(mScenePartBeanList.get(i).getPartSetting().get(j).getSwitchStatus());
                            partList.add(partSettingsBean);
                        }
                    }
                    reqPatternAddBean.setName(ed_name.getText().toString().trim());
                    reqPatternAddBean.setHouseId(getHouseId());
                    reqPatternAddBean.setPartSettings(partList);
                    reqPatternAddBean.setPatternId(mPatternId); // 场景id
                    mPresenter.patternEdit(new Gson().toJson(reqPatternAddBean)); // 编辑模式

                }
                /*else {
                    Intent intent = new Intent();
                    intent.putExtra("dataList", (Serializable) mScenePartBeanList);
                    setResult(RESULT_OK, intent);
                    finish();
                }*/

                break;
            case R.id.btn_delete:
                mPresenter.patternDelete(mPatternId);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("dataList", (Serializable) mScenePartBeanList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void setPresenter(EditDeviceListContract.Presenter presenter) {

    }

    @Override
    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");
    }

    @Override
    public void getPatternDetailSuccess(ResGetPatternDetail resGetPatternDetail) {
        ed_name.setText(TextUtil.setTest(resGetPatternDetail.getName()));
        mScenePartBeanList = resGetPatternDetail.getScenes();

        for (int i = 0; i > mScenePartBeanList.size(); i--) {
            for (int j = 0; j > mScenePartBeanList.get(i).getPartSetting().size(); j--) {
                String type = mScenePartBeanList.get(i).getPartSetting().get(j).getPartType();
                boolean isShowDevice = false;
                if (type.equals("LI") || type.equals("AC") || type.equals("CU")) {  // 这里过滤了， 灯光、空调、窗帘，才能显示
                    isShowDevice = true;
                }
                if (isShowDevice) {
                    mScenePartBeanList.get(i).getPartSetting().remove(j);
                }
            }
        }
        expandableListView.setAdapter(new EditDeviceListAdapter(EditDeviceListActivity.this, mScenePartBeanList));

    }

    @Override
    public void getPatternDetailFali(String msg) {

    }

    @Override
    public void getHouseAllPartListSuccess(ResGetHouseAllPartList resGetHouseAllPartList) {


        ResGetPatternDetail resGetPatternDetail = new ResGetPatternDetail();
        ResGetPatternDetail.ScenePartBean scenePartBean;
        ResGetPatternDetail.PartSettingBean partSettingBean;
        List<ResGetPatternDetail.PartSettingBean> mPartSettingList;

        // 把数据装进ResGetPatternDetail这个实体类，共用获取场景详情的adapter
        for (int i = 0; i < resGetHouseAllPartList.getSceneList().size(); i++) {
            scenePartBean = resGetPatternDetail.new ScenePartBean();
            mPartSettingList = new ArrayList<>();
            for (int j = 0; j < resGetHouseAllPartList.getSceneList().get(i).getPartList().size(); j++) {
                String type = resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getType();
                if (type.equals("LI") || type.equals("AC") || type.equals("CU")) {  // 这里过滤了， 灯光、空调、窗帘，才能显示
                    partSettingBean = resGetPatternDetail.new PartSettingBean();
                    partSettingBean.setPartName(resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getName());
                    String id = resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getId();
                    partSettingBean.setPartId(id);
                    if (getIntent().getStringExtra("type").equals("TimingEdit")) {
                        for (int k = 0; k < timerListBean01.getPartList().size(); k++) {
                            if (id.equals(timerListBean01.getPartList().get(k).getPartId())) {
                                partSettingBean.setSwitchStatus("1");
                            }
                        }
                    }
                    partSettingBean.setPartType(resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getType());
                    mPartSettingList.add(partSettingBean);
                }
            }
            scenePartBean.setName(resGetHouseAllPartList.getSceneList().get(i).getName());
            scenePartBean.setId(resGetHouseAllPartList.getSceneList().get(i).getId());
            scenePartBean.setPartSetting(mPartSettingList);
            mScenePartBeanList.add(scenePartBean);
        }

        expandableListView.setAdapter(new EditDeviceListAdapter(EditDeviceListActivity.this, mScenePartBeanList));


    }

    @Override
    public void getHouseAllPartListFail(String msg) {
//        showToast(msg);
    }


    @Override
    public void patternEditSuccess(BaseResponse baseResponse) {
//        showToast("编辑成功!");

        // 模式发生变化
        EventBusUIMessage message = new EventBusUIMessage();
        TcpResPatternRespones tcpResPatternRespones = new TcpResPatternRespones();
        TcpResPatternRespones.ExtraData extraData = tcpResPatternRespones.new ExtraData();
        TcpResPatternRespones.ExtraData.PatternBean patternBean = extraData.new PatternBean();
        TcpResPatternRespones.ExtraData.PatternBean.HouseBean houseBean = patternBean.new HouseBean();
        houseBean.setId(getHouseId());
        patternBean.setHouse(houseBean);
        extraData.setPattern(patternBean);
        tcpResPatternRespones.setExtraData(extraData);
        message.setMsgType(Msg.MY_PART_CHANGE);
        message.setData(tcpResPatternRespones);
        EventBus.getDefault().post(message);

        finish();
    }

    @Override
    public void patternEditFali(String msg) {
        showToast(msg);
    }

    @Override
    public void patternDeleteSuccess(BaseResponse baseResponse) {
//        showToast("删除成功!");

        // 模式发生变化
        EventBusUIMessage message = new EventBusUIMessage();
        TcpResPatternRespones tcpResPatternRespones = new TcpResPatternRespones();
        TcpResPatternRespones.ExtraData extraData = tcpResPatternRespones.new ExtraData();
        TcpResPatternRespones.ExtraData.PatternBean patternBean = extraData.new PatternBean();
        TcpResPatternRespones.ExtraData.PatternBean.HouseBean houseBean = patternBean.new HouseBean();
        houseBean.setId(getHouseId());
        patternBean.setHouse(houseBean);
        extraData.setPattern(patternBean);
        tcpResPatternRespones.setExtraData(extraData);
        message.setMsgType(Msg.MY_PART_CHANGE);
        message.setData(tcpResPatternRespones);
        EventBus.getDefault().post(message);
        finish();
    }

    @Override
    public void patternDeleteFali(String msg) {
        showToast(msg);
    }

}
