package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqModifyPassword;
import com.mxkj.econtrol.contract.UserPwdModifyContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/27.
 *
 * @Description:
 */

public class ModifyPasswordModel implements UserPwdModifyContract.Model {


    @Override
    public Observable<BaseResponse> modifyPassword(ReqModifyPassword reqModifyPassword) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqModifyPassword.toJsonStr(), APIService.USER_PWD_MODIFY)
                .compose(new APITransFormer<BaseResponse>());


    }
}
