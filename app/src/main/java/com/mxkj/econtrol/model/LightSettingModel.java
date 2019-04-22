package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartDetail;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.contract.LightSettingContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class LightSettingModel implements LightSettingContract.Model {


    @Override
    public Observable<ResGetScenePartOperatorTimerList> getTimerOpenList(ReqGetScenePartOperatorTimerList request) {
        return RetrofitUtil.getInstance().getmApiService()
                .getScenePartOperatorTimerList(request.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartOperatorTimerList>());
    }

    @Override
    public Observable<ResGetScenePartOperatorTimerList> getTimerCloseList(ReqGetScenePartOperatorTimerList request) {
        return RetrofitUtil.getInstance().getmApiService()
                .getScenePartOperatorTimerList(request.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartOperatorTimerList>());
    }

    @Override
    public Observable<ResGetScenePartDetail> getScenePartDetail(ReqGetScenePartDetail request) {
        return RetrofitUtil.getInstance().getmApiService()
                .getScenePartDetail(request.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartDetail>());
    }


    @Override
    public Observable<BaseResponse> scenePartEdit(ReqScenePartEdit request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartEdit(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
