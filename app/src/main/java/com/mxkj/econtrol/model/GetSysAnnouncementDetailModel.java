package com.mxkj.econtrol.model;

import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncement;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncementDetail;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncementDetail;
import com.mxkj.econtrol.contract.GetSysAnnouncementDetailContract;
import com.mxkj.econtrol.contract.MessageListContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2017/10/30.
 *
 * @Description:
 */

public class GetSysAnnouncementDetailModel implements GetSysAnnouncementDetailContract.Model {


    /**Ov获取系统通知详情*/
    public Observable<ResGetSysAnnouncementDetail> getSysAnnouncementDetail(ReqGetSysAnnouncementDetail reqGetSysAnnouncementDetail) {
        return RetrofitUtil.getInstance().getmApiService()
                .GetSysAnnouncementDetail(reqGetSysAnnouncementDetail.toJsonStr())
                .compose(new APITransFormer<ResGetSysAnnouncementDetail>());
    }
}
