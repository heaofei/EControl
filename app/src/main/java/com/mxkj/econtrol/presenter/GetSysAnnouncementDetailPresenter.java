package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncementDetail;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncementDetail;
import com.mxkj.econtrol.contract.GetSysAnnouncementDetailContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class GetSysAnnouncementDetailPresenter implements GetSysAnnouncementDetailContract.Presenter {
    private GetSysAnnouncementDetailContract.Model mModel;
    private GetSysAnnouncementDetailContract.View mView;

    public GetSysAnnouncementDetailPresenter(GetSysAnnouncementDetailContract.View mView, GetSysAnnouncementDetailContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }
    @Override
    public void getSysAnnouncementDetail(ReqGetSysAnnouncementDetail reqGetSysAnnouncementDetail) {
        mModel.getSysAnnouncementDetail(reqGetSysAnnouncementDetail)
                .subscribe(new APISubcriber<ResGetSysAnnouncementDetail>() {
                    @Override
                    public void onFail(ResGetSysAnnouncementDetail baseResponse, String msg) {
                        mView.getSysAnnouncementDetailFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetSysAnnouncementDetail resGetSysAnnouncementDetail) {
                        mView.getSysAnnouncementDetailSuccess(resGetSysAnnouncementDetail);
                    }
                });
    }
}
