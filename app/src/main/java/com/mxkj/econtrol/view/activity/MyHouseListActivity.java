package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.MyHouse;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResSetDefaultUserHouse;
import com.mxkj.econtrol.bean.response.TcpResHouseRespones;
import com.mxkj.econtrol.contract.MyHoursListContract;
import com.mxkj.econtrol.model.MyHoursListModel;
import com.mxkj.econtrol.presenter.MyHoursPresenter;
import com.mxkj.econtrol.ui.activity.QRCodeActivity;
import com.mxkj.econtrol.ui.adapter.MyHouseListAdapter;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.mxkj.econtrol.widget.MyLinearLayout;
import com.mxkj.econtrol.widget.SuperSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 我的住宅列表
 */
public class MyHouseListActivity extends BaseActivity implements MyHoursListContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private RelativeLayout rl_add_hours;
    private RecyclerView recycle_myhours_list;
    private MyLinearLayout ll_status;
    private MyHouseListAdapter myHouseListAdapter;
    private List<MyHouse> userHourList = new ArrayList();

    private MyHoursListContract.Presenter mPresenter;
    private String setDefaultHouseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_house_list);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);

        tv_title_content.setText(getString(R.string.my_house));
        tv_title_right.setText(getString(R.string.fragment_sences_edit02));

        ll_status = findView(R.id.ll_status);
        rl_add_hours = findView(R.id.rl_add_hours);
        recycle_myhours_list = findView(R.id.recycle_myhours_list);
        recycle_myhours_list.setLayoutManager(new LinearLayoutManager(this));

        mPresenter = new MyHoursPresenter(this, new MyHoursListModel());
        mPresenter.getHousingEstateList();

    }

    @Override
    protected void initData() {
        initRefreshLayou(); // 初始化刷新
    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        rl_add_hours.setOnClickListener(this);
    }


    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private void initRefreshLayou() {
        swipeRefreshLayout = (SuperSwipeRefreshLayout)findViewById(R.id.superSwipeRefreshLayout);
        View child = LayoutInflater.from(swipeRefreshLayout.getContext()).inflate(R.layout.layout_pulltorefresh_head, null);
        progressBar = (ProgressBar) child.findViewById(R.id.pb_view);
        textView = (TextView) child.findViewById(R.id.text_view);
        textView.setText(getString(R.string.put_refresh));
        textView.setTextColor(getResources().getColor(R.color.text_black_999));
        imageView = (ImageView) child.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.ic_message_put_loading);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setHeaderView(child);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.getHousingEstateList();
                        textView.setText(getString(R.string.refresh));
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        System.out.println("debug:onRefresh");
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                                System.out.println("debug:stopRefresh");
                            }
                        }, 2000);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        System.out.println("debug:distance = " + distance);
                        //myAdapter.updateHeaderHeight(distance);
                    }
                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? getString(R.string.release_refresh) : getString(R.string.put_refresh));
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.rl_add_hours:
                startActivity(new Intent(this, NewHouseActivity.class));
                break;
            case R.id.tv_title_right:
                if (tv_title_right.getText().toString().trim().equals(getString(R.string.fragment_sences_edit02))) {
                    myHouseListAdapter.upDateAdapter(userHourList, true);
                    tv_title_right.setText(getString(R.string.finish));
                } else if (tv_title_right.getText().toString().trim().equals(getString(R.string.finish))) {
                    myHouseListAdapter.upDateAdapter(userHourList, false);
                    tv_title_right.setText(getString(R.string.fragment_sences_edit02));
                }
                break;
            case R.id.ll_isdefaul:        // 设置默认房子
                MyHouse myHouse = (MyHouse) v.getTag();
                setDefaultHouseId = myHouse.getId();
                if (myHouse.getIsDefault()==0){ // 0不是默认房子
                    mPresenter.setDefaultUserHouse();
                }
                break;
            case R.id.ll_change:        // 修改审核失败资料
                startActivity(new Intent(this,NewHouseActivity.class));
                break;
            case R.id.rl_code:        // 查看二维码
                MyHouse myHouse02 = (MyHouse) v.getTag();
                Intent intent = new Intent(this, QRCodeActivity.class);
                intent.putExtra("houseId", myHouse02.getId());
                intent.putExtra("housingEstate", myHouse02.getHousingEstate());
                intent.putExtra("building", myHouse02.getBuilding());
                intent.putExtra("houseNo", myHouse02.getHouseNo());
                startActivity(intent);

                break;
            case R.id.iv_delete:
                showDeliteHouseDialog((MyHouse) v.getTag());
                break;
        }
    }
    private void showDeliteHouseDialog(final MyHouse myHouse) {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                mPresenter.unbindHouseUser4Reject(myHouse.getHouseUserId());
            }
        };
        dialog.showTipDialog(getString(R.string.my_house_delete),getString(R.string.my_house_delete_tips));
    }

    @Override
    public void getHousingEstateListSuccess(final ResGetUserHouseList resGetUserHouseList) {
        rl_add_hours.setVisibility(View.VISIBLE);
        String content = new Gson().toJson(resGetUserHouseList);
        userHourList = resGetUserHouseList.getHouseList();
        myHouseListAdapter = new MyHouseListAdapter(this,userHourList, R.layout.layout_my_house_item, this);
        recycle_myhours_list.setAdapter(myHouseListAdapter);
        if (userHourList.size() > 0) {
            ll_status.showData();
        } else {
            tv_title_right.setVisibility(View.INVISIBLE);
            ll_status.setImage(R.drawable.ic_no_house);
            ll_status.setText(getString(R.string.my_house_nohouse));
            View view = ll_status.showEmpty(R.layout.custom_empty_view);
            Button btn_add = (Button) view.findViewById(R.id.btn_add);
            btn_add.setVisibility(View.GONE);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyHouseListActivity.this, NewHouseActivity.class));
                }
            });
        }
    }

    @Override
    public void getHousingEstateListFail(String msg) {
        rl_add_hours.setVisibility(View.GONE);
        ll_status.showError(R.layout.custom_error_view);
    }
    /*****设置默认房子成功**/
    @Override
    public void setDefaultUserHouseSuccess(ResSetDefaultUserHouse resSetDefaultUserHouse) {
        String content = new Gson().toJson(resSetDefaultUserHouse);
        SharedPreferencesUtils.setParam(APP.getInstance(), "houseId",setDefaultHouseId);// 当前的房子id
        APP.user.setDefaultHouseId(setDefaultHouseId); // 默认的房子id
        mPresenter.getHousingEstateList();


        EventBusUIMessage message = new EventBusUIMessage();
        message.setMsgType(Msg.EVENBUS_HOUSE_MESSAGE_5554);
        EventBus.getDefault().post(message); // 发送evenbus

    }
    /*****设置默认房子失败**/
    @Override
    public void setDefaultUserHouseFail(String msg) {
        showToast(msg);
    }

    @Override
    public String getHouseId() {
        return setDefaultHouseId;
    }
    /*****删除房子成功**/
    @Override
    public void unbindHouseUser4RejectSuccess() {
        mPresenter.getHousingEstateList();

        EventBusUIMessage message = new EventBusUIMessage();
        message.setMsgType(Msg.EVENBUS_HOUSE_MESSAGE_5553);
        EventBus.getDefault().post(message); // 发送evenbus

        tv_title_right.setText(getString(R.string.fragment_sences_edit02));

    }
    /*****删除房子失败**/
    @Override
    public void unbindHouseUser4RejectFail(String msg) {
        showToast(msg);
    }

    @Override
    public void setPresenter(MyHoursListContract.Presenter presenter) {

    }


}
