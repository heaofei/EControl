package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.contract.InformationCommentContract;
import com.mxkj.econtrol.contract.InformationContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class InformationCommentPresenter implements InformationCommentContract.Presenter {
    private InformationCommentContract.Model mModel;
    private InformationCommentContract.View mView;

    public InformationCommentPresenter(InformationCommentContract.View mView, InformationCommentContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }
    @Override
    public void getCommunityCommentsMyList(int page, int rows, String userId, String type) {

        ReqGetCommunityCommentsMyList reqGetCommunityCommentsMyList = new ReqGetCommunityCommentsMyList();
        reqGetCommunityCommentsMyList.setPage(page);
        reqGetCommunityCommentsMyList.setRows(rows);
        reqGetCommunityCommentsMyList.setUserId(userId);
        reqGetCommunityCommentsMyList.setType(type);

        mModel.getCommunityCommentsMyList(reqGetCommunityCommentsMyList)
                .subscribe(new APISubcriber<ResGetCommunityCommentsMyList>() {
                    @Override
                    public void onFail(ResGetCommunityCommentsMyList baseResponse,String msg) {
                        mView.getCommunityCommentsMyListFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetCommunityCommentsMyList resGetCommunityList) {
                        mView.getCommunityCommentsMyListSuccess(resGetCommunityList);
                    }
                });
    }
}
