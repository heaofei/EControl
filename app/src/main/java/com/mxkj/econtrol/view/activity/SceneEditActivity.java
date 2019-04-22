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

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.TCPCMDResult;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.tcpcmd.Command;
import com.mxkj.econtrol.bean.tcpcmd.SmartPartCMD;
import com.mxkj.econtrol.contract.SceneEditContract;
import com.mxkj.econtrol.model.SceneEditModel;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.SceneEditPresenter;
import com.mxkj.econtrol.ui.adapter.SceneEditListAdapter;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.MyLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景，编辑，新增
 */
public class SceneEditActivity extends BaseActivity implements SceneEditContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private RecyclerView recyclerView_list;
    private MyLinearLayout ll_status;

    private SceneEditContract.Presenter mPresenter;
    // 场景列表
    private List<ResGetPatternList.ListBeaner> scenelist = new ArrayList<ResGetPatternList.ListBeaner>();
    private SceneEditListAdapter sceneEditListAdapter;
    private String roomId;// 不同房间针对不同的场景模式，这个是房间id，用来获取该房间下对应的场景模式
    private String sceneTypeCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_scene);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);

        tv_title_content.setText("编辑模式");
        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);
        recyclerView_list = findView(R.id.recyclerView_list);
        recyclerView_list.setLayoutManager(new LinearLayoutManager(this));
        sceneEditListAdapter = new SceneEditListAdapter(scenelist, R.layout.adapter_scene_edit_item);
        recyclerView_list.setAdapter(sceneEditListAdapter);
        sceneEditListAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
//                    Intent intent = new Intent(SceneEditActivity.this, EditDeviceListActivity.class);
                    sendCMD(scenelist.get(position).getNum());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2200); // 休眠2.2秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(SceneEditActivity.this, SceneDeviceEditActivity.class);
                        intent.putExtra("type", "SCANEEDIT");
                        intent.putExtra("id", scenelist.get(position).getId());
                        if (TextUtils.isEmpty(scenelist.get(position).getMsName())) {
                        intent.putExtra("name", scenelist.get(position).getName());
                        }else {
                        intent.putExtra("name", scenelist.get(position).getMsName());
                        }
                        intent.putExtra("typeCode", scenelist.get(position).getTypeCode());
                        intent.putExtra("editCode", scenelist.get(position).getEditCode());
                        intent.putExtra("roomId", roomId);
                        startActivity(intent);
                    }
                }).start();
            }
        });
        roomId  = getIntent().getStringExtra("roomId");
        sceneTypeCode  = getIntent().getStringExtra("sceneTypeCode");
    }

    /*****
     * 编辑该模式下，必须先选中某个模式，一定要切换在某个模式下面先
     * ***/
    private void sendCMD(String typeCode) {

        if (typeCode!=null && typeCode.length()>3) {
            typeCode= typeCode.substring(typeCode.length()-3);
        }

        final SmartPartCMD smartPartCMD = new SmartPartCMD();
        Command command = new Command();
        command.setN("MD"+typeCode);
        command.setA("01");
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
        mPresenter = new SceneEditPresenter(this, new SceneEditModel());
        String houstId =  (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
        if (!TextUtils.isEmpty(houstId)) {
            mPresenter.getPatternList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initAdapter() {

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

               /* if (getIntent().getStringExtra("type").equals("newScene")) { // 新建场景模式
                    Intent intent = new Intent();
                    intent.putExtra("newLightList", (Serializable) newLightList);
                    setResult(RESULT_OK, intent);
                    finish();

                } else if (getIntent().getStringExtra("type").equals("editScene")) { // 编辑场景模式
                    Intent intent = new Intent();
                    intent.putExtra("editLightList", (Serializable) editLightList);
                    setResult(RESULT_OK, intent);
                    finish();
                }*/

        }
    }


    @Override
    public void setPresenter(SceneEditContract.Presenter presenter) {

    }

    @Override
    public String getHouseId() {
        return (String) SharedPreferencesUtils.getParam(this, "houseId", "");// 传房子id
    }

    @Override
    public void getPatternListSuccess(ResGetPatternList resGetPatternList) {
        if (resGetPatternList.getList().size() > 0) {
//            scenelist = resGetPatternList.getList();
            for (int i = 0; i < resGetPatternList.getList().size(); i++) {
                if (resGetPatternList.getList().get(i).getTypeCode()!=null  && resGetPatternList.getList().get(i).getTypeCode().equals(sceneTypeCode)) {
                    scenelist.add(resGetPatternList.getList().get(i));
                }
            }
            sceneEditListAdapter.upDateAdapter(scenelist); // 默认模式
        }

    }

    @Override
    public void getPatternListFali(String msg) {
        showToast(msg);
    }
}
