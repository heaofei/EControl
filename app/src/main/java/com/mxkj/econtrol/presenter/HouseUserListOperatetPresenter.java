package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitch;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser;
import com.mxkj.econtrol.bean.request.ReqUserCheckBindHouse;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.contract.HouseUserListContract;
import com.mxkj.econtrol.contract.HouseUserListOperatetContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class HouseUserListOperatetPresenter implements HouseUserListOperatetContract.Presenter {
    private HouseUserListOperatetContract.View mView;
    private HouseUserListOperatetContract.Model mModel;

    public HouseUserListOperatetPresenter(HouseUserListOperatetContract.View mView, HouseUserListOperatetContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }


    @Override
    public void unbindHouseUser(String userId) {
        ReqUnbindHouseUser reqUnbindHouseUser = new ReqUnbindHouseUser(userId);
        mModel.unbindHouseUser(reqUnbindHouseUser)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.unbindHouseUserFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.unbindHouseUserSuccess();
                    }
                });
    }

    @Override
    public void houseManagerSwitch(String toUserId, String houseId,String isRemove) {
        ReqHouseManagerSwitch request = new ReqHouseManagerSwitch(toUserId, houseId,isRemove);
        mModel.houseManagerSwitch(request)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.houseManagerSwitchFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.houseManagerSwitchSuccess();
                    }
                });
    }


}
