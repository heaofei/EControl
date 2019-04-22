package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserInfo;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.bean.response.ResGethouseUserInfo;
import com.mxkj.econtrol.contract.HouseUserInfoContract;
import com.mxkj.econtrol.contract.HouseUserListContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2017/10/25.
 *
 * @Description：
 */

public class HouseUserInfoModel implements HouseUserInfoContract.Model {

    @Override
    public Observable<ResGethouseUserInfo> getHouseUserInfo(ReqGetHouseUserInfo reqGetHouseUserInfo) {
        return RetrofitUtil.getInstance()
                .getmApiService()
                .getHouseUserInfo(reqGetHouseUserInfo.toJsonStr())
                .compose(new APITransFormer<ResGethouseUserInfo>());
    }
}
