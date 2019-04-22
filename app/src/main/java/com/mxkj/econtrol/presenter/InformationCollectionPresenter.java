package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.request.ReqGetUserCommunity;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.contract.InformationCollectionContract;
import com.mxkj.econtrol.contract.InformationCommentContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class InformationCollectionPresenter implements InformationCollectionContract.Presenter {
    private InformationCollectionContract.Model mModel;
    private InformationCollectionContract.View mView;

    public InformationCollectionPresenter(InformationCollectionContract.View mView, InformationCollectionContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    /**
     * 获取我的收藏列表
     * @param page
     * @param rows
     * @param userId
     */
    @Override
    public void getCommunityCollectionList(int page, int rows, String userId) {
        ReqGetUserCommunity reqGetUserCommunity = new ReqGetUserCommunity();
        reqGetUserCommunity.setPage(page);
        reqGetUserCommunity.setRows(rows);
        reqGetUserCommunity.setUserId(userId);

        mModel.getCommunityCollectionList(reqGetUserCommunity)
                .subscribe(new APISubcriber<ResGetCommunityList>() {
                    @Override
                    public void onFail(ResGetCommunityList baseResponse,String msg) {
                        mView.getCommunityCollectionListFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetCommunityList resGetCommunityList) {
                        mView.getCommunityCollectionListSuccess(resGetCommunityList);
                    }
                });
    }



    /**
     * // 收藏
     * @param communityId
     * @param userId
     */
    @Override
    public void publicCommunityCollectionUp(String communityId, String userId) {
        ReqPublicCommunityUser reqPublicCommunityUser = new ReqPublicCommunityUser();
        reqPublicCommunityUser.setCommunityId(communityId);
        reqPublicCommunityUser.setUserId(userId);

        mModel.publicCommunityCollectionUp(reqPublicCommunityUser)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse resGetNewsDetail, String msg) {
                        mView.publicCommunityCollectionUpFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse resGetNewsDetail) {
                        mView.publicCommunityCollectionUpSuccess(resGetNewsDetail);
                    }
                });
    }


    /**
     * 点赞 （对文章）
     * @param communityId
     * @param userId
     */
    @Override
    public void publicCommunityThumbUp(String communityId, String userId) {
        ReqPublicCommunityUser reqPublicCommunityUser = new ReqPublicCommunityUser();
        reqPublicCommunityUser.setCommunityId(communityId);
        reqPublicCommunityUser.setUserId(userId);

        mModel.publicCommunityThumbUp(reqPublicCommunityUser)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse resGetNewsDetail, String msg) {
                        mView.publicCommunityThumbUpFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse resGetNewsDetail) {
                        mView.publicCommunityThumbUpSuccess(resGetNewsDetail);
                    }
                });
    }


}
