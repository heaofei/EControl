package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageTipRead;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;
import com.mxkj.econtrol.contract.MessageListContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class MessageListPresenter implements MessageListContract.Presenter {
    private MessageListContract.Model mModel;
    private MessageListContract.View mView;

    public MessageListPresenter(MessageListContract.View mView, MessageListContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }
    @Override
    public void getAppPushMessageList(ReqGetAppPushMessageList reqGetAppPushMessageList) {
        mModel.getAppPushMessageList(reqGetAppPushMessageList)
                .subscribe(new APISubcriber<ResGetAppPushMessageList>() {
                    @Override
                    public void onFail(ResGetAppPushMessageList baseResponse,String msg) {
                        mView.getAppPushMessageListFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetAppPushMessageList resGetAppPushMessageList) {
                        mView.getAppPushMessageListSuccess(resGetAppPushMessageList);
                    }
                });
    }

    @Override
    public void getSysAnnouncementPage(ReqGetSysAnnouncement reqGetSysAnnouncement) {
        mModel.getSysAnnouncementPage(reqGetSysAnnouncement)
                .subscribe(new APISubcriber<ResGetSysAnnouncement>() {
                    @Override
                    public void onFail(ResGetSysAnnouncement baseResponse,String msg) {
                        mView.getSysAnnouncementPageFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetSysAnnouncement resGetSysAnnouncement) {
                        mView.getSysAnnouncementPageSuccess(resGetSysAnnouncement);
                    }
                });
    }

    @Override
    public void appPushMessageTipRead(ReqAppPushMessageTipRead reqAppPushMessageTipRead) {
        mModel.appPushMessageTipRead(reqAppPushMessageTipRead)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.appPushMessageTipReadFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.appPushMessageTipReadSuccess(baseResponse);
                    }
                });
    }

    /****
     * 清空消息列表
     */
    @Override
    public void deleteAppPushMessage( ) {
        mModel.deleteAppPushMessage()
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.deleteAppPushMessageFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.deleteAppPushMessageSuccess(baseResponse);
                    }
                });
    }


}
