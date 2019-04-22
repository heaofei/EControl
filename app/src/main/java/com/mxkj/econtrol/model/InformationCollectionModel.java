package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.request.ReqGetUserCommunity;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.contract.InformationCollectionContract;
import com.mxkj.econtrol.contract.InformationCommentContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/7/6.
 *
 * @Description:
 */

public class InformationCollectionModel implements InformationCollectionContract.Model {


    /**获取我的收藏列表**/

    @Override
    public Observable<ResGetCommunityList> getCommunityCollectionList(ReqGetUserCommunity reqGetUserCommunity) {
        return RetrofitUtil.getInstance().getmApiService()
                .getCommunityCollectionList(reqGetUserCommunity.toJsonStr())
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
