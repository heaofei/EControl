package com.mxkj.econtrol.presenter;

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
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chan on 2018/1/4.
 *
 * @Description:
 */

public class ApplyBindHouseListPresenter implements ApplyBindHouseListContract.Presenter {
    private ApplyBindHouseListContract.Model mModel;
    private ApplyBindHouseListContract.View mView;

    public ApplyBindHouseListPresenter(ApplyBindHouseListContract.View mView, ApplyBindHouseListContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList) {
        mModel.getApplyBindHouseList(reqGetApplyBindHouseList)
                .subscribe(new APISubcriber<ResGetApplyBindHouseList>() {
                    @Override
                    public void onFail(ResGetApplyBindHouseList baseResponse,String msg) {
                        mView.getApplyBindHouseListFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList) {
                        mView.getApplyBindHouseListSuccess(resGetApplyBindHouseList);
                    }
                });
    }

    @Override
    public void applyBindHouseAudit(ReqApplyBindHouseAudit reqApplyBindHouseAudit) {
        mModel.applyBindHouseAudit(reqApplyBindHouseAudit)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.applyBindHouseAuditFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.applyBindHouseAuditSuccess(baseResponse);
                    }
                });
    }


}
