package com.mxkj.econtrol.model;

import com.mxkj.econtrol.bean.request.ReqGetUserCommunity;
import com.mxkj.econtrol.bean.response.ResGetUserCommunity;
import com.mxkj.econtrol.contract.UserCommunityContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/6/13.
 *
 * @Description：
 */

public class UserCommunityModel implements UserCommunityContract.Model {
    @Override
    public Observable<ResGetUserCommunity> getUserCommunity(ReqGetUserCommunity request) {
      return  RetrofitUtil.getInstance().getmApiService().getUserCommunity(request.toJsonStr())
                .compose(new APITransFormer<ResGetUserCommunity>());
    }
}
