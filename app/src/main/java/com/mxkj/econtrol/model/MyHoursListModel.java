package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqSetHouseDefault;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.request.ReqUserMoodModify;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResSetDefaultUserHouse;
import com.mxkj.econtrol.contract.MainContract;
import com.mxkj.econtrol.contract.MyHoursListContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 *  Created by chanjun on 2017/8/2
 *
 * @Description:
 */

public class MyHoursListModel implements MyHoursListContract.Model {

    @Override
    public Observable<ResGetUserHouseList> getHousingEstateList(ReqGetUserHouseList reqGetUserHouseList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getUserHouseList(reqGetUserHouseList.toJsonStr())
                .compose(new APITransFormer<ResGetUserHouseList>());
    }

    @Override
    public Observable<ResSetDefaultUserHouse> setDefaultUserHouse(ReqSetHouseDefault reqSetHouseDefault) {
        return RetrofitUtil.getInstance().getmApiService()
                .setDefaultUserHouse(reqSetHouseDefault.toJsonStr())
                .compose(new APITransFormer<ResSetDefaultUserHouse>());
    }

    @Override
    public Observable<BaseResponse> unbindHouseUser4Reject(ReqUnbindHouseUser4Reject request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.UNBIND_HOUSE_USER_4_REJECT)
                .compose(new APITransFormer<BaseResponse>());
    }

}
