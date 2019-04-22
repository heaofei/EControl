package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqCommunityCommentThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityCommeants;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetNewsDetail;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${  chenjun  } on 2018/7/6.
 */

public interface ArticleDetailContract {


    interface View extends BaseView<ArticleDetailContract.Presenter> {

        //获取社区信息成功
        void getArticleDetailSuccess(ResGetNewsDetail resGetNewsDetail);
        //获取社区信息失败
        void getArticleDetailFali(String msg);

         //添加社区评论信息成功
        void publicCommunityCommentsSuccess(BaseResponse baseResponse);
        //添加社区评论信息失败
        void publicCommunityCommentsFali(String msg);

        //添加添加收藏成功
        void publicCommunityCollectionUpSuccess(BaseResponse baseResponse);
        //添加收藏失败
        void publicCommunityCollectionUpFali(String msg);

         //添加阅读数成功
        void publicCommunityReadingUpSuccess(BaseResponse baseResponse);

        //添加.取消，点赞社区文章成功
        void publicCommunityThumbUpSuccess(BaseResponse baseResponse);
        //添加.取消，点赞社区文章失败
        void publicCommunityThumbUpFali(String msg);

        //添加文章的评论点赞成功
        void publicCommunityCommentThumbUpSuccess(BaseResponse baseResponse);
        //添加文章的评论点赞失败
        void publicCommunityCommentThumbUpFali(String msg);

    }

    interface Presenter extends BasePresenter {
        //获取社区信息
        void getArticleDetail(String articleId ,int page ,int rows );
        // 添加社区评论信息  //参数 msg 评论内容 communityId 文章ID userId   replyUserId回复人ID（非必填）
        void publicCommunityComments(String msg ,String communityId ,String replyUserId );
        // 添加收藏
        void publicCommunityCollectionUp(String communityId ,String userId );
        // 添加阅读数
        void publicCommunityReadingUp(String communityId ,String userId );
        // 添加.取消，点赞社区文章
        void publicCommunityThumbUp(String communityId ,String userId );
        // 添加文章的评论点赞
        void publicCommunityCommentThumbUp(String CommunityCommentId ,String userId );

    }
    interface Model extends BaseModel {
         //获取文章详情
        Observable<ResGetNewsDetail> getArticleDetail(ReqGetCommunityCommentsList reqGetCommunityCommentsList);
        // 添加社区评论信息
        Observable<BaseResponse> publicCommunityComments(ReqPublicCommunityCommeants reqPublicCommunityCommeants);
         // 添加收藏
        Observable<BaseResponse> publicCommunityCollectionUp(ReqPublicCommunityUser reqPublicCommunityUser);
        // 添加阅读数
        Observable<BaseResponse> publicCommunityReadingUp(ReqPublicCommunityUser reqPublicCommunityUser);
        // 添加.取消，点赞社区文章
        Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityUser reqPublicCommunityUser);
        // 添加文章的评论点赞
        Observable<BaseResponse> publicCommunityCommentThumbUp(ReqCommunityCommentThumbUp reqCommunityCommentThumbUp);
    }

}
