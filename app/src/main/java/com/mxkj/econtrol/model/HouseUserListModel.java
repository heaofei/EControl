package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitch;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.request.ReqUserCheckBindHouse;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.contract.HouseUserListContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class HouseUserListModel implements HouseUserListContract.Model {
    @Override
    public Observable<ResGetHouseUserList> getHouseUserList(ReqGetHouseUserList reqGetHouseUserList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getHouseUserList(reqGetHouseUserList.toJsonStr())
                .compose(new APITransFormer<ResGetHouseUserList>());
    }

    @Override
    public Observable<BaseResponse> unbindHouseUser4Reject(ReqUnbindHouseUser4Reject request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.UNBIND_HOUSE_USER_4_REJECT)
                .compose(new APITransFormer<BaseResponse>());
    }


    /***获取申请列表**/
    @Override
    public Observable<ResGetApplyBindHouseList> getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getApplyBindHouseList(reqGetApplyBindHouseList.toJsonStr())
                .compose(new APITransFormer<ResGetApplyBindHouseList>());
    }
}
