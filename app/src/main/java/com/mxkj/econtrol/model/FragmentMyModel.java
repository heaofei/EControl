package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.response.ResAppPushMessageTipCount;
import com.mxkj.econtrol.bean.response.ResAppTodoList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;
import com.mxkj.econtrol.contract.FragmentMainContract;
import com.mxkj.econtrol.contract.FragmentMyContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/03.
 *
 * @Description:
 */

public class FragmentMyModel implements FragmentMyContract.Model {



    @Override
    public Observable<ResAppPushMessageTipCount> appPushMessageTipCount() {
        return RetrofitUtil.getInstance().getmApiService()
                .appPushMessageTipCount("{}")
                .compose(new APITransFormer<ResAppPushMessageTipCount>());
    }

    @Override
    public Observable<ResAppTodoList> appTodoList() {
        return RetrofitUtil.getInstance().getmApiService()
                .appTodoList("{}")
                .compose(new APITransFormer<ResAppTodoList>());
    }


    /***获取申请列表**/
    @Override
    public Observable<ResGetApplyBindHouseList> getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getApplyBindHouseList(reqGetApplyBindHouseList.toJsonStr())
                .compose(new APITransFormer<ResGetApplyBindHouseList>());
    }
}
