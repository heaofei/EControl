package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqCommunityCommentThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityCommeants;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetNewsDetail;
import com.mxkj.econtrol.contract.ArticleDetailContract;
import com.mxkj.econtrol.contract.InformationContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/7/6.
 *
 * @Description:
 */

public class ArticleDetailModel implements ArticleDetailContract.Model {

    /**文章详情*/
    @Override
    public Observable<ResGetNewsDetail> getArticleDetail(ReqGetCommunityCommentsList reqGetCommunityList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getCommunityCommentsList(reqGetCommunityList.toJsonStr())
                .compose(new APITransFormer<ResGetNewsDetail>());
    }

    /***提交评论**/
    @Override
    public Observable<BaseResponse> publicCommunityComments(ReqPublicCommunityCommeants reqPublicCommunityCommeants) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityComments(reqPublicCommunityCommeants.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    /****收藏*/
    @Override
    public Observable<BaseResponse> publicCommunityCollectionUp(ReqPublicCommunityUser reqPublicCommunityUser) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityCollectionUp(reqPublicCommunityUser.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    /****增加阅读数量 +1**/
    @Override
    public Observable<BaseResponse> publicCommunityReadingUp(ReqPublicCommunityUser reqPublicCommunityUser) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityReadingUp(reqPublicCommunityUser.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
    /***点赞，对文章**/
    @Override
    public Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityUser reqPublicCommunityUser) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityThumbUp(reqPublicCommunityUser.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
    /****点赞，对评论列表**/
    @Override
    public Observable<BaseResponse> publicCommunityCommentThumbUp(ReqCommunityCommentThumbUp reqCommunityCommentThumbUp) {
        return RetrofitUtil.getInstance().getmApiService()
                .publicCommunityCommentThumbUp(reqCommunityCommentThumbUp.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
