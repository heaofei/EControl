package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.contract.PublicCommunityContract;
import com.mxkj.econtrol.contract.UserCommunityContract;
import com.mxkj.econtrol.model.PublicCommunityModel;
import com.mxkj.econtrol.presenter.PublicCommunityPresenter;
import com.mxkj.econtrol.ui.activity.BrowsePicActivity;
import com.mxkj.econtrol.ui.activity.CommentActivity;
import com.mxkj.econtrol.ui.activity.SendCommunityActivity;
import com.mxkj.econtrol.ui.activity.WebViewActivity;
import com.mxkj.econtrol.ui.adapter.CommunityAdapter;
import com.mxkj.econtrol.ui.adapter.UserCommunityAdapter;
import com.mxkj.econtrol.widget.TipDialog;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;

/**
 * 社区 （旧）
 */

public class PublicCommunityActivity extends BaseActivity implements PublicCommunityContract.View, CommonAdapter.OnItemClickListener {

    private PublicCommunityContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private CommunityAdapter mAdapter;
    private List<CommunityContent> mDatas;
    private Button mBtnAdd;
    private int page = 0;
    private LinearLayout mLlStore;
    private LinearLayout mLlLianjia;
    private LinearLayout mLlMain;
    //下拉刷新
    private SmartPullableLayout mSmartPullableLayout;
    private boolean isLastPage;
    private ImageView mImvUserPic;
    private CommunityContent communityContentStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_public_community);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        mRecyclerView = findView(R.id.recycle_public_community);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBtnAdd = findView(R.id.btn_add);
        mSmartPullableLayout = findView(R.id.smpl_community);
        mLlStore = findView(R.id.ll_bottom_left);
        mLlLianjia = findView(R.id.ll_bottom_right);
        mLlMain = findView(R.id.ll_bottom_midle);
        mImvUserPic = findView(R.id.user_pic);
        if (APP.user == null) {
            mBtnAdd.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(Config.BASE_PIC_URL + APP.user.getHeadPicture())
                    .error(R.drawable.ic_no_head)
                    .into(mImvUserPic);
        }
    }


    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        mPresenter = new PublicCommunityPresenter(this, new PublicCommunityModel());
        mPresenter.getCommunityList(page);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initListener() {
        mBtnAdd.setOnClickListener(this);
        mSmartPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
            @Override
            public void onPullDown() {
                page = 0;
                mDatas.clear();
                mPresenter.getCommunityList(page);
            }

            @Override
            public void onPullUp() {
                if (isLastPage) {
                    //如果已经是最后一页
                    showToast("已经到底了，没有更多了！");
                    mSmartPullableLayout.stopPullBehavior();
                    return;

                }
                page++;
                mPresenter.getCommunityList(page);
            }
        });
        mLlStore.setOnClickListener(this);
        mLlLianjia.setOnClickListener(this);
        mLlMain.setOnClickListener(this);
        mImvUserPic.setOnClickListener(this);
    }


    @Override
    public void setPresenter(PublicCommunityContract.Presenter presenter) {

    }


    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void getCommunityListSuccess(ResGetCommunityList resGetCommunityList) {
        isLastPage = resGetCommunityList.getPage().isLastPage();
        mDatas.addAll(resGetCommunityList.getPage().getContent());
        if (mAdapter == null) {
            mAdapter = new CommunityAdapter(mDatas, R.layout.layout_community_item, this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(this);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mSmartPullableLayout.stopPullBehavior();
    }

    @Override
    public void getCommunityListFail(String msg) {
        showToast(msg);
        mSmartPullableLayout.stopPullBehavior();
    }

    @Override
    public void tumpUpSuccess() {
        if (communityContentStart != null) {
            if (communityContentStart.getHaveThumbUp() == 1) {
                communityContentStart.setHaveThumbUp(0);
                communityContentStart.setThumbUpCount(communityContentStart.getThumbUpCount() - 1);
            } else {
                communityContentStart.setHaveThumbUp(1);
                communityContentStart.setThumbUpCount(communityContentStart.getThumbUpCount() + 1);
            }

        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void tumpUpFail() {

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, PublicCommunityDetailActivity.class);
        intent.putExtra("communityContent", mDatas.get(position));
        startToActivity(intent);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_add:
                if (APP.user != null) {
                    //已登录
                    Intent intent = new Intent(this, SendCommunityActivity.class);
                    startActivityForResult(intent, 1);
                }

                break;
            case R.id.ll_bottom_left:
                Intent intent01 = new Intent(this, WebViewActivity.class);
                intent01.putExtra("type", 0);
                intent01.putExtra("position", 0);
                intent01.putExtra("url", "https://image.baidu.com/search/wiseala?tn=wiseala&ie=utf8&fmpage=search&dulisearch=1&pos=tagclick&word=家居手机壁纸%20竖屏&oriquery=家居手机壁纸 ");
                intent01.putExtra("title", "壁纸");
                startToActivity(intent01);
                finish();
                break;
            case R.id.ll_bottom_right:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", "http://gz.lianjia.com/");
                intent.putExtra("type", 0);
                intent.putExtra("position", 2);
                intent.putExtra("title", "链家");
                startToActivity(intent);
                finish();
                break;
            case R.id.ll_bottom_midle:
//                if (APP.user != null) {
//                    //已经登录的
//                    startToActivity(MainActivity.class);
//                    finish();
//
//                } else {
//                    startToActivity(LoginActivity.class);
//                }
//                finish();
                break;
            case R.id.user_pic:
                if (APP.user != null) {
                    //已经登录的
                    startToActivity(MainActivity.class);
                    finish();
                } else {
                    startToActivity(LoginActivity.class);
                }
                break;

            case R.id.imv_start:
                if (APP.user != null) {
                    //已登录
                    communityContentStart = (CommunityContent) v.getTag();
                    mPresenter.publicCommunityThumbUp(communityContentStart.getId());
                } else {
                    showTip();
                }
                break;
            case R.id.imv_community_pic:
                CommunityContent communityContent = (CommunityContent) v.getTag();
                Intent picDetail = new Intent(this, BrowsePicActivity.class);
                picDetail.putExtra("picUrl", communityContent.getPic());
                startActivity(picDetail);
                break;
            case R.id.imv_header_pic:
                CommunityContent.User user = (CommunityContent.User) v.getTag(R.id.imv_header_pic);
                Intent userCommunityIntent = new Intent(this, UserCommunityActivity.class);
                userCommunityIntent.putExtra("user", user);
                startToActivity(userCommunityIntent);
                break;


        }
    }

    public void showTip() {
        TipDialog tipDialog = new TipDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToActivity(LoginActivity.class);

            }
        });

        tipDialog.setContent("该功能要登录才能操作，您要登录吗？");
        tipDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            mDatas.clear();
            page = 0;
            mPresenter.getCommunityList(page);
        }
    }
}
