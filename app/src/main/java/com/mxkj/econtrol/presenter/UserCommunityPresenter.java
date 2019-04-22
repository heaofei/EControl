package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetUserCommunity;
import com.mxkj.econtrol.bean.response.ResGetUserCommunity;
import com.mxkj.econtrol.contract.UserCommunityContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/6/13.
 *
 * @Description：
 */

public class UserCommunityPresenter implements UserCommunityContract.Presenter {

    private UserCommunityContract.View mView;
    private UserCommunityContract.Model mModel;

    public UserCommunityPresenter(UserCommunityContract.View mView, UserCommunityContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getUserCommunity(int page) {
        final ReqGetUserCommunity reqGetUserCommunity = new ReqGetUserCommunity(page, 8, mView.getUserId());
        mModel.getUserCommunity(reqGetUserCommunity)
                .subscribe(new APISubcriber<ResGetUserCommunity>() {
                    @Override
                    public void onFail(ResGetUserCommunity baseResponse, String msg) {
                        mView.getUserCommunityFail(msg);
                    }

                    @Override
                    public void onSuccess(ResGetUserCommunity resGetUserCommunity) {
                        mView.getUserCommunitySuccess(resGetUserCommunity);
                    }
                });
    }
}
