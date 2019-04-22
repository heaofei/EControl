package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqLockGetPartPermissionsList;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.request.ReqLockPartPermissionsUpdate;
import com.mxkj.econtrol.bean.request.ReqPartUnbindGateway;
import com.mxkj.econtrol.bean.request.ReqPartUnbindSnid;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.response.ResLockPowerList;
import com.mxkj.econtrol.contract.LockPowerListContract;
import com.mxkj.econtrol.contract.LockTemporaryPswContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: 门锁权限列表
 */

public class LockPowerListModel implements LockPowerListContract.Model {

    @Override
    public Observable<ResLockPowerList> getPartPermissionsList(ReqLockGetPartPermissionsList reqLockGetPartPermissionsList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getPartPermissionsList(reqLockGetPartPermissionsList.toJsonStr())
                .compose(new APITransFormer<ResLockPowerList>());
    }

    @Override
    public Observable<BaseResponse> partPermissionsUpdate(ReqLockPartPermissionsUpdate reqLockPartPermissionsUpdate) {
        return RetrofitUtil.getInstance().getmApiService()
                .partPermissionsUpdate(reqLockPartPermissionsUpdate.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> partUnbindGateway(ReqPartUnbindGateway reqPartUnbindGateway) {
        return RetrofitUtil.getInstance().getmApiService()
                .partUnbindGateway(reqPartUnbindGateway.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> partUnbindSnid(ReqPartUnbindSnid reqPartUnbindSnid) {
        return RetrofitUtil.getInstance().getmApiService()
                .partUnbindSn(reqPartUnbindSnid.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
