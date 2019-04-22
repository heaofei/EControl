package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartDetail;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.contract.LightSettingContract;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.utils.LogUtil;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class LightSettingPresenter implements LightSettingContract.Presenter {
    private LightSettingContract.View mView;
    private LightSettingContract.Model mModel;

    public LightSettingPresenter(LightSettingContract.View mView, LightSettingContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getTimerOpenList(String operatorId) {
        ReqGetScenePartOperatorTimerList reqGetScenePartOperatorTimerList = new ReqGetScenePartOperatorTimerList(operatorId);
        mModel.getTimerOpenList(reqGetScenePartOperatorTimerList)
                .subscribe(new APISubcriber<ResGetScenePartOperatorTimerList>() {
                    @Override
                    public void onFail(ResGetScenePartOperatorTimerList baseResponse,String msg) {
                        LogUtil.i("=======返回==请求打开列表失败");
                    }
                    @Override
                    public void onSuccess(ResGetScenePartOperatorTimerList baseResponse) {
                        mView.getTimerOpenListSuccess(baseResponse);
                    }
                });
    }
    @Override
    public void getTimerCloseList(String operatorId) {
        ReqGetScenePartOperatorTimerList reqGetScenePartOperatorTimerList = new ReqGetScenePartOperatorTimerList(operatorId);
        mModel.getTimerCloseList(reqGetScenePartOperatorTimerList)
                .subscribe(new APISubcriber<ResGetScenePartOperatorTimerList>() {
                    @Override
                    public void onFail(ResGetScenePartOperatorTimerList baseResponse,String msg) {
                        LogUtil.i("=======返回==请求关闭列表失败");
                    }
                    @Override
                    public void onSuccess(ResGetScenePartOperatorTimerList baseResponse) {
                        mView.getTimerCloseListSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void getScenePartDetail(String id) {
        mModel.getScenePartDetail(new ReqGetScenePartDetail(id))
                .subscribe(new APISubcriber<ResGetScenePartDetail>() {
                    @Override
                    public void onFail(ResGetScenePartDetail baseResponse,String msg) {
                        mView.getScenePartDetailFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetScenePartDetail resGetScenePartDetail) {
                        mView.getScenePartDetailSuccess(resGetScenePartDetail);
                    }
                });
    }

    @Override
    public void scenePartEdit(String stateTimer) {
        ReqScenePartEdit reqScenePartTimerStatusChange = new ReqScenePartEdit();
        reqScenePartTimerStatusChange.setId(mView.getDeviceId());
        reqScenePartTimerStatusChange.setStateTimer(stateTimer);
        reqScenePartTimerStatusChange.setName(mView.getDeviceName());

        mModel.scenePartEdit(reqScenePartTimerStatusChange)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.scenePartEditFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.scenePartEditSuccess(baseResponse);
                    }
                });
    }
}
