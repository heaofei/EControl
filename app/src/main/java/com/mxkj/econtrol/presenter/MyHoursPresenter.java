package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqSetHouseDefault;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResSetDefaultUserHouse;
import com.mxkj.econtrol.contract.MyHoursListContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by ${  chenjun  } on 2017/8/2.
 */

public class MyHoursPresenter implements MyHoursListContract.Presenter{

    private MyHoursListContract.View mView;
    private MyHoursListContract.Model mModel;

    public MyHoursPresenter(MyHoursListContract.View mView, MyHoursListContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
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
    public void setDefaultUserHouse() {
        if (APP.user == null) {
            return;
        }
        ReqSetHouseDefault reqSetHouseDefault = new ReqSetHouseDefault(mView.getHouseId());
        mModel.setDefaultUserHouse(reqSetHouseDefault)
                .subscribe(new APISubcriber<ResSetDefaultUserHouse>() {
                    @Override
                    public void onFail(ResSetDefaultUserHouse baseResponse,String msg) {
                        mView.setDefaultUserHouseFail(msg);
                    }
                    @Override
                    public void onSuccess(ResSetDefaultUserHouse resSetDefaultUserHouse) {
                        mView.setDefaultUserHouseSuccess(resSetDefaultUserHouse);
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
    public void start() {

    }
}
