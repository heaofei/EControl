package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
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
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class ArticleDetailPresenter implements ArticleDetailContract.Presenter {
    private ArticleDetailContract.Model mModel;
    private ArticleDetailContract.View mView;

    public ArticleDetailPresenter(ArticleDetailContract.View mView, ArticleDetailContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    /**
     * 获取文章详情
     * */
    @Override
    public void getArticleDetail(String articleId, int page, int rows) {
        ReqGetCommunityCommentsList reqGetCommunityList;

        if (APP.isLogin) {
            reqGetCommunityList = new ReqGetCommunityCommentsList(articleId, page, rows);
            reqGetCommunityList.setUserId(APP.user.getUserId());
        }else {
            reqGetCommunityList = new ReqGetCommunityCommentsList(articleId, page, rows);
        }

        mModel.getArticleDetail(reqGetCommunityList)
                .subscribe(new APISubcriber<ResGetNewsDetail>() {
                    @Override
                    public void onFail(ResGetNewsDetail resGetNewsDetail, String msg) {
                        mView.getArticleDetailFali(msg);
                    }

                    @Override
                    public void onSuccess(ResGetNewsDetail resGetNewsDetail) {
                        mView.getArticleDetailSuccess(resGetNewsDetail);
                    }
                });
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
     * // 收藏
     * @param communityId
     * @param userId
     */
    @Override
    public void publicCommunityCollectionUp(String communityId, String userId) {
        ReqPublicCommunityUser reqPublicCommunityUser = new ReqPublicCommunityUser();
        reqPublicCommunityUser.setCommunityId(communityId);
        reqPublicCommunityUser.setUserId(userId);

        mModel.publicCommunityCollectionUp(reqPublicCommunityUser)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse resGetNewsDetail, String msg) {
                        mView.publicCommunityCollectionUpFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse resGetNewsDetail) {
                        mView.publicCommunityCollectionUpSuccess(resGetNewsDetail);
                    }
                });
    }

    /**
     * 文章阅读数+1
     * @param communityId
     * @param userId
     */
    @Override
    public void publicCommunityReadingUp(String communityId, String userId) {
        ReqPublicCommunityUser reqPublicCommunityUser = new ReqPublicCommunityUser();
        reqPublicCommunityUser.setCommunityId(communityId);
        reqPublicCommunityUser.setUserId(userId);

        mModel.publicCommunityReadingUp(reqPublicCommunityUser)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse resGetNewsDetail, String msg) {

                    }
                    @Override
                    public void onSuccess(BaseResponse resGetNewsDetail) {
                        mView.publicCommunityReadingUpSuccess(resGetNewsDetail);
                    }
                });
    }

    /**
     * 点赞 （对文章）
     * @param communityId
     * @param userId
     */
    @Override
    public void publicCommunityThumbUp(String communityId, String userId) {
        ReqPublicCommunityUser reqPublicCommunityUser = new ReqPublicCommunityUser();
        reqPublicCommunityUser.setCommunityId(communityId);
        reqPublicCommunityUser.setUserId(userId);

        mModel.publicCommunityThumbUp(reqPublicCommunityUser)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse resGetNewsDetail, String msg) {
                        mView.publicCommunityThumbUpFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse resGetNewsDetail) {
                        mView.publicCommunityThumbUpSuccess(resGetNewsDetail);
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
}
