package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
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
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/21.
 *
 * @Description：
 */

public class PublicCommunityDetailPresenter implements PublicCommunityDetailContract.Presenter {
    private PublicCommunityDetailContract.View mView;
    private PublicCommunityDetailContract.Model mModel;

    public PublicCommunityDetailPresenter(PublicCommunityDetailContract.View mView, PublicCommunityDetailContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getCommunityCommentsList(String id) {
        ReqGetCommunityCommentsList list = new ReqGetCommunityCommentsList("",0,0);
        list.setCommunityId(id);
        list.setPage(0);
        list.setRows(1000);
        mModel.getCommunityCommentsList(list)
                .subscribe(new APISubcriber<ResGetCommunityCommentsList>() {
                    @Override
                    public void onFail(ResGetCommunityCommentsList baseResponse,String msg) {
                        mView.showMsg(msg);
                    }

                    @Override
                    public void onSuccess(ResGetCommunityCommentsList resGetCommunityCommentsList) {
                        mView.setAdapter(resGetCommunityCommentsList.getPage().getContent());
                    }
                });

    }

    @Override
    public void publicCommunityComments(String id, String comment, String replyUserId) {
        ReqPublicCommunityComment reqPublicCommunityComment = new ReqPublicCommunityComment();
        reqPublicCommunityComment.setCommunityId(id);
        //  reqPublicCommunityComment.setReplyUserId(replyUserId);
        reqPublicCommunityComment.setMsg(comment);
        reqPublicCommunityComment.setUserId(APP.user.getUserId());
        mModel.publicCommunityComments(reqPublicCommunityComment)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {

                    }
                });
    }


    @Override
    public void publicCommunityThumbUp(String id) {
        ReqPublicCommunityThumbUp re = new ReqPublicCommunityThumbUp(id, APP.user.getUserId());
        APISubcriber<BaseResponse> apiSubcriber = new APISubcriber<BaseResponse>() {
            @Override
            public void onFail(BaseResponse baseResponse,String msg) {
                mView.tumpUpFail();
            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.tumpUpSuccess();
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.publicCommunityThumbUp(re).subscribe(apiSubcriber);
    }

    @Override
    public void getCommunityThumbUpList() {
        ReqGetCommunityThumbUpList request = new ReqGetCommunityThumbUpList(0, 99, "4028a0815b7f3c96015b7f3ca9600001");
        mModel.getCommunityThumbUpList(request)
                .subscribe(new APISubcriber<ResGetCommunityThumbUpList>() {
                    @Override
                    public void onFail(ResGetCommunityThumbUpList baseResponse,String msg) {

                    }

                    @Override
                    public void onSuccess(ResGetCommunityThumbUpList resGetCommunityThumbUpList) {

                    }
                });

    }


    @Override
    public void deleteCommunityComments(String id) {
        ReqDeleteCommunityComments request = new ReqDeleteCommunityComments(id);
        mModel.deleteCommunityComments(request)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {

                    }
                });
    }

    @Override
    public void deleteCommunityThumbUp(String id) {
        ReqDeleteCommunityThumbUp request = new ReqDeleteCommunityThumbUp(id);
        mModel.deleteCommunityThumbUp(request)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {

                    }
                });
    }
}
