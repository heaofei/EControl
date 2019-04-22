package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetSceneList;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;
import com.mxkj.econtrol.contract.HouseMainContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class HouseMainModel implements HouseMainContract.Model {
    @Override
    public Observable<ResGetSceneList> getSceneList(ReqGetSceneList reqGetSceneList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getSceneList(reqGetSceneList.toJsonStr())
                .compose(new APITransFormer<ResGetSceneList>());
    }

    @Override
    public Observable<ResGetAtHomeUserList> getAtHomeUserList(ReqGetAtHomeUserList request) {
        return RetrofitUtil.getInstance().getmApiService()
                .getAtHomeUserList(request.toJsonStr())
                .compose(new APITransFormer<ResGetAtHomeUserList>());

    }

    @Override
    public Observable<BaseResponse> deleteOtherHomeUser(ReqDeleteOtherHomeUser request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.DELETE_OTHER_HOME_USER)
                .compose(new APITransFormer<BaseResponse>());
    }
}
