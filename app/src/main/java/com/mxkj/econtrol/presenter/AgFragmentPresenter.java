package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartDetail;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.contract.AgFragmentContract;
import com.mxkj.econtrol.contract.LightSettingContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chan on 2018/2/5.
 *
 * @Description：
 */

public class AgFragmentPresenter implements AgFragmentContract.Presenter {
    private AgFragmentContract.View mView;
    private AgFragmentContract.Model mModel;

    public AgFragmentPresenter(AgFragmentContract.View mView, AgFragmentContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void scenePartEdit(String id,String name,String stateTimer) {
        ReqScenePartEdit reqScenePartTimerStatusChange = new ReqScenePartEdit();
        reqScenePartTimerStatusChange.setId(id);
        reqScenePartTimerStatusChange.setStateTimer(stateTimer);
        reqScenePartTimerStatusChange.setName(name);

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
