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
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description:
 */

public class NewHousePresenter implements NewHouseContract.Presenter {

    private NewHouseContract.View mView;
    private NewHouseContract.Model mModel;

    public NewHousePresenter(NewHouseContract.View mView, NewHouseContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAreaList(String areaCode) {
        APISubcriber<ResGetAreaLsit> apiSubcriber = new APISubcriber<ResGetAreaLsit>() {
            @Override
            public void onFail(ResGetAreaLsit baseResponse,String msg) {
                mView.getAreaListFail(msg);
            }

            @Override
            public void onSuccess(ResGetAreaLsit resGetAreaLsit) {
                mView.getAreaListSuccess(resGetAreaLsit.getAreaList());
            }
        };
        apiSubcriber.setShowLoding(false);

        ReqGetAreaList req = new ReqGetAreaList();
        req.setAreaCode(areaCode);
        req.setStatus("1"); //0-禁用；1-启用；不传-全部；

        mModel.getAreaList(req)
                .subscribe(apiSubcriber);

    }

    @Override
    public void getHousingEstateList(final ReqGetHousingEstateList reqGetHousingEstateList) {
        APISubcriber<ResGetHousingEstateList> apiSubcriber = new APISubcriber<ResGetHousingEstateList>() {
            @Override
            public void onFail(ResGetHousingEstateList baseResponse,String msg) {
                mView.getHousingEstateListFail(msg);
            }

            @Override
            public void onSuccess(ResGetHousingEstateList resGetHousingEstateList) {
                mView.getHousingEstateListSuccess(resGetHousingEstateList.getHousingEstatList());
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.getHousingEstateList(reqGetHousingEstateList)
                .subscribe(apiSubcriber);
    }

    @Override
    public void getBuildingList(ReqGetBuildingList reqGetBuildingAndHouseList) {
        APISubcriber<ResGetBuildingList> apiSubcriber = new APISubcriber<ResGetBuildingList>() {
            @Override
            public void onFail(ResGetBuildingList baseResponse,String msg) {
                mView.getBuildingListFail(msg);
            }

            @Override
            public void onSuccess(ResGetBuildingList resGetBuildingList) {
                mView.getBuildingListSuccess(resGetBuildingList.getBuildingList());
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.getBuildingList(reqGetBuildingAndHouseList)
                .subscribe(apiSubcriber);
    }

    @Override
    public void getHouseList(ReqGetHouseList reqGetBuildingAndHouseList) {
        APISubcriber<ResGetHouseList> apiSubcriber = new APISubcriber<ResGetHouseList>() {
            @Override
            public void onFail(ResGetHouseList baseResponse,String msg) {
                mView.getHouseListFail(msg);
            }

            @Override
            public void onSuccess(ResGetHouseList resGetHouseList) {
                mView.getHouseListSuccess(resGetHouseList.getHouseList());
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.getHouseList(reqGetBuildingAndHouseList)
                .subscribe(apiSubcriber);
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
