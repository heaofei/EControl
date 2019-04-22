package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartList;
import com.mxkj.econtrol.bean.response.ResGetScenePartList;
import com.mxkj.econtrol.contract.RoomContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description：
 */

public class RoomPresenter implements RoomContract.Presenter {
    private RoomContract.View mView;
    private RoomContract.Model mModel;

    public RoomPresenter(RoomContract.View mView, RoomContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getScenePartList(String sceneId) {
        APISubcriber<ResGetScenePartList> apiSubcriber = new APISubcriber<ResGetScenePartList>() {
            @Override
            public void onFail(ResGetScenePartList baseResponse, String msg) {
                mView.getScenePartListFail("");
            }

            @Override
            public void onSuccess(ResGetScenePartList resGetScenePartList) {
                mView.getScenePartListSuccess(resGetScenePartList);
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.getScenePartList(new ReqGetScenePartList(sceneId))
                .subscribe(apiSubcriber);
    }

}
