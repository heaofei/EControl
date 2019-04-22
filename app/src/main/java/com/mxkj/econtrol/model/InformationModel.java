package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqGetRankingDateList;
import com.mxkj.econtrol.bean.request.ReqGetTimeRanking;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;
import com.mxkj.econtrol.contract.DeviceUseNumberContract;
import com.mxkj.econtrol.contract.InformationContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/7/6.
 *
 * @Description:
 */

public class InformationModel implements InformationContract.Model {

    @Override
    public Observable<ResGetCommunityList> getCommunityList(ReqGetCommunityList reqGetCommunityList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getCommunityList(reqGetCommunityList.toJsonStr())
                .compose(new APITransFormer<ResGetCommunityList>());
    }

    /****收藏*/
    @Override
    public Observable<BaseResponse> publicCommunityCollectionUp(ReqPublicCommunityUser reqPublicCommunityUser) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityCollectionUp(reqPublicCommunityUser.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    /***点赞，对文章**/
    @Override
    public Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityUser reqPublicCommunityUser) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityThumbUp(reqPublicCommunityUser.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
