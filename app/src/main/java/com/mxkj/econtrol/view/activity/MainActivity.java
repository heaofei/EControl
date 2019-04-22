package com.mxkj.econtrol.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.EventBusMessage;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.MyHouse;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.contract.MainContract;
import com.mxkj.econtrol.model.MainModel;
import com.mxkj.econtrol.presenter.MainPresenter;
import com.mxkj.econtrol.ui.activity.FeedBackActivity;
import com.mxkj.econtrol.ui.activity.HouseControlLogActivity;
import com.mxkj.econtrol.ui.activity.SettingActivity;
import com.mxkj.econtrol.ui.activity.WebViewActivity;
import com.mxkj.econtrol.ui.adapter.MyHouseListAdapter;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.widget.TipDialog;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;


/**
 * 登录成功后的主页
 */
public class MainActivity extends BaseActivity implements MainContract.View {
    private LinearLayout mLLShop, mLLLianJia, mLLShare, mLLHelp, mLLHistory, mLLSetting;
    private RecyclerView mRvHouseList;
    private MyHouseListAdapter myHouseListAdapter;
    private MainContract.Presenter mPresenter;
    private TextView mTvUserName;
    private ImageView mUserHeader;
    private ImageView imvModifyMood;
    private EditText mEditMood;
    private TextView mMoodTime;
    private RelativeLayout mRlMood;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        //设置状态栏颜色
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(0xffff695a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //从sceneActivity 回来，则关闭还没关闭的houseMainActivity
        EventBusUIMessage eventBusMessage = new EventBusUIMessage(Msg.CLOSE_HOUSE_MAIN_ACTIVITY);
        EventBus.getDefault().post(eventBusMessage);
    }


    @Override
    protected void initView() {
        mLLHelp = findView(R.id.ll_helpe);
        mLLHistory = findView(R.id.ll_history);
        mLLSetting = findView(R.id.ll_setting);
        mLLShop = findView(R.id.ll_shop);
        mLLLianJia = findView(R.id.ll_lianjia);
        mLLShare = findView(R.id.ll_share);
        mTvUserName = findView(R.id.tv_user_name);
        mRvHouseList = findView(R.id.rv_houses);
        mUserHeader = findView(R.id.imv_header_pic);
        imvModifyMood = findView(R.id.imv_modify_mood);
        mEditMood = findView(R.id.edit_mood);
        mRlMood = findView(R.id.rl_mood);
        mMoodTime = findView(R.id.tv_mood_time);
        swipeRefreshLayout = findView(R.id.swiperefresh);
        mEditMood.setEnabled(false);

    }

    @Override
    protected void initData() {
        mPresenter = new MainPresenter(this, new MainModel());
        mRvHouseList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (APP.user == null) {
            showToast("未登录，请登录！");
            startToActivity(FirstPageActivity.class);
            finish();
            return;
        }
        mPresenter.getHousingEstateList();
        mTvUserName.setText(APP.user.getNiceName());
        if (!TextUtils.isEmpty(APP.user.getMoon())) {
            mEditMood.setText(APP.user.getMoon());
            mMoodTime.setText(APP.user.getMoonTime());
        }
        Glide.with(this).load(Config.BASE_PIC_URL + APP.user.getHeadPicture())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_no_head).into(mUserHeader);
    }

    @Override
    protected void initListener() {
        mLLHelp.setOnClickListener(this);
        mLLHistory.setOnClickListener(this);
        mLLSetting.setOnClickListener(this);
        mLLShop.setOnClickListener(this);
        mLLShare.setOnClickListener(this);
        mLLLianJia.setOnClickListener(this);
        mUserHeader.setOnClickListener(this);
        imvModifyMood.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHousingEstateList();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_helpe:
                startToActivity(FeedBackActivity.class);
                break;
            case R.id.ll_history:
                startToActivity(HouseControlLogActivity.class);
                break;
            case R.id.ll_setting:
                startToActivity(SettingActivity.class);
                break;
            case R.id.ll_shop:
//                startToActivity(StoreActivity.class);
                Intent intent01 = new Intent(this, WebViewActivity.class);
                intent01.putExtra("type", 0);
                intent01.putExtra("position", 0);
                intent01.putExtra("url", "https://image.baidu.com/search/wiseala?tn=wiseala&ie=utf8&fmpage=search&dulisearch=1&pos=tagclick&word=家居手机壁纸%20竖屏&oriquery=家居手机壁纸 ");
                intent01.putExtra("title", "壁纸");
                startToActivity(intent01);
                break;
            case R.id.ll_lianjia:
                Intent intent0 = new Intent(this, WebViewActivity.class);
                intent0.putExtra("url", "http://gz.lianjia.com/");
                intent0.putExtra("title", "链家");
                intent0.putExtra("type", 0);
                intent0.putExtra("position", 2);
                startToActivity(intent0);
                break;
            case R.id.ll_share:
                startToActivity(PublicCommunityActivity.class);
                break;
            case R.id.imv_header_pic:
                startToActivity(UserInfoModifyActivity.class);
                break;
            case R.id.imv_modify_mood:
                if (mEditMood.isEnabled()) {
                    mPresenter.userMoodModify(mEditMood.getText() + "");
                    imvModifyMood.setImageResource(R.drawable.ic_modify);
                    mRlMood.setBackgroundResource(R.drawable.mood_bg);
                    mEditMood.setEnabled(false);
                } else {
                    mEditMood.setEnabled(true);
                    mEditMood.setFocusable(true);
                    mEditMood.setCursorVisible(true);
                    mRlMood.setBackgroundResource(R.drawable.mood_bg_focus);
                    mEditMood.setSelection(mEditMood.getText().length());
                    InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    imvModifyMood.setImageResource(R.drawable.ic_edit_active);

                }

                break;
        }
    }


    @Override
    protected void handleMessage(EventBusMessage msg) {
        super.handleMessage(msg);
        switch (msg.getMsgType()) {
            case Msg.CHECK_RESULT:
                //审核了申请，刷新界面
                if (isForeground)
                    mPresenter.getHousingEstateList();
                break;
        }

    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void getHousingEstateListSuccess(final ResGetUserHouseList resGetUserHouseList) {
        resGetUserHouseList.getHouseList().add(0, new MyHouse());
        myHouseListAdapter = new MyHouseListAdapter(this,resGetUserHouseList.getHouseList(), R.layout.layout_my_house_item,this);
        mRvHouseList.setAdapter(myHouseListAdapter);
        myHouseListAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    //点击我的家不做处理
                    return;
                }
                if (position == resGetUserHouseList.getHouseList().size() - 1) {
                    startToActivity(NewHouseActivity.class);
                } else {
                    if (resGetUserHouseList.getHouseList().get(position).getState().equals("1")) {
                        //已经审核通过了的房子才能进入
                        Intent intent = new Intent(MainActivity.this, HouseMainActivity.class);
                        intent.putExtra("houseId", resGetUserHouseList.getHouseList().get(position).getId());
                        startToActivity(intent);
                        finish();
                    } else if (resGetUserHouseList.getHouseList().get(position).getState().equals("0")) {
                        showToast("请等待业主审核！");
                    } else if (resGetUserHouseList.getHouseList().get(position).getState().equals("2")) {
                        showRejectTip();
                        mPresenter.unbindHouseUser4Reject(resGetUserHouseList.getHouseList().get(position).getHouseUserId());
                    }
                }
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showRejectTip() {
        TipDialog tipDialog = new TipDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tipDialog.setCancelVisible(View.GONE);
        tipDialog.setContent(getString(R.string.reject_tip));
        tipDialog.show();
    }


    @Override
    public void getHousingEstateListFail(String msg) {
        showToast("获取我的家列表失败");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void userMoodModifySuccess() {
        showToast(getString(R.string.modify_mood_success));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mMoodTime.setText(DateTimeUtil.getCurrentTimeString(simpleDateFormat));
        APP.user.setMoon(mEditMood.getText().toString());
        APP.user.setMoonTime(mMoodTime.getText().toString());
    }

    @Override
    public void userMoodModifyFail(String msg) {
        showToast(msg);
    }

    @Override
    public void unbindHouseUser4RejectSuccess() {
        mPresenter.getHousingEstateList();
    }

    @Override
    public void unbindHouseUser4RejectFail(String msg) {
        showToast(msg);
    }
}
