package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncementDetail;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncementDetail;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2017/10/30.
 */

public interface GetSysAnnouncementDetailContract {

    interface View extends BaseView<MessageListContract.Presenter> {

        //系统通知详情成功
        void getSysAnnouncementDetailSuccess(ResGetSysAnnouncementDetail resGetSysAnnouncementDetail);
        //系统通知详情失败
        void getSysAnnouncementDetailFail(String msg);

    }

     interface Presenter extends BasePresenter {
        // 系统通知详情
        void getSysAnnouncementDetail(ReqGetSysAnnouncementDetail reqGetSysAnnouncementDetail);

    }

    interface Model extends BaseModel {
        // 系统通知详情
        Observable<ResGetSysAnnouncementDetail> getSysAnnouncementDetail(ReqGetSysAnnouncementDetail reqGetSysAnnouncementDetail);

    }
}
