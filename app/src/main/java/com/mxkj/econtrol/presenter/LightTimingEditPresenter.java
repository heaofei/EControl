package com.mxkj.econtrol.presenter;

import com.google.gson.Gson;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;
import com.mxkj.econtrol.bean.response.ResUserLogin;
import com.mxkj.econtrol.contract.LightTimingEditContract;
import com.mxkj.econtrol.contract.LightTimingListContract;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class LightTimingEditPresenter implements LightTimingEditContract.Presenter {
    private LightTimingEditContract.View mView;
    private LightTimingEditContract.Model mModel;

    public LightTimingEditPresenter(LightTimingEditContract.View mView, LightTimingEditContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void scenePartOperatorTimerSave(ReqScenePartOperatorTimerSave reqScenePartOperatorTimerSave) {
//        ReqScenePartOperatorTimerSave reqScenePartOperatorTimerSave =  new ReqScenePartOperatorTimerSave();
        mModel.scenePartOperatorTimerSave(reqScenePartOperatorTimerSave)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.scenePartOperatorTimerSaveFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.scenePartOperatorTimerSaveSuccess(baseResponse);
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
    @Override
    public void start() {

    }
}
