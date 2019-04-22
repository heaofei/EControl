package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.request.ReqSscenePartOperatorTimerStatus;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.contract.LightTimingListContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class LightTimingListModel implements LightTimingListContract.Model {

    @Override
    public Observable<ResGetScenePartOperatorTimerList> getScenePartOperatorTimerList(ReqGetScenePartOperatorTimerList request) {
        return RetrofitUtil.getInstance().getmApiService()
                .getScenePartOperatorTimerList(request.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartOperatorTimerList>());
    }

    @Override
    public Observable<BaseResponse> scenePartOperatorTimerStatusChange(ReqSscenePartOperatorTimerStatus request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartOperatorTimerStatusChange(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> scenePartOperatorTimerDelete(ReqScenePartOperatorTimerDelete request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartOperatorTimerDelete(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
