package com.mxkj.econtrol.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.bean.response.ResGetUserCommunity;
import com.mxkj.econtrol.bean.response.UserCommunity;
import com.mxkj.econtrol.contract.UserCommunityContract;
import com.mxkj.econtrol.model.UserCommunityModel;
import com.mxkj.econtrol.presenter.UserCommunityPresenter;
import com.mxkj.econtrol.ui.adapter.UserCommunityAdapter;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.hwang.widgets.SmartPullableLayout;

public class UserCommunityActivity extends BaseActivity implements UserCommunityContract.View {

    private RecyclerView mRecyclerUserCommunity;
    private ImageView imvBack;
    private SmartPullableLayout smartPullableLayout;
    private int page = 0;
    private boolean isLastPage;
    private UserCommunityContract.Presenter mPresenter;
    private CommunityContent.User mUser;
    private List<UserCommunity> mUserCommunities;
    private UserCommunityAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_community);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        imvBack = findView(R.id.imv_back);
        mRecyclerUserCommunity = findView(R.id.recycle_user_community);
        smartPullableLayout = findView(R.id.smpl_community);
        mRecyclerUserCommunity.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        mUserCommunities = new ArrayList<>(0);
        //添加第一条默认数据
        mUserCommunities.add(0, new UserCommunity());
        mUser = (CommunityContent.User) getIntent().getSerializableExtra("user");
        mPresenter = new UserCommunityPresenter(this, new UserCommunityModel());
        mPresenter.getUserCommunity(page);
    }

    @Override
    protected void initListener() {
        smartPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
            @Override
            public void onPullDown() {
                page = 0;
                mUserCommunities.clear();
                //添加第一条默认数据
                mUserCommunities.add(0, new UserCommunity());
                mPresenter.getUserCommunity(page);

            }

            @Override
            public void onPullUp() {
                if (isLastPage) {
                    //如果已经是最后一页
                    showToast("已经到底了，没有更多了！");
                    smartPullableLayout.stopPullBehavior();
                    return;

                }
                page++;
                mPresenter.getUserCommunity(page);
            }
        });
        findView(R.id.imv_back).setOnClickListener(this);
    }


    /**
     * @Desc: 将同一天的社区放一起
     * @author:liangshan
     */
    private List<UserCommunity> getUserCommunities(List<CommunityContent> communityContents) {
        Map<String, List<CommunityContent>> maps = new LinkedHashMap<String, List<CommunityContent>>();
        for (CommunityContent content : communityContents) {
            String date = DateTimeUtil.getDate(Long.valueOf(content.getUpdateTime()));
            LogUtil.e(date + "-----------");
            if (maps.containsKey(date)) {
                maps.get(date).add(content);
            } else {
                List<CommunityContent> contentList = new ArrayList<>();
                contentList.add(content);
                maps.put(date, contentList);
            }
        }
        List<UserCommunity> result = new ArrayList<>();
        for (String key : maps.keySet()) {
            LogUtil.e(key + "-----------++");
            UserCommunity userCommunity = new UserCommunity();
            String today = DateTimeUtil.getDate(System.currentTimeMillis());
            String yesterday = DateTimeUtil.getDate(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            if (key.equals(today)) {
                userCommunity.setTime("今天");
            } else if (yesterday.equals(key)) {
                userCommunity.setTime("昨天");
            } else {
                userCommunity.setTime(key.substring(5, 10));
            }
            userCommunity.setCommunityContentList(maps.get(key));
            result.add(userCommunity);
        }
        return result;
    }

    @Override
    public void setPresenter(UserCommunityContract.Presenter presenter) {

    }

    @Override
    public void getUserCommunitySuccess(ResGetUserCommunity resGetUserCommunity) {
        smartPullableLayout.stopPullBehavior();
        List<CommunityContent> content = resGetUserCommunity.getPage().getContent();
        isLastPage = resGetUserCommunity.getPage().isLastPage();
        List<UserCommunity> userCommunities = getUserCommunities(content);
        if (mUserCommunities.size() > 2) {
            if (mUserCommunities.get(mUserCommunities.size() - 1).getTime().equals(userCommunities.get(0).getTime())) {
                mUserCommunities.get(mUserCommunities.size() - 1).getCommunityContentList().addAll(userCommunities.get(0).getCommunityContentList());
                userCommunities.remove(0);
            }
        }
        mUserCommunities.addAll(userCommunities);
        if (mAdapter == null) {
            mAdapter = new UserCommunityAdapter(mUserCommunities, R.layout.layout_user_community_item, mUser);
            mRecyclerUserCommunity.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getUserCommunityFail(String msg) {
        showToast(msg);
    }

    @Override
    public String getUserId() {
        return mUser.getId();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.imv_back) {
            finish();
        }
    }
}
