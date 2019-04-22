package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitch;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.request.ReqUserCheckBindHouse;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.contract.HouseUserListContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class HouseUserListPresenter implements HouseUserListContract.Presenter {
    private HouseUserListContract.View mView;
    private HouseUserListContract.Model mModel;

    public HouseUserListPresenter(HouseUserListContract.View mView, HouseUserListContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getHouseUserList(String houseId) {
        mModel.getHouseUserList(new ReqGetHouseUserList(houseId))
                .subscribe(new APISubcriber<ResGetHouseUserList>() {
                    @Override
                    public void onFail(ResGetHouseUserList baseResponse,String msg) {
                        mView.getHouseUserListFail(msg);
                    }

                    @Override
                    public void onSuccess(ResGetHouseUserList resGetHouseUserList) {
                        mView.getHouseUserListSuccess(resGetHouseUserList.getHouseUserList());
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


    @Override
    public void getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList) {
        mModel.getApplyBindHouseList(reqGetApplyBindHouseList)
                .subscribe(new APISubcriber<ResGetApplyBindHouseList>() {
                    @Override
                    public void onFail(ResGetApplyBindHouseList baseResponse,String msg) {
                        mView.getApplyBindHouseListFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList) {
                        mView.getApplyBindHouseListSuccess(resGetApplyBindHouseList);
                    }
                });
    }

}
