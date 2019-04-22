package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2017/11/2.
 */

public interface LightTimingEditContract {


    interface View extends BaseView<Presenter>{


        // 场景的部件设备操作定时器保存成功
        void scenePartOperatorTimerSaveSuccess(BaseResponse baseResponse);

         // 场景的部件设备操作定时器保存 失败
        void scenePartOperatorTimerSaveFail(String msg);


        // 场景的部件设备操作定时器删除成功
        void scenePartOperatorTimerDeleteSuccess(BaseResponse baseResponse);

        //场景的部件设备操作定时器删除失败
        void scenePartOperatorTimerDeleteFail(String msg);


    }

    interface Presenter extends BasePresenter{

        // 场景的部件设备操作定时器保存
        void scenePartOperatorTimerSave(ReqScenePartOperatorTimerSave reqScenePartOperatorTimerSave);

        // 场景的部件设备操作定时器删除
        void scenePartOperatorTimerDelete(String id);


    }

    interface Model extends BaseModel{

        //场景的部件设备操作定时器保存
        Observable<BaseResponse> scenePartOperatorTimerSave(ReqScenePartOperatorTimerSave request);

        // // 场景的部件设备操作定时器删除
        Observable<BaseResponse> scenePartOperatorTimerDelete(ReqScenePartOperatorTimerDelete request);


    }

}
