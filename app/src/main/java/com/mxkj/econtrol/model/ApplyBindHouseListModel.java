package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqApplyBindHouseAudit;
import com.mxkj.econtrol.bean.request.ReqGetAppPushMessageList;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;
import com.mxkj.econtrol.contract.ApplyBindHouseListContract;
import com.mxkj.econtrol.contract.MessageListContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2018/1/4.
 *
 * @Description:
 */

public class ApplyBindHouseListModel implements ApplyBindHouseListContract.Model {

    /***获取申请列表**/
    @Override
    public Observable<ResGetApplyBindHouseList> getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getApplyBindHouseList(reqGetApplyBindHouseList.toJsonStr())
                .compose(new APITransFormer<ResGetApplyBindHouseList>());
    }
    /***审核申请接口**/
    @Override
    public Observable<BaseResponse> applyBindHouseAudit(ReqApplyBindHouseAudit reqApplyBindHouseAudit) {
        return RetrofitUtil.getInstance().getmApiService()
                .applyBindHouseAudit(reqApplyBindHouseAudit.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }


}
