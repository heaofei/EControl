package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqUserRegist;
import com.mxkj.econtrol.bean.request.ReqUserRegistSms;
import com.mxkj.econtrol.bean.response.ResUserRegist;
import com.mxkj.econtrol.contract.RegistContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public class RegistModel implements RegistContract.Model {
    @Override
    public Observable<ResUserRegist> regist(ReqUserRegist reqUserRegist) {
        return RetrofitUtil.getInstance().getmApiService().userRegist(reqUserRegist.toJsonStr())
                .compose(new APITransFormer<ResUserRegist>());

    }

    @Override
    public Observable<BaseResponse> userRegistSms(ReqUserRegistSms reqUserRegistSms) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqUserRegistSms.toJsonStr(), APIService.USER_REGIST_SMS)
                .compose(new APITransFormer<BaseResponse>());
    }
}
