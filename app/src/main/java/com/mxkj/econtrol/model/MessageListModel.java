package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageTipRead;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;
import com.mxkj.econtrol.contract.MessageListContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class MessageListModel implements MessageListContract.Model {

    /**系统通知列表***/
    @Override
    public Observable<ResGetSysAnnouncement> getSysAnnouncementPage(ReqGetSysAnnouncement reqGetSysAnnouncement) {
        return RetrofitUtil.getInstance().getmApiService()
                .GetSysAnnouncementPage(reqGetSysAnnouncement.toJsonStr())
                .compose(new APITransFormer<ResGetSysAnnouncement>());
    }

    /**获取消息推送列表***/
    @Override
    public Observable<ResGetAppPushMessageList> getAppPushMessageList(ReqGetAppPushMessageList reqGetAppPushMessageList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getAppPushMessageList(reqGetAppPushMessageList.toJsonStr())
                .compose(new APITransFormer<ResGetAppPushMessageList>());
    }
    /**设置消息已读接口***/
    @Override
    public Observable<BaseResponse> appPushMessageTipRead(ReqAppPushMessageTipRead reqAppPushMessageTipRead) {
        return RetrofitUtil.getInstance().getmApiService()
                .appPushMessageTipRead(reqAppPushMessageTipRead.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    /**清空消息列表***/
    @Override
    public Observable<BaseResponse> deleteAppPushMessage() {
        return RetrofitUtil.getInstance().getmApiService()
                .deleteAppPushMessage("{}")
                .compose(new APITransFormer<BaseResponse>());
    }

}
