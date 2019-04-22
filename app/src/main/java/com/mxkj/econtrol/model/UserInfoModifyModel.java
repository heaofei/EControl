package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetAreaList;
import com.mxkj.econtrol.bean.request.ReqUserHeadPicModify;
import com.mxkj.econtrol.bean.request.ReqUserInfoModify;
import com.mxkj.econtrol.bean.response.ResAreaBean;
import com.mxkj.econtrol.bean.response.ResGetAreaLsit;
import com.mxkj.econtrol.bean.response.ResUserHeadPicModify;
import com.mxkj.econtrol.contract.UserInfoModifyContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/27.
 *
 * @Description:
 */

public class UserInfoModifyModel implements UserInfoModifyContract.Model {
    @Override
    public Observable<BaseResponse> userInfoModify(ReqUserInfoModify reqUserInfoModify) {

        return RetrofitUtil.getInstance().getmApiService()
                .API(reqUserInfoModify.toJsonStr(), APIService.USER_INFO_MODIFY)
                .compose(new APITransFormer<BaseResponse>());

    }

    @Override
    public Observable<ResUserHeadPicModify> userHeadPicModify(ReqUserHeadPicModify reqUserHeadPicModify) {
        return RetrofitUtil.getInstance().getmApiService()
                .userHeadPicModify(reqUserHeadPicModify.toJsonStr())
                .compose(new APITransFormer<ResUserHeadPicModify>());
    }


    @Override
    public Observable<ResAreaBean> getAreaList(ReqGetAreaList reqGetAreaList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getAreaList02(reqGetAreaList.toJsonStr())
                .compose(new APITransFormer<ResAreaBean>());
    }
}
