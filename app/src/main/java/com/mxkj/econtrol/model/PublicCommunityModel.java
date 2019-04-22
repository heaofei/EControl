package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteCommunity;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunity;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityThumbUp;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetUserEstateList;
import com.mxkj.econtrol.contract.PublicCommunityContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description：
 */

public class PublicCommunityModel implements PublicCommunityContract.Model {
    @Override
    public Observable<ResGetCommunityList> getCommunityList(ReqGetCommunityList reqGetCommunityList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getCommunityList(reqGetCommunityList.toJsonStr())
                .compose(new APITransFormer<ResGetCommunityList>());
    }


    @Override
    public Observable<BaseResponse> publicCommunity(ReqPublicCommunity reqPublicCommunity) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqPublicCommunity.toJsonStr(), APIService.PUBLI_CCOMMUNITY)
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> deleteCommunity(ReqDeleteCommunity reqDeleteCommunity) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqDeleteCommunity.toJsonStr(), APIService.DELETE_COMMUNITY)
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityThumbUp request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.PUBLIC_COMMUNITY_THUMBUP)
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<ResGetUserEstateList> getUserEstateList() {
        return RetrofitUtil.getInstance().getmApiService()
                .getUserEstateList("{}")
                .compose(new APITransFormer<ResGetUserEstateList>());
    }


}
