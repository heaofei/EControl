package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitch;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser;
import com.mxkj.econtrol.bean.request.ReqUserCheckBindHouse;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.contract.HouseUserListContract;
import com.mxkj.econtrol.contract.HouseUserListOperatetContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class HouseUserListOperatetModel implements HouseUserListOperatetContract.Model {

    @Override
    public Observable<BaseResponse> unbindHouseUser(ReqUnbindHouseUser reqUnbindHouseUser) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqUnbindHouseUser.toJsonStr(), APIService.UNBIND_HOUSE_USER)
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> houseManagerSwitch(ReqHouseManagerSwitch request) {
       /* return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.HOUSE_MANAGER_SWITCH)
                .compose(new APITransFormer<BaseResponse>());*/

        return RetrofitUtil.getInstance().getmApiService()
                .houseManagerSwitchAssign(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());


    }


}
