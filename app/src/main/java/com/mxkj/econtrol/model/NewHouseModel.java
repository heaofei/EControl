package com.mxkj.econtrol.model;

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
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 新建方案model实现类
 */

public class NewHouseModel implements NewHouseContract.Model {
    @Override
    public Observable<ResGetAreaLsit> getAreaList(ReqGetAreaList reqGetAreaList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getAreaList(reqGetAreaList.toJsonStr())
                .compose(new APITransFormer<ResGetAreaLsit>());
    }

    @Override
    public Observable<ResGetHousingEstateList> getHousingEstateList(ReqGetHousingEstateList reqGetHousingEstateList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getHousingEstateList(reqGetHousingEstateList.toJsonStr())
                .compose(new APITransFormer<ResGetHousingEstateList>());
    }

    @Override
    public Observable<ResGetBuildingList> getBuildingList(ReqGetBuildingList reqGetBuildingAndHouseList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getBuildingList(reqGetBuildingAndHouseList.toJsonStr())
                .compose(new APITransFormer<ResGetBuildingList>());
    }

    @Override
    public Observable<ResGetHouseList> getHouseList(ReqGetHouseList reqGetHouseList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getHouseList(reqGetHouseList.toJsonStr())
                .compose(new APITransFormer<ResGetHouseList>());
    }

    @Override
    public Observable<BaseResponse> userApplyBindHouse(ReqUserApplyBindHouse reqUserApplyBindHouse) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqUserApplyBindHouse.toJsonStr(), APIService.USER_APPLY_BIND_HOUSE)
                .compose(new APITransFormer<BaseResponse>());
    }
}
