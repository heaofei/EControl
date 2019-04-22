package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetScenePartDetail;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2017/11/2.
 */

public interface LightSettingContract {


    interface View extends BaseView<Presenter>{

        String getDeviceId();

        String getDeviceName();

        // 获取定时详情
        void getScenePartDetailSuccess(ResGetScenePartDetail resGetScenePartDetail);
        // 获取定时列表失败
        void getScenePartDetailFail(String msg);

        //  修改部件状态（修改名字以及开关状态）成功
        void scenePartEditSuccess(BaseResponse baseResponse);
        //  修改部件状态（修改名字以及开关状态）失败
        void scenePartEditFail(String msg);

        //  获取定时打开列表成功
        void getTimerOpenListSuccess(ResGetScenePartOperatorTimerList resGetScenePartOperatorTimerList);
        //  获取定时打开列表成功
        void getTimerCloseListSuccess(ResGetScenePartOperatorTimerList resGetScenePartOperatorTimerList);

    }

    interface Presenter extends BasePresenter{

        // 获取定时打开列表
        void getTimerOpenList(String operatorId);
        // 获取定时关闭列表
        void getTimerCloseList(String operatorId);

        // 获取部件详情接口
        void getScenePartDetail(String id);

        // 修改部件状态（修改名字以及开关状态）
        void scenePartEdit(String stateTimer);

    }

    interface Model extends BaseModel{

        // 获取定时打开列表
        Observable<ResGetScenePartOperatorTimerList> getTimerOpenList(ReqGetScenePartOperatorTimerList request);
        // 获取定时关闭列表
        Observable<ResGetScenePartOperatorTimerList> getTimerCloseList(ReqGetScenePartOperatorTimerList request);

        //获取部件详情接口
        Observable<ResGetScenePartDetail> getScenePartDetail(ReqGetScenePartDetail reqGetScenePartDetail);

        // 修改部件状态（修改名字以及开关状态）
        Observable<BaseResponse> scenePartEdit(ReqScenePartEdit request);

    }

}
