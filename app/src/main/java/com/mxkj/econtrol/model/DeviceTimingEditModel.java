package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;
import com.mxkj.econtrol.bean.request.ReqScenePartTimerSave;
import com.mxkj.econtrol.contract.DiviceTimingEditContract;
import com.mxkj.econtrol.contract.LightTimingEditContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class DeviceTimingEditModel implements DiviceTimingEditContract.Model {

    /*****设备，定时新增或编辑***/
    @Override
    public Observable<BaseResponse> scenePartTimerSave(ReqScenePartTimerSave request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartTimerSave(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    /****设备，定时删除**/
    @Override
    public Observable<BaseResponse> scenePartTimerDelete(ReqScenePartOperatorTimerDelete request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartTimerDelete(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
