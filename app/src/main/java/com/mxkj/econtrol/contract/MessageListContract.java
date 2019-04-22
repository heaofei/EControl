package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageReply;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageTipRead;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetSceneList;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;

import java.util.List;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/13.
 *
 * @Description:
 */

public interface MessageListContract {
    interface View extends BaseView<Presenter> {

        //获取HouseId
        String getHouseId();

        //获取消息推送列表成功
        void getAppPushMessageListSuccess(ResGetAppPushMessageList resGetAppPushMessageList);

        //获取消息推送列表失败
        void getAppPushMessageListFail(String msg);

       //系统通知列表成功
        void getSysAnnouncementPageSuccess(ResGetSysAnnouncement resGetSysAnnouncement);

        //系统通知列表失败
        void getSysAnnouncementPageFail(String msg);

         //设置消息已读接口成功
        void appPushMessageTipReadSuccess(BaseResponse response);

        //设置消息已读接口失败
        void appPushMessageTipReadFail(String msg);

          //清空消息列表成功
        void deleteAppPushMessageSuccess(BaseResponse response);

        //清空消息列表失败
        void deleteAppPushMessageFail(String msg);


    }

    interface Presenter extends BasePresenter {

        //获取消息推送列表
        void getAppPushMessageList(ReqGetAppPushMessageList reqGetAppPushMessageList);

        //清空消息列表
        void deleteAppPushMessage( );

        // 系统通知列表
        void getSysAnnouncementPage(ReqGetSysAnnouncement reqGetSysAnnouncement);

        // 设置消息已读接口
        void appPushMessageTipRead(ReqAppPushMessageTipRead reqAppPushMessageTipRead);


    }

    interface Model extends BaseModel {

        // 系统通知列表
        Observable<ResGetSysAnnouncement> getSysAnnouncementPage(ReqGetSysAnnouncement reqGetSysAnnouncement);

        //获取消息推送列表
        Observable<ResGetAppPushMessageList> getAppPushMessageList(ReqGetAppPushMessageList reqGetAppPushMessageList);

        // 设置消息已读接口
        Observable<BaseResponse> appPushMessageTipRead(ReqAppPushMessageTipRead reqAppPushMessageTipRead);

        //清空消息列表
        Observable<BaseResponse> deleteAppPushMessage( );

    }

}
