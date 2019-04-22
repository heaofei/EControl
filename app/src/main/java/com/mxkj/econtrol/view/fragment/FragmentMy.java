package com.mxkj.econtrol.view.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseFragment;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitchHandle;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.ResAppPushMessageTipCount;
import com.mxkj.econtrol.bean.response.ResAppTodoList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.contract.FragmentMyContract;
import com.mxkj.econtrol.model.FragmentMyModel;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.presenter.FragmentMyPresenter;
import com.mxkj.econtrol.ui.activity.AboutThisVersionActivity;
import com.mxkj.econtrol.ui.activity.BrowsePicActivity;
import com.mxkj.econtrol.ui.activity.FeedBackActivity;
import com.mxkj.econtrol.ui.activity.HistoryActivity;
import com.mxkj.econtrol.ui.activity.LoginRegistActivity;
import com.mxkj.econtrol.ui.activity.SecondActivity;
import com.mxkj.econtrol.ui.activity.SettingActivity;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.LoginUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.view.activity.AppTodoListActivity;
import com.mxkj.econtrol.view.activity.ApplyBindHouseListActivity;
import com.mxkj.econtrol.view.activity.HouseUserListActivity;
import com.mxkj.econtrol.view.activity.MessageCenterActivity;
import com.mxkj.econtrol.view.activity.MyHouseListActivity;
import com.mxkj.econtrol.view.activity.UserInfoModifyActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;


/**
 * Created by ${  chenjun  } on 2017/7/26.
 */

public class FragmentMy extends BaseFragment implements FragmentMyContract.View {

    private com.mxkj.econtrol.widget.CircleImageView imv_header;
    private TextView tv_user_name;
    private RelativeLayout rl_info;
    private RelativeLayout rl_scan;
    private RelativeLayout rl_message;
    private RelativeLayout rl_myhouse;
    private RelativeLayout rl_family_member;
    private RelativeLayout rl_history;
    private RelativeLayout rl_feedback;
    private RelativeLayout rl_about;
    private RelativeLayout rl_setting;
    private TextView tv_exit_experience;
    private TextView tv_count; // 消息数量
    private TextView tv_count_apply; // 申请绑定房子数量
    private TextView tv_history;
    private TextView tv_isowen;// 业主还是家属
    private TextView tv_myhouse;
    private TextView tv_family_member;
    private TextView tv_message;
    private TextView tv_scan;

    private ImageView iv_history;
    private ImageView iv_myhouse;
    private ImageView iv_message;
    private ImageView iv_family_member;
    private ImageView iv_scan;

    private FragmentMyContract.Presenter mPresenter;
    private int systemNotify = 0; // 系统通知条数
    private int messageNotify = 0; // 消息提醒条数
    private int totalCount = 0; // 总未读消息数量


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        super.createView(view);
        return view;
    }

    @Override
    public void initView() {
        imv_header = findView(R.id.imv_header);
        rl_info = findView(R.id.rl_info);
        rl_scan = findView(R.id.rl_scan);
        tv_user_name = (TextView) findView(R.id.tv_user_name);
        rl_message = (RelativeLayout) findView(R.id.rl_message);
        rl_myhouse = (RelativeLayout) findView(R.id.rl_myhouse);
        rl_family_member = (RelativeLayout) findView(R.id.rl_family_member);
        rl_history = (RelativeLayout) findView(R.id.rl_history);
        rl_feedback = (RelativeLayout) findView(R.id.rl_feedback);
        rl_about = (RelativeLayout) findView(R.id.rl_about);
        rl_setting = (RelativeLayout) findView(R.id.rl_setting);
        tv_count = (TextView) findView(R.id.tv_count);
        tv_count_apply = (TextView) findView(R.id.tv_count_apply);
        tv_exit_experience = (TextView) findView(R.id.tv_exit_experience);
        tv_message = (TextView) findView(R.id.tv_message);
        tv_history = (TextView) findView(R.id.tv_history);
        tv_myhouse = (TextView) findView(R.id.tv_myhouse);
        tv_family_member = (TextView) findView(R.id.tv_family_member);
        tv_scan = (TextView) findView(R.id.tv_scan);

        iv_message = (ImageView) findView(R.id.iv_message);
        iv_history = (ImageView) findView(R.id.iv_history);
        iv_myhouse = (ImageView) findView(R.id.iv_myhouse);
        iv_family_member = (ImageView) findView(R.id.iv_family_member);
        iv_scan = (ImageView) findView(R.id.iv_scan);
        tv_isowen = (TextView) findView(R.id.tv_isowen);
        tv_isowen.setText("业主");

        if (APP.isExperience) {
            tv_exit_experience.setVisibility(View.VISIBLE);
            rl_setting.setVisibility(View.GONE);

            iv_message.setAlpha(100);
            iv_family_member.setAlpha(100);
            iv_myhouse.setAlpha(100);
            iv_history.setAlpha(100);
            iv_scan.setAlpha(100);

            tv_message.setTextColor(getResources().getColor(R.color.text_black_666al40));
            tv_history.setTextColor(getResources().getColor(R.color.text_black_666al40));
            tv_myhouse.setTextColor(getResources().getColor(R.color.text_black_666al40));
            tv_family_member.setTextColor(getResources().getColor(R.color.text_black_666al40));
            tv_scan.setTextColor(getResources().getColor(R.color.text_black_666al40));

        }else {
            tv_exit_experience.setVisibility(View.GONE);
            rl_setting.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void initData() {
        mPresenter  =  new FragmentMyPresenter(this,new FragmentMyModel());
        if (APP.isLogin) {
            mPresenter.appTodoList();// 请求待处理的列表
        }
        if (APP.user == null) {
            tv_user_name.setText("游客");
        } else {
            tv_user_name.setText(APP.user.getNiceName());
            if (APP.user.getHeadPicture() != null && !TextUtils.isEmpty(APP.user.getHeadPicture())) {
                Glide.with(this)
                        .load(Config.BASE_PIC_URL + APP.user.getHeadPicture())
                        .error(R.drawable.ic_no_head)
                        .into(imv_header);
            }
        }
        gethouUser();
    }

    @Override
    public void onResume() {
        if (APP.isLogin) {
           mPresenter.appPushMessageTipCount();
            getApplyBindHouseList();
        }
//        mPresenter.appTodoList();// 请求待处理的列表
        super.onResume();
    }






    //获取绑定房子审核消息
    public void  getApplyBindHouseList(){
        if (APP.isLogin /*&& APP.user.isOwner()*/) {
            String mHouseId = (String) SharedPreferencesUtils.getParam(getActivity(), "houseId", "");
            mPresenter.getApplyBindHouseList(new ReqGetApplyBindHouseList(1,3000,mHouseId,"0")); // status;    //0、未审核，1、审核，2、拒绝'
        }
    }

    @Override
    public void initListener() {
        imv_header.setOnClickListener(this);
        rl_info.setOnClickListener(this);
        rl_scan.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        rl_myhouse.setOnClickListener(this);
        rl_family_member.setOnClickListener(this);
        rl_history.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        tv_exit_experience.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_scan:                  // 二维码界面
                if (LoginUtil.isExperience(getActivity())) {
                    showToast("游客无法操作此功能");
                    return;
                }
                //只有当系统版本大于等于6.0时才需要
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //判断该应用是否有相机权限，如果没有再去申请
                    if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, 123);
                        // 切记在Fragment里面 不要使用 ActivityCompat.requestPermissions 不然就不能再fragment里面回调onRequestPermissionsResult方法
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 123);
                    }else {
                        //有这个权限，做相应处理
                        Intent intent = new Intent(getActivity(), SecondActivity.class);
                        startActivity(intent);
                    }
                }else {
                    //有这个权限，做相应处理
                    Intent intent = new Intent(getActivity(), SecondActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_info:               // 修改个人信息
                if (LoginUtil.isExperience(getActivity())) {
                    showToast("游客无法操作此功能");
                    return;
                }
                startActivity(new Intent(getActivity(), UserInfoModifyActivity.class));
                break;
            case R.id.imv_header:              // 查看个人头像
//                  startActivity(new Intent(getActivity(), UserInfoModifyActivity.class));
                Intent picDetail = new Intent(getActivity(), BrowsePicActivity.class);
                if (APP.user.getHeadPicture() != null && !TextUtils.isEmpty(APP.user.getHeadPicture())) {
                     picDetail.putExtra("picUrl", APP.user.getHeadPicture());
                }
                startActivity(picDetail);
                break;
            case R.id.tv_user_name:
                break;
            case R.id.rl_message:            // 消息列表
                if (LoginUtil.isExperience(getActivity())) {
                    showToast("游客无法操作此功能");
                    return;
                }
                Intent intent1 = new Intent(getActivity(), MessageCenterActivity.class);
                intent1.putExtra("systemNotify", systemNotify);
                intent1.putExtra("messageNotify", messageNotify);
                startActivity(intent1);
                break;
            case R.id.rl_myhouse:            //  我的住宅列表
                if (LoginUtil.isExperience(getActivity())) {
                    showToast("游客无法操作此功能");
                    return;
                }
                startActivity(new Intent(getActivity(), MyHouseListActivity.class));
                break;
            case R.id.rl_family_member:      // 家庭成员列表
                if (LoginUtil.isExperience(getActivity())) {
                    showToast("游客无法操作此功能");
                    return;
                }
//                String houseId = APP.user.getDefaultHouseId();
                String houseId = (String) SharedPreferencesUtils.getParam(getActivity(), "houseId", "");
                if (TextUtils.isEmpty(houseId)) {
                    showToast(getString(R.string.fragment_my_house_tips));
                } else {
                    startActivity(new Intent(getActivity(), HouseUserListActivity.class));
                }
                break;
            case R.id.rl_history:            // 历史记录
                if (LoginUtil.isExperience(getActivity())) {
                    showToast("游客无法操作此功能");
                    return;
                }
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;
            case R.id.rl_feedback:           // 意见反馈
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.rl_about:              // 关于我们
                startActivity(new Intent(getActivity(), AboutThisVersionActivity.class));
                break;
            case R.id.rl_setting:             // 设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
//                startActivity(new Intent(getActivity(), CameraActivity.class));
//                startActivity(new Intent(getActivity(), DemoActivity.class));
                break;
            case R.id.tv_exit_experience: // 退出体验
                APP.isExperience = false;
                startActivity(new Intent(getActivity(), LoginRegistActivity.class));
                getActivity().finish();
                break;

        }
    }


/*    String result=null;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { /// 扫描二维码结果
            if(1==1){////o代表是上传头像,1代表扫描二维码
                Bundle bundle = data.getExtras();
                result = bundle.getString("result");
            }
        }
    }*/

    /**
     * 处理eventBus消息
     *
     * @param msg
     */
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        if (msg.getMsgType() == Msg.CHANGE_USER_INFO) { // 修改了用户信息
            tv_user_name.setText(APP.user.getNiceName());
            Glide.with(this)
                    .load(Config.BASE_PIC_URL + APP.user.getHeadPicture())
                    .error(R.drawable.ic_no_head)
                    .into(imv_header);
        }else if (msg.getMsgType() == Msg.CHECK_EXAMINE){
            // 这里是接收了tcp消息之后，再请求待处理列表
            mPresenter.appTodoList();// 请求待处理的列表
        }else if (msg.getMsgType() == Msg.SWITCH_HOUSE) { // 切换房子数据
            gethouUser();
        }
    }

    @Override
    public void appPushMessageTipCountSuccess(ResAppPushMessageTipCount resAppPushMessageTipCount) {

        systemNotify = resAppPushMessageTipCount.getSystem_notify().getNum();
        messageNotify = resAppPushMessageTipCount.getTotalCount();
        totalCount = resAppPushMessageTipCount.getTotalCount();

        if (messageNotify>0 && messageNotify<= 99){
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText(resAppPushMessageTipCount.getTotalCount()+"");
            EventBus.getDefault().post(new EventBusUIMessage(Msg.FRAGMENT_MY_MESSAGE_TOTAL,String.valueOf(totalCount)));
        }else if( messageNotify >99){
            tv_count.setVisibility(View.VISIBLE);
            EventBus.getDefault().post(new EventBusUIMessage(Msg.FRAGMENT_MY_MESSAGE_TOTAL,"99"));
            tv_count.setText("99");
        }else {
            EventBus.getDefault().post(new EventBusUIMessage(Msg.FRAGMENT_MY_MESSAGE_TOTAL,"0"));
            tv_count.setVisibility(View.GONE);
        }

       /* if (resAppPushMessageTipCount.getHouse_switch_manager_assign().getNum()>0) {

        }*/



    }

    @Override
    public void appPushMessageTipCountFali(String msg) {
//        showToast(msg);
    }

    @Override
    public void appTodoListSuccess(ResAppTodoList resAppTodoList) {
        /**待处理转让权消息**/
        if (resAppTodoList.getSwitchMasterTodo()!=null && resAppTodoList.getSwitchMasterTodo().size()>1) {
            // 跳转列表界面处理
            showCheckDialog("你有"+resAppTodoList.getSwitchMasterTodo().size()+"条业主权限转让待处理",resAppTodoList.getSwitchMasterTodo());
        }else if (resAppTodoList.getSwitchMasterTodo()!=null && resAppTodoList.getSwitchMasterTodo().size()==1){
            // 显示弹窗直接处理
            houseManagerSwitchAssignDialog(resAppTodoList.getSwitchMasterTodo().get(0).getTodoMsg(),resAppTodoList.getSwitchMasterTodo().get(0).getHouseId());
        }
        /**待处理审核成员消息**/
        if (resAppTodoList.getMemberApplyTodo() !=null  && resAppTodoList.getMemberApplyTodo().size()>0) {
            showMemberApplyTodoDialog("你有"+"待审核成员消息处理",resAppTodoList.getMemberApplyTodo());
        }
    }
    @Override
    public void appTodoListFali(String msg) {
//        showToast(msg);
    }

    @Override
    public void setPresenter(FragmentMyContract.Presenter presenter) {

    }


    @Override
    public void getApplyBindHouseListSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList) {
        if (resGetApplyBindHouseList.getWaiting() > 0) {
            tv_count_apply.setVisibility(View.VISIBLE);
            tv_count_apply.setText(resGetApplyBindHouseList.getWaiting()+"");
        } else {
            tv_count_apply.setVisibility(View.GONE);
        }
    }

    @Override
    public void getApplyBindHouseListFail(String msg) {
        tv_count_apply.setVisibility(View.GONE);
    }


    /**
     * 收到房子审核成员消息处理
     * @param content
     */
    public  void showMemberApplyTodoDialog(String content, final List<ResAppTodoList.MemberApplyTodoBean> todoList){
        final MyDialogUtil dialog = new MyDialogUtil(getActivity()) {
            @Override
            public void confirm() {
                String houseId = "";
                for (int i = 0; i < todoList.size(); i++) {
                    houseId += todoList.get(i).getHouseId()+";";
                }
                Intent intent  = new Intent(getActivity(),ApplyBindHouseListActivity.class);
                intent.putExtra("type","MESSAGE");
                intent.putExtra("houseId",houseId);
                startActivity(intent);
            }
        };
        dialog.showPerfectTipDialog("提示",content,"取消","查看");
    }
    /**
     * 收到房子转让处理消息（多条）
     * @param content
     */
    public  void showCheckDialog(String content, final List<ResAppTodoList.SwitchMasterTodoBean> todoList){
        final MyDialogUtil dialog = new MyDialogUtil(getActivity()) {
            @Override
            public void confirm() {
                Intent intent  = new Intent(getActivity(),AppTodoListActivity.class);
                intent.putExtra("todoList",(Serializable)todoList);
                startActivity(intent);
            }
        };
        dialog.showPerfectTipDialog("提示",content,"取消","查看");
    }

    /**
     *
     * 收到房子转让处理消息(单条)
     * @param content
     */
    private Dialog dialog;
    public  void houseManagerSwitchAssignDialog(String content, final String houseId){
        if (dialog==null) {
            dialog = new Dialog(getActivity(), R.style.Alert_Dialog_Style);
            TextView tv_title;
            TextView tv_content;
            TextView tv_confirm = null;
            TextView tv_cancel;
            View view = null;

            view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sure_again, null);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
            tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

            tv_title.setText("提示");
            tv_content.setText(content);
            tv_cancel.setText("拒绝");
            tv_confirm.setText("同意");
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    houseManagerSwitchAssign(houseId,"0"); // accepted 0-拒绝，1-同意
                    dialog.dismiss();
                }
            });
            tv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    houseManagerSwitchAssign(houseId,"1"); // accepted 0-拒绝，1-同意
                    dialog.dismiss();
                }
            });
            dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                    (int) (APP.screenWidth * 0.7),
                    android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
            // 设置显示宽高
            dialog.show();
            // 设置点击外围解散
            dialog.setCanceledOnTouchOutside(false);
        }else {
            dialog.show();
        }

    }

    // 请求处理业主确认转让权限
    private void houseManagerSwitchAssign(String houseId, String accepted) {
        final ReqHouseManagerSwitchHandle request = new ReqHouseManagerSwitchHandle();
        request.setHouseId(houseId);
        request.setAccepted(accepted);
        RetrofitUtil.getInstance().getmApiService()
                .houseManagerSwitchHandle(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        showToast(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse response) {
//                        String content = new Gson().toJson(response);
//                        showToast(response.getMsg());
                    }
                });
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
                    //用户点击了接受，可以进行相应处理
                    Intent intent = new Intent(getActivity(), SecondActivity.class);
                    startActivity(intent);
                } else {
                    //用户点击了拒绝，可以进行相应处理
                    showToast(getString(R.string.qrcode_cametra_error));
                }
            }
        }
    }

    private List<HouseUser> mUsers;
    boolean isOwner = false;
    public void gethouUser() {
       String  mHouseId = (String) SharedPreferencesUtils.getParam(getActivity(), "houseId", "");
        LogUtil.i("=========================获取家庭成员:"+mHouseId);
        RetrofitUtil.getInstance().getmApiService()
                .getHouseUserList(new ReqGetHouseUserList(mHouseId).toJsonStr())
                .compose(new APITransFormer<ResGetHouseUserList>())
                .subscribe(new APISubcriber<ResGetHouseUserList>() {
                    @Override
                    public void onFail(ResGetHouseUserList baseResponse,String msg) {
                    }
                    @Override
                    public void onSuccess(ResGetHouseUserList response) {
                        mUsers = response.getHouseUserList();
                        Iterator<HouseUser> iterator = mUsers.iterator();
                        while (iterator.hasNext()) {
                            HouseUser user = iterator.next();
                            if (user.getBindType().equals("0")) {
                                if (user.getUserId().equals(APP.user.getUserId())) { // 判断管理员的userId 与自己id一样，显示 操作
                                    isOwner = true;
                                } else {
                                    isOwner = false;
                                }
                                break;
                            }
                        }
                        if (isOwner){
                            tv_isowen.setText("户主");
                        }else {
                            tv_isowen.setText("家庭用户");
                        }
                    }
                });

    }
}
