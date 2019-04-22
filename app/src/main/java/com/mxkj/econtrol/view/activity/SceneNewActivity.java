package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.PartSettingsBean;
import com.mxkj.econtrol.bean.request.ReqPatternAddBean;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.TcpResPatternRespones;
import com.mxkj.econtrol.contract.SceneNewContract;
import com.mxkj.econtrol.model.SceneNewModel;
import com.mxkj.econtrol.presenter.SceneNewPresenter;
import com.mxkj.econtrol.ui.adapter.EditDeviceListAdapter;
import com.mxkj.econtrol.ui.adapter.SceneNewAdapter;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.widget.NestedExpandaleListView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 场景，编辑，新增
 */
public class SceneNewActivity extends BaseActivity implements SceneNewContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private EditText ed_name;
    private NestedExpandaleListView expandableListView;

    private List<ResGetPatternDetail.ScenePartBean> mScenePartBeanList = new ArrayList<>();

    private ReqPatternAddBean reqPatternAddBean; // 编辑或新增模式装载状态数据的实体类
    private PartSettingsBean partSettingsBean;  // 编辑或新增模式装载状态数据的实体类
    private List<PartSettingsBean> partList = new ArrayList<>();  // 编辑或新增模式装载状态数据的实体类

    private int mPosition;

    private SceneNewContract.Presenter mPresenter;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_scene);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_right.setText("保存");

        ed_name = findView(R.id.ed_name);
        expandableListView = findView(R.id.expandableListView);
            tv_title_content.setText("新增场景");

    }

    @Override
    protected void initData() {
        mPresenter = new SceneNewPresenter(this, new SceneNewModel());
        mPresenter.getHouseAllPartList(getHouseId());
    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                showTipDialog();
                break;
            case R.id.tv_title_right:// 新增场景

                if (TextUtils.isEmpty(ed_name.getText().toString().trim())) {
                    showToast("场景名称不能为空");
                    return;
                }
                reqPatternAddBean = new ReqPatternAddBean();
                for (int i = 0; i <mScenePartBeanList.size() ; i++) {
                    for (int j = 0; j <mScenePartBeanList.get(i).getPartSetting().size() ; j++) {
                        partSettingsBean = new PartSettingsBean();
                        partSettingsBean.setPartId(mScenePartBeanList.get(i).getPartSetting().get(j).getPartId());
                        partSettingsBean.setSwitchStatus(mScenePartBeanList.get(i).getPartSetting().get(j).getSwitchStatus());
                        partList.add(partSettingsBean);
                    }
                }
                reqPatternAddBean.setName(ed_name.getText().toString().trim());
                reqPatternAddBean.setHouseId(getHouseId());
                reqPatternAddBean.setPartSettings(partList);
                mPresenter.patternAdd(new Gson().toJson(reqPatternAddBean));// 添加模式

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
    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");//
    }

    /***获取全屋设备列表**/
    @Override
    public void getHouseAllPartListSuccess(final ResGetHouseAllPartList resGetHouseAllPartList) {
//        String content = new Gson().toJson(resGetHouseAllPartList);

        ResGetPatternDetail resGetPatternDetail = new ResGetPatternDetail();
        ResGetPatternDetail.ScenePartBean scenePartBean;
        ResGetPatternDetail.PartSettingBean partSettingBean;
        List<ResGetPatternDetail.PartSettingBean> mPartSettingList;

        // 把数据装进ResGetPatternDetail这个实体类，共用获取场景详情的adapter
        for (int i = 0; i <resGetHouseAllPartList.getSceneList().size() ; i++) {
            scenePartBean  = resGetPatternDetail.new ScenePartBean();
            mPartSettingList = new ArrayList<>();
            for (int j = 0; j < resGetHouseAllPartList.getSceneList().get(i).getPartList().size(); j++) {
                String type = resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getType();
                if (type.equals("LI") || type.equals("AC") || type.equals("CU")) {  // 这里过滤了， 灯光、空调、窗帘，才能显示
                    partSettingBean = resGetPatternDetail.new PartSettingBean();
                    partSettingBean.setPartName(resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getName());
                    partSettingBean.setPartId(resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getId());
                    partSettingBean.setSwitchStatus(resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getSwitchStatus());
                    partSettingBean.setPartType(resGetHouseAllPartList.getSceneList().get(i).getPartList().get(j).getType());
                    mPartSettingList.add(partSettingBean);
                }
            }
            scenePartBean.setName(resGetHouseAllPartList.getSceneList().get(i).getName());
            scenePartBean.setId(resGetHouseAllPartList.getSceneList().get(i).getId());
            scenePartBean.setPartSetting(mPartSettingList);
            mScenePartBeanList.add(scenePartBean);
        }
        expandableListView.setAdapter(new EditDeviceListAdapter(SceneNewActivity.this, mScenePartBeanList));

        /*newPatternList = resGetHouseAllPartList.getSceneList();

        sceneNewAdapter = new SceneNewAdapter(newPatternList, R.layout.adapter_scene_new_item);
        recycle_myroom_list.setAdapter(sceneNewAdapter);
        sceneNewAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPosition = position;
                Intent intent = new Intent(SceneNewActivity.this, EditDeviceListActivity.class);
                intent.putExtra("type", "newScene");
                intent.putExtra("name", newPatternList.get(position).getName());
                intent.putExtra("scenesList", (Serializable) resGetHouseAllPartList.getSceneList().get(position));
                startActivityForResult(intent, 1);
            }
        });*/

    }

    @Override
    public void getHouseAllPartListFail(String msg) {

    }

    @Override
    public void patternAddSuccess(BaseResponse baseResponse) {
//        showToast("新增模式成功!");


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
    public void patternAddFali(String msg) {
        showToast(msg);
    }


    @Override
    public void setPresenter(SceneNewContract.Presenter presenter) {

    }
}
