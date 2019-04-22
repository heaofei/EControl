package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqCommunityCommentThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsInfoList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityCommeants;
import com.mxkj.econtrol.bean.response.CommentList;
import com.mxkj.econtrol.bean.response.CommunityCommentsInfoList;
import com.mxkj.econtrol.bean.response.ResGetNewsDetail;
import com.mxkj.econtrol.contract.CommentDetailsContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/7/6.
 *
 * @Description:
 */

public class CommentDetailsModel implements CommentDetailsContract.Model {


    /***提交评论**/
    @Override
    public Observable<BaseResponse> publicCommunityComments(ReqPublicCommunityCommeants reqPublicCommunityCommeants) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityComments(reqPublicCommunityCommeants.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
    /****点赞，对评论列表**/
    @Override
    public Observable<BaseResponse> publicCommunityCommentThumbUp(ReqCommunityCommentThumbUp reqCommunityCommentThumbUp) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityCommentThumbUp(reqCommunityCommentThumbUp.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    /*******获取二级评论列表*****/
    @Override
    public Observable<CommunityCommentsInfoList> getCommunityCommentsInfoList(ReqGetCommunityCommentsInfoList reqGetCommunityCommentsInfoList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getCommunityCommentsInfoList(reqGetCommunityCommentsInfoList.toJsonStr())
                .compose(new APITransFormer<CommunityCommentsInfoList>());
    }

}
