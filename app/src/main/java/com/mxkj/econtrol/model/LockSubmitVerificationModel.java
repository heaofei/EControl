package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.request.ReqLockUserApplyPartPassword;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.contract.LockSubmitVerificationContract;
import com.mxkj.econtrol.contract.LockTemporaryPswContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: 门锁提交身份证验证
 */

public class LockSubmitVerificationModel implements LockSubmitVerificationContract.Model {
    @Override
    public Observable<BaseResponse> userApplyPartPassword(ReqLockUserApplyPartPassword reqLockUserApplyPartPassword) {
        return RetrofitUtil.getInstance().getmApiService()
                .userApplyPartPassword(reqLockUserApplyPartPassword.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }




}
