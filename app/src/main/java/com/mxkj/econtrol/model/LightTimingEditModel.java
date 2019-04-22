package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;
import com.mxkj.econtrol.contract.LightTimingEditContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class LightTimingEditModel implements LightTimingEditContract.Model {


    @Override
    public Observable<BaseResponse> scenePartOperatorTimerSave(ReqScenePartOperatorTimerSave request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartOperatorTimerSave(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<BaseResponse> scenePartOperatorTimerDelete(ReqScenePartOperatorTimerDelete request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartOperatorTimerDelete(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
