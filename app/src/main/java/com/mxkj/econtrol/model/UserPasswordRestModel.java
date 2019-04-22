package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqSUserSms;
import com.mxkj.econtrol.bean.request.ReqUserPassWordReset;
import com.mxkj.econtrol.contract.UserPassWordResetContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/28.
 *
 * @Description: 重置密码model
 */

public class UserPasswordRestModel implements UserPassWordResetContract.Model {
    @Override
    public Observable<BaseResponse> getVerifyCode(ReqSUserSms reqSUserSms) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqSUserSms.toJsonStr(), "userPassWordResetSms")
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> userPasswordRest(ReqUserPassWordReset reqUserPassWordReset) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqUserPassWordReset.toJsonStr(), APIService.USER_PASSWORD_RESET)
                .compose(new APITransFormer<BaseResponse>());
    }
}
