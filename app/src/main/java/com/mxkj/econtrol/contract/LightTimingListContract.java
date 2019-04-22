package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.request.ReqSscenePartOperatorTimerStatus;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2017/11/2.
 */

public interface LightTimingListContract {


    interface View extends BaseView<Presenter>{


        // 获取场景的部件设备-操作的定时列表信息成功
        void getScenePartOperatorTimerListSuccess(ResGetScenePartOperatorTimerList resGetScenePartOperatorTimerList);

         // 获取场景的部件设备-操作的定时列表信息 失败
        void getScenePartOperatorTimerListFail(String msg);


        // 场景的部件设备操作定时器状态修改成功
        void scenePartOperatorTimerStatusChangeSuccess(BaseResponse baseResponse);

        //场景的部件设备操作定时器状态修改失败
        void scenePartOperatorTimerStatusChangeFail(String msg);


        // 场景的部件设备操作定时器删除成功
        void scenePartOperatorTimerDeleteSuccess(BaseResponse baseResponse);

        //场景的部件设备操作定时器删除失败
        void scenePartOperatorTimerDeleteFail(String msg);

    }

    interface Presenter extends BasePresenter{

        // 获取场景的部件设备-操作的定时列表信息
        void getScenePartOperatorTimerList(String operatorId);

        // 场景的部件设备操作定时器状态修改
        void scenePartOperatorTimerStatusChange(String id,String status);

        // 场景的部件设备操作定时器删除
        void scenePartOperatorTimerDelete(String id);

    }

    interface Model extends BaseModel{

        //获取场景的部件设备-操作的定时列表信息
        Observable<ResGetScenePartOperatorTimerList> getScenePartOperatorTimerList(ReqGetScenePartOperatorTimerList request);

        // 场景的部件设备操作定时器状态修改
        Observable<BaseResponse> scenePartOperatorTimerStatusChange(ReqSscenePartOperatorTimerStatus request);

        // // 场景的部件设备操作定时器删除
        Observable<BaseResponse> scenePartOperatorTimerDelete(ReqScenePartOperatorTimerDelete request);

    }

}
