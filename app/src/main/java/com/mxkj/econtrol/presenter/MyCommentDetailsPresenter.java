package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqCommunityCommentThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsInfoList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityCommeants;
import com.mxkj.econtrol.bean.response.CommentList;
import com.mxkj.econtrol.bean.response.CommunityCommentsInfoList;
import com.mxkj.econtrol.contract.CommentDetailsContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class MyCommentDetailsPresenter implements CommentDetailsContract.Presenter {
    private CommentDetailsContract.Model mModel;
    private CommentDetailsContract.View mView;

    public MyCommentDetailsPresenter(CommentDetailsContract.View mView, CommentDetailsContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }


    /**
     * // 发送平评论
     * */
    @Override
    public void publicCommunityComments(String msg, String communityId, String replyUserId) {
        ReqPublicCommunityCommeants reqPublicCommunityCommeants = new ReqPublicCommunityCommeants();
        reqPublicCommunityCommeants.setCommunityId(communityId);
        reqPublicCommunityCommeants.setMsg(msg);
        reqPublicCommunityCommeants.setReplyUserId(replyUserId);
        reqPublicCommunityCommeants.setUserId(APP.user.getUserId());

        mModel.publicCommunityComments(reqPublicCommunityCommeants)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse resGetNewsDetail, String msg) {
                        mView.publicCommunityCommentsFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse resGetNewsDetail) {
                        mView.publicCommunityCommentsSuccess(resGetNewsDetail);
                    }
                });
    }


    /**
     *
     * // 添加文章的评论点赞  （对评论）
     * @param CommunityCommentId
     * @param userId
     */
    @Override
    public void publicCommunityCommentThumbUp(String CommunityCommentId, String userId) {
        ReqCommunityCommentThumbUp reqCommunityCommentThumbUp = new ReqCommunityCommentThumbUp();
        reqCommunityCommentThumbUp.setCommunityCommentId(CommunityCommentId);
        reqCommunityCommentThumbUp.setUserId(userId);

        mModel.publicCommunityCommentThumbUp(reqCommunityCommentThumbUp)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse resGetNewsDetail, String msg) {
                        mView.publicCommunityCommentThumbUpFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse resGetNewsDetail) {
                        mView.publicCommunityCommentThumbUpSuccess(resGetNewsDetail);
                    }
                });
    }
    /***
     * 获取二级评论列表
     * ***/
    @Override
    public void getCommunityCommentsInfoList(int page, int rows, String commId) {
        ReqGetCommunityCommentsInfoList reqGetCommunityCommentsInfoList = new ReqGetCommunityCommentsInfoList();
        reqGetCommunityCommentsInfoList.setPage(page);
        reqGetCommunityCommentsInfoList.setRows(rows);
        reqGetCommunityCommentsInfoList.setCommId(commId);

        if (APP.isLogin) {
            reqGetCommunityCommentsInfoList.setUserId(APP.user.getUserId());
        }else {
            reqGetCommunityCommentsInfoList.setUserId("");
        }

        mModel.getCommunityCommentsInfoList(reqGetCommunityCommentsInfoList)
                .subscribe(new APISubcriber<CommunityCommentsInfoList>() {
                    @Override
                    public void onFail(CommunityCommentsInfoList resGetNewsDetail, String msg) {
                        mView.getCommunityCommentsInfoListFali(msg);
                    }
                    @Override
                    public void onSuccess(CommunityCommentsInfoList commentList) {
                        mView.getCommunityCommentsInfoListSuccess(commentList);
                    }
                });
    }
}
