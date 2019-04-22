package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.request.ReqSscenePartOperatorTimerStatus;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.contract.LightTimingListContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class LightTimingListPresenter implements LightTimingListContract.Presenter {
    private LightTimingListContract.View mView;
    private LightTimingListContract.Model mModel;

    public LightTimingListPresenter(LightTimingListContract.View mView, LightTimingListContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getScenePartOperatorTimerList(String operatorId) {
        ReqGetScenePartOperatorTimerList reqGetScenePartOperatorTimerList = new ReqGetScenePartOperatorTimerList(operatorId);
        mModel.getScenePartOperatorTimerList(reqGetScenePartOperatorTimerList)
                .subscribe(new APISubcriber<ResGetScenePartOperatorTimerList>() {
                    @Override
                    public void onFail(ResGetScenePartOperatorTimerList baseResponse,String msg) {
                        mView.getScenePartOperatorTimerListFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetScenePartOperatorTimerList baseResponse) {
                        mView.getScenePartOperatorTimerListSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void scenePartOperatorTimerStatusChange(String id ,String status) {
        ReqSscenePartOperatorTimerStatus reqSscenePartOperatorTimerStatus = new ReqSscenePartOperatorTimerStatus(id,status);
        mModel.scenePartOperatorTimerStatusChange(reqSscenePartOperatorTimerStatus)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.scenePartOperatorTimerStatusChangeFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.scenePartOperatorTimerStatusChangeSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void scenePartOperatorTimerDelete(String id) {
        ReqScenePartOperatorTimerDelete reqScenePartOperatorTimerDelete = new ReqScenePartOperatorTimerDelete(id);
        mModel.scenePartOperatorTimerDelete(reqScenePartOperatorTimerDelete)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.scenePartOperatorTimerDeleteFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.scenePartOperatorTimerDeleteSuccess(baseResponse);
                    }
                });
    }
}
