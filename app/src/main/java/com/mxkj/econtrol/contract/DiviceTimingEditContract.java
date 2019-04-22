package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;
import com.mxkj.econtrol.bean.request.ReqScenePartTimerSave;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2017/11/2.
 */

public interface DiviceTimingEditContract {


    interface View extends BaseView<Presenter>{


        //设备，定时修改或新增保存成功
        void scenePartTimerSaveSuccess(BaseResponse baseResponse);

         // 设备，定时修改或新增保存 失败
        void scenePartTimerSaveFail(String msg);


        // 设备，定时删除成功
        void scenePartTimerDeleteSuccess(BaseResponse baseResponse);

        //设备，定时删除失败
        void scenePartTimerDeleteFail(String msg);


    }

    interface Presenter extends BasePresenter{

        // 设备，定时修改或新增保存
        void scenePartTimerSave(ReqScenePartTimerSave reqScenePartTimerSave);

        //  设备，定时删除
        void scenePartTimerDelete(String id);


    }

    interface Model extends BaseModel{

        //设备，定时修改或新增保存
        Observable<BaseResponse> scenePartTimerSave(ReqScenePartTimerSave request);

        // // 设备，定时删除
        Observable<BaseResponse> scenePartTimerDelete(ReqScenePartOperatorTimerDelete request);


    }

}
