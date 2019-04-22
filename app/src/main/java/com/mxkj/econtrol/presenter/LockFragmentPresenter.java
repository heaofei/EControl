package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartDetail;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.contract.LightSettingContract;
import com.mxkj.econtrol.contract.LockFragmentContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chan on 2018/1/24.
 *
 * @Description：
 */

public class LockFragmentPresenter implements LockFragmentContract.Presenter {
    private LockFragmentContract.View mView;
    private LockFragmentContract.Model mModel;

    public LockFragmentPresenter(LockFragmentContract.View mView, LockFragmentContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

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


}
