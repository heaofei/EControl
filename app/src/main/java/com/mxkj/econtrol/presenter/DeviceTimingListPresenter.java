package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqSscenePartOperatorTimerStatus;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTimerList;
import com.mxkj.econtrol.contract.DeviceTimingListContract;
import com.mxkj.econtrol.contract.LightTimingListContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class DeviceTimingListPresenter implements DeviceTimingListContract.Presenter {
    private DeviceTimingListContract.View mView;
    private DeviceTimingListContract.Model mModel;

    public DeviceTimingListPresenter(DeviceTimingListContract.View mView, DeviceTimingListContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    /****获取设备所有定时列表**/
    @Override
    public void getScenePartTimerList(int page, int rows,String houstid) {
        ReqGetCommunityList reqGetCommunityList = new ReqGetCommunityList(page,rows);
        reqGetCommunityList.setHouseId(houstid);
        reqGetCommunityList.setUserId(APP.user.getUserId());

        mModel.getScenePartTimerList(reqGetCommunityList)
                .subscribe(new APISubcriber<ResGetScenePartTimerList>() {
                    @Override
                    public void onFail(ResGetScenePartTimerList baseResponse,String msg) {
                        mView.getScenePartTimerListFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetScenePartTimerList baseResponse) {
                        mView.getScenePartTimerListSuccess(baseResponse);
                    }
                });
    }

    /***设备定时任务开启/关闭**/
    @Override
    public void scenePartTimerStatusChange(String id, String status) {
        ReqSscenePartOperatorTimerStatus reqSscenePartOperatorTimerStatus = new ReqSscenePartOperatorTimerStatus(id,status);
        mModel.scenePartTimerStatusChange(reqSscenePartOperatorTimerStatus)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.scenePartTimerStatusChangeFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.scenePartTimerStatusChangeSuccess(baseResponse);
                    }
                });
    }

}
