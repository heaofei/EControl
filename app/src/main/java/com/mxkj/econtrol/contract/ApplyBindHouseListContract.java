package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqApplyBindHouseAudit;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;

import rx.Observable;

/**
 * Created by liangshan on 2018/1/4.
 *
 * @Description:
 */

public interface ApplyBindHouseListContract {
    interface View extends BaseView<Presenter> {


        //获取申请列表成功
        void getApplyBindHouseListSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList);

        //获取申请列表失败
        void getApplyBindHouseListFail(String msg);

       //审核申请接口成功
        void applyBindHouseAuditSuccess(BaseResponse baseResponse);

        //审核申请接口失败
        void applyBindHouseAuditFail(String msg);





    }

    interface Presenter extends BasePresenter {

        // 获取申请列表
        void getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList);

        //  审核申请接口
        void applyBindHouseAudit(ReqApplyBindHouseAudit reqApplyBindHouseAudit);


    }

    interface Model extends BaseModel {

        // 获取申请列表
        Observable<ResGetApplyBindHouseList> getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList);

        //  审核申请接口
        Observable<BaseResponse> applyBindHouseAudit(ReqApplyBindHouseAudit reqApplyBindHouseAudit);

    }

}
