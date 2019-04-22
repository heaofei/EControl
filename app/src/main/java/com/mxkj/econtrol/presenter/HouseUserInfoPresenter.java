package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserInfo;
import com.mxkj.econtrol.bean.response.ResGethouseUserInfo;
import com.mxkj.econtrol.contract.HouseUserInfoContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by ${  chenjun  } on 2017/10/25.
 */

public class HouseUserInfoPresenter implements HouseUserInfoContract.Presenter{

    private HouseUserInfoContract.View mView;
    private HouseUserInfoContract.Model mModel;

    public HouseUserInfoPresenter(HouseUserInfoContract.View mView, HouseUserInfoContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }
    @Override
    public void getHouseUserInfo(String string) {
        mModel.getHouseUserInfo(new ReqGetHouseUserInfo(string))
                .subscribe(new APISubcriber<ResGethouseUserInfo>() {
                    @Override
                    public void onFail(ResGethouseUserInfo baseResponse, String msg) {
                        mView.getHouseUserInfoFail(msg);
                    }

                    @Override
                    public void onSuccess(ResGethouseUserInfo s) {
                        mView.getHouseUserInfoSuccess(s);
                    }
                });
    }

    @Override
    public void start() {

    }
}
