package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteCommunity;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunity;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityThumbUp;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.contract.PublicCommunityContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description：
 */

public class PublicCommunityPresenter implements PublicCommunityContract.Presenter {

    private PublicCommunityContract.View mView;
    private PublicCommunityContract.Model mModel;

    public PublicCommunityPresenter(PublicCommunityContract.View mView, PublicCommunityContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getCommunityList(int page) {
        final ReqGetCommunityList reqGetCommunityList = new ReqGetCommunityList(page, 6);
        mModel.getCommunityList(reqGetCommunityList)
                .subscribe(new APISubcriber<ResGetCommunityList>() {
                    @Override
                    public void onFail(ResGetCommunityList baseResponse,String msg) {
                        mView.getCommunityListFail(msg);
                    }

                    @Override
                    public void onSuccess(ResGetCommunityList resGetCommunityList) {
                        mView.getCommunityListSuccess(resGetCommunityList);
                    }
                });
    }


    @Override
    public void publicCommunity() {
        ReqPublicCommunity reqPublicCommunity = new ReqPublicCommunity();
        mModel.publicCommunity(reqPublicCommunity).subscribe(new APISubcriber<BaseResponse>() {
            @Override
            public void onFail(BaseResponse baseResponse,String msg) {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void deleteCommunity(String id) {
        ReqDeleteCommunity request = new ReqDeleteCommunity(id);
        mModel.deleteCommunity(request)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {

                    }
                });
    }

    @Override
    public void publicCommunityThumbUp(String id) {
        ReqPublicCommunityThumbUp re = new ReqPublicCommunityThumbUp(id, APP.user.getUserId());
        APISubcriber<BaseResponse> apiSubcriber = new APISubcriber<BaseResponse>() {
            @Override
            public void onFail(BaseResponse baseResponse,String msg) {
                mView.tumpUpFail();
            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.tumpUpSuccess();
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.publicCommunityThumbUp(re).subscribe(apiSubcriber);
    }

}
