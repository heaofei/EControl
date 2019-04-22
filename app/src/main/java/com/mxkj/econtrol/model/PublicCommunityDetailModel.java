package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteCommunityComments;
import com.mxkj.econtrol.bean.request.ReqDeleteCommunityThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityThumbUpList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityComment;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityThumbUp;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsList;
import com.mxkj.econtrol.bean.response.ResGetCommunityThumbUpList;
import com.mxkj.econtrol.contract.PublicCommunityDetailContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/21.
 *
 * @Description：
 */

public class PublicCommunityDetailModel implements PublicCommunityDetailContract.Model {
    @Override
    public Observable<ResGetCommunityCommentsList> getCommunityCommentsList(ReqGetCommunityCommentsList reqGetCommunityCommentsList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getCommunityCommentsList(reqGetCommunityCommentsList.toJsonStr())
                .compose(new APITransFormer<ResGetCommunityCommentsList>());
    }

    @Override
    public Observable<BaseResponse> publicCommunityComments(ReqPublicCommunityComment reqPublicCommunityComment) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqPublicCommunityComment.toJsonStr(), APIService.PUBLIC_COMMUNITY_COMMENTS)
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityThumbUp request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.PUBLIC_COMMUNITY_THUMBUP)
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<ResGetCommunityThumbUpList> getCommunityThumbUpList(ReqGetCommunityThumbUpList reauest) {
        return RetrofitUtil.getInstance().getmApiService()
                .getCommunityThumbUpList(reauest.toJsonStr())
                .compose(new APITransFormer<ResGetCommunityThumbUpList>());
    }

    @Override
    public Observable<BaseResponse> deleteCommunityComments(ReqDeleteCommunityComments request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.DELETE_COMMUNITY_COMMENTS)
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> deleteCommunityThumbUp(ReqDeleteCommunityThumbUp request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.DELETE_COMMUNITYT_HUMBUP)
                .compose(new APITransFormer<BaseResponse>());
    }

}
