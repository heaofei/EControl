package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.request.ReqUserMoodModify;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.contract.MainContract;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MainContract.Model mModel;

    public MainPresenter(MainContract.View mView, MainContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getHousingEstateList() {
        if (APP.user == null) {
            return;
        }
        ReqGetUserHouseList reqGetUserHouseList = new ReqGetUserHouseList(APP.user.getUserId());
        mModel.getHousingEstateList(reqGetUserHouseList)
                .subscribe(new APISubcriber<ResGetUserHouseList>() {
                    @Override
                    public void onFail(ResGetUserHouseList baseResponse,String msg) {
                        mView.getHousingEstateListFail("");
                    }

                    @Override
                    public void onSuccess(ResGetUserHouseList resGetUserHouseList) {
                        mView.getHousingEstateListSuccess(resGetUserHouseList);
                    }
                });
    }

    @Override
    public void userMoodModify(String mood) {
        if (APP.user == null) {
            return;
        }
        ReqUserMoodModify reqUserMoodModify = new ReqUserMoodModify(APP.user.getUserId(), mood);
        mModel.userMoodModify(reqUserMoodModify)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.userMoodModifyFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.userMoodModifySuccess();
                    }
                });
    }

    @Override
    public void unbindHouseUser4Reject(String houseUserId) {
        ReqUnbindHouseUser4Reject request = new ReqUnbindHouseUser4Reject(houseUserId);
        mModel.unbindHouseUser4Reject(request)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.unbindHouseUser4RejectFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.unbindHouseUser4RejectSuccess();
                    }
                });
    }
}
