package com.mxkj.econtrol.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.contract.HouseUserListOperatetContract;
import com.mxkj.econtrol.model.HouseUserListOperatetModel;
import com.mxkj.econtrol.presenter.HouseUserListOperatetPresenter;
import com.mxkj.econtrol.ui.adapter.HouseUserListOperatetAdapter;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/****
 *
 * 家庭成员操作（转让跟删除）
 *
 */
public class HouseUserLisOperatetActivity extends BaseActivity implements HouseUserListOperatetContract.View, CommonAdapter.OnItemClickListener {


    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private HouseUserListOperatetContract.Presenter mPresenter;
    private String mHouseId;
    private RecyclerView mRecyclerView;
    private  HouseUserListOperatetAdapter adapter;
    private List<HouseUser> mUsers; // 操作的用户列表
    private String type="";
    private int clickNumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_house_user_operate_list);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_left.setCompoundDrawables(null,null,null,null);
        tv_title_left.setText("取消");

        type = getIntent().getStringExtra("type");
        if (type.equals("TRANSFER_HOUSE")){
            tv_title_content.setText("转让管理权限");
        }else if (type.equals("DELETE_USER")){
            tv_title_content.setText("删除成员");
            tv_title_right.setText("删除");
            tv_title_right.setTextColor(Color.parseColor("#80ffffff"));
            tv_title_right.setEnabled(false);
        }

        mRecyclerView = findView(R.id.recycle_user_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPresenter = new HouseUserListOperatetPresenter(this , new HouseUserListOperatetModel());
        mHouseId = getIntent().getStringExtra("houseId");
        mHouseId = (String)SharedPreferencesUtils.getParam(this,"houseId","");
//        mPresenter.getHouseUserList(mHouseId);


    }

    @Override
    protected void initData() {
        mUsers = (List<HouseUser>)getIntent().getSerializableExtra("userList");

        if (mUsers.size()>0){
            mUsers.remove(mUsers.size()-1);  // 把减号移除
        }
        Iterator<HouseUser> iterator = mUsers.iterator();
        while (iterator.hasNext()) {
            HouseUser user = iterator.next();
            if (user.getBindType().equals("0")) {
                //移除管理员
                iterator.remove();
                break;
            }
        }
        adapter = new HouseUserListOperatetAdapter(type,mUsers, R.layout.layout_house_user_item02, this);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);



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
                finish();
                break;
            case R.id.tv_title_right:
                showDeleteDialog(nickNameUtil());
                break;
        }
    }


    // 处理显示对话框显示的nickName
    private String nickNameUtil(){
        String niceName = "";
        int k = 0;
        for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).isClick()){
                k+=1;
                niceName +=mUsers.get(i).getNiceName()+"、";
//                houseUserId=mUsers.get(i).getUserId();  // 暂时只能一个
            }
        }
        if (k>0 && k <=3){
            niceName =niceName.substring(0,niceName.length()-1);
        }else if ( k>3){
            niceName="";
            int j=0;
            for (int i = 0; i < mUsers.size(); i++) {
                if (mUsers.get(i).isClick()){
                    j +=1;
                    if (j<=3){
                        niceName +=mUsers.get(i).getNiceName()+"、";
                    }
                }
            }
            niceName =niceName.substring(0,niceName.length()-1)+"等";
        }
        return niceName;
    }

    private int successNumber=0;
    @Override
    public void unbindHouseUserSuccess() {
        successNumber+=1;
        if (successNumber==clickNumber){
             List<HouseUser> mUsersList = new ArrayList<HouseUser>(); // 更新显示的列表
            for (int i = 0; i <mUsers.size() ; i++) {
                if (!mUsers.get(i).isClick()) {
                    mUsersList.add(mUsers.get(i));
                }
            }
            mUsers=mUsersList;
            adapter.upDateAdapter(mUsers);
            showToast("删除成功!");
            tv_title_right.setText("");
        }

    }

    @Override
    public void unbindHouseUserFail(String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void houseManagerSwitchSuccess() {
        showToast("已发起转让请求，请等待新业主确认接收权限");
        finish();
    }

    @Override
    public void houseManagerSwitchFail(String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void setPresenter(HouseUserListOperatetContract.Presenter presenter) {

    }

    @Override
    public void onItemClick(View view, int position) {


        if (type.equals("TRANSFER_HOUSE")){ // 转让管理权
            showTransferDialog(mUsers.get(position).getNiceName(),position);
        }else   if (type.equals("DELETE_USER")){    // 删除家庭成员
            if (mUsers.get(position).isClick()){
                mUsers.get(position).setClick(false);
            }else {
                mUsers.get(position).setClick(true);
            }
            adapter.upDateAdapter(mUsers);
            int size = 0;
            for (int i = 0; i < mUsers.size(); i++) {
                if (mUsers.get(i).isClick()){
                    size += 1;
                }
            }
            if (size>0){
                tv_title_right.setText("删除( "+size+" )");
                tv_title_right.setTextColor(Color.parseColor("#ffffff"));
                tv_title_right.setEnabled(true);
            }else {
                tv_title_right.setText("删除");
                tv_title_right.setTextColor(Color.parseColor("#80ffffff"));
                tv_title_right.setEnabled(false);
            }
        }

    }
    /****
     * 转让操作权对话框
     *
     * ***/
    private void showTransferDialog(String niceName, final int position) {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                mPresenter.houseManagerSwitch(mUsers.get(position).getUserId(), mHouseId, "0"); // 1为转让并退出，0为转让不退出
            }
        };
        dialog.showTipDialog("确认退出？","确定将业住管理权转让给 "+ niceName +" 吗？转让后您将失去管理权限");
    }

    /****
     * 删除成员对话框
     * ***/
    private void showDeleteDialog( String niceName) {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                for (int i = 0; i < mUsers.size(); i++) {
                    if (mUsers.get(i).isClick()){
                        clickNumber+=1;
                        mPresenter.unbindHouseUser(mUsers.get(i).getId());   // 用户绑定的房子关系id
                    }
                }
            }
        };

        dialog.showTipDialog("确认删除？","确定要删除成员 "+ niceName +" ？");
    }


}
