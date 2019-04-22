package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetAreaList;
import com.mxkj.econtrol.bean.request.ReqGetBuildingList;
import com.mxkj.econtrol.bean.request.ReqGetHouseList;
import com.mxkj.econtrol.bean.request.ReqGetHousingEstateList;
import com.mxkj.econtrol.bean.request.ReqUserApplyBindHouse;
import com.mxkj.econtrol.bean.response.ResGetAreaLsit;
import com.mxkj.econtrol.bean.response.ResGetBuildingList;
import com.mxkj.econtrol.bean.response.ResGetHouseList;
import com.mxkj.econtrol.bean.response.ResGetHousingEstateList;
import com.mxkj.econtrol.contract.NewHouseContract;
import com.mxkj.econtrol.contract.QrCodeScanContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description:
 */

public class QrCodeScanPresenter implements QrCodeScanContract.Presenter {

    private QrCodeScanContract.View mView;
    private QrCodeScanContract.Model mModel;

    public QrCodeScanPresenter(QrCodeScanContract.View mView, QrCodeScanContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }
    @Override
    public void userApplyBindHouse(ReqUserApplyBindHouse reqUserApplyBindHouse) {
        mModel.userApplyBindHouse(reqUserApplyBindHouse)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.userApplyBindHouseFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.userApplyBindHouseSuccess();
                    }
                });
    }
}
