package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.request.ReqUserMoodModify;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.contract.MainContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class MainModel implements MainContract.Model {


    @Override
    public Observable<ResGetUserHouseList> getHousingEstateList(ReqGetUserHouseList reqGetUserHouseList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getUserHouseList(reqGetUserHouseList.toJsonStr())
                .compose(new APITransFormer<ResGetUserHouseList>());
    }

    @Override
    public Observable<BaseResponse> userMoodModify(ReqUserMoodModify reqUserMoodModify) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqUserMoodModify.toJsonStr(), APIService.USER_MOOD_MODIFY)
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> unbindHouseUser4Reject(ReqUnbindHouseUser4Reject request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.UNBIND_HOUSE_USER_4_REJECT)
                .compose(new APITransFormer<BaseResponse>());
    }
}
