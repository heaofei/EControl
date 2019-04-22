package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqCommunityCommentThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsInfoList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityCommeants;
import com.mxkj.econtrol.bean.response.CommentList;
import com.mxkj.econtrol.bean.response.CommunityCommentsInfoList;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2018/7/6.
 */

public interface CommentDetailsContract {


    interface View extends BaseView<CommentDetailsContract.Presenter> {

         //添加社区评论信息成功
        void publicCommunityCommentsSuccess(BaseResponse baseResponse);
        //添加社区评论信息失败
        void publicCommunityCommentsFali(String msg);

        //添加文章的评论的点赞 成功
        void publicCommunityCommentThumbUpSuccess(BaseResponse baseResponse);
        //添加文章的评论的点赞 失败
        void publicCommunityCommentThumbUpFali(String msg);

        //获取二级评论列表 成功
        void getCommunityCommentsInfoListSuccess(CommunityCommentsInfoList baseResponse);
        //获取二级评论列表 失败
        void getCommunityCommentsInfoListFali(String msg);

    }

    interface Presenter extends BasePresenter {

        // 添加社区评论信息  //参数 msg 评论内容 communityId 文章ID userId   replyUserId回复人ID（非必填）
        void publicCommunityComments(String msg, String communityId, String replyUserId);
        // 添加文章的评论点赞
        void publicCommunityCommentThumbUp(String communityCommentId, String userId);
        // 获取二级评论列表
        void getCommunityCommentsInfoList(int page, int   rows ,String commId);

    }
    interface Model extends BaseModel {
        // 添加社区评论信息
        Observable<BaseResponse> publicCommunityComments(ReqPublicCommunityCommeants reqPublicCommunityCommeants);
        // 添加文章的评论点赞
        Observable<BaseResponse> publicCommunityCommentThumbUp(ReqCommunityCommentThumbUp reqCommunityCommentThumbUp);
        // 获取二级评论列表
        Observable<CommunityCommentsInfoList> getCommunityCommentsInfoList(ReqGetCommunityCommentsInfoList reqGetCommunityCommentsInfoList);
    }

}
