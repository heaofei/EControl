package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;
import com.mxkj.econtrol.bean.request.ReqScenePartTimerSave;
import com.mxkj.econtrol.contract.DiviceTimingEditContract;
import com.mxkj.econtrol.contract.LightTimingEditContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class DeviceTimingEditPresenter implements DiviceTimingEditContract.Presenter {
    private DiviceTimingEditContract.View mView;
    private DiviceTimingEditContract.Model mModel;

    public DeviceTimingEditPresenter(DiviceTimingEditContract.View mView, DiviceTimingEditContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void scenePartTimerSave(ReqScenePartTimerSave reqScenePartTimerSave) {
        ReqScenePartTimerSave reqScenePartOperatorTimerSave =  new ReqScenePartTimerSave();

        mModel.scenePartTimerSave(reqScenePartTimerSave)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.scenePartTimerSaveFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.scenePartTimerSaveSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void scenePartTimerDelete(String id) {
        ReqScenePartOperatorTimerDelete reqScenePartOperatorTimerDelete =  new ReqScenePartOperatorTimerDelete(id);

        mModel.scenePartTimerDelete(reqScenePartOperatorTimerDelete)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.scenePartTimerDeleteFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.scenePartTimerDeleteSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void start() {

    }
}
