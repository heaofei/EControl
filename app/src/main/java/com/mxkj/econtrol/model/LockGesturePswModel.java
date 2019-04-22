package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordChange;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordGrant;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordInit;
import com.mxkj.econtrol.bean.response.ResAppPushMessageTipCount;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.response.ResLockPasswordGrant;
import com.mxkj.econtrol.contract.FragmentMyContract;
import com.mxkj.econtrol.contract.LockGesturePswContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description:
 */

public class LockGesturePswModel implements LockGesturePswContract.Model {


    @Override
    public Observable<BaseResponse> partPasswordInit(ReqLockPartPasswordInit reqLockPartPasswordInit) {
        return RetrofitUtil.getInstance().getmApiService()
                .partPasswordInit(reqLockPartPasswordInit.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<ResLockPasswordGrant> partPasswordGrant(ReqLockPartPasswordGrant reqLockPartPasswordGrant) {
        return RetrofitUtil.getInstance().getmApiService()
                .partPasswordGrant(reqLockPartPasswordGrant.toJsonStr())
                .compose(new APITransFormer<ResLockPasswordGrant>());
    }

    @Override
    public Observable<BaseResponse> partPasswordChange(ReqLockPartPasswordChange reqLockPartPasswordChange) {
        return RetrofitUtil.getInstance().getmApiService()
                .partPasswordChange(reqLockPartPasswordChange.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }


}
