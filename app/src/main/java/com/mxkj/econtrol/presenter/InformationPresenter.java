package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqGetRankingDateList;
import com.mxkj.econtrol.bean.request.ReqGetTimeRanking;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;
import com.mxkj.econtrol.contract.DeviceUseNumberContract;
import com.mxkj.econtrol.contract.InformationContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class InformationPresenter implements InformationContract.Presenter {
    private InformationContract.Model mModel;
    private InformationContract.View mView;

    public InformationPresenter(InformationContract.View mView, InformationContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void getCommunityList(int page, int rows) {
        ReqGetCommunityList reqGetCommunityList = new ReqGetCommunityList(page,rows);
        if (APP.isLogin) {
          reqGetCommunityList.setUserId(APP.user.getUserId());
        }else {
            reqGetCommunityList.setUserId("");
        }

        APISubcriber<ResGetCommunityList> apiSubcriber = new APISubcriber<ResGetCommunityList>() {
            @Override
            public void onFail(ResGetCommunityList resGetCommunityList, String msg) {
                mView.getCommunityListFali(msg);
            }
            @Override
            public void onSuccess(ResGetCommunityList resGetCommunityList) {
                mView.getCommunityListSuccess(resGetCommunityList);
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.getCommunityList(reqGetCommunityList)
                .subscribe(apiSubcriber);
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
