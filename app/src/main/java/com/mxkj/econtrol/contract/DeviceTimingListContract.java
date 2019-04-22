package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqSscenePartOperatorTimerStatus;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTimerList;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2017/11/2.
 */

public interface DeviceTimingListContract {


    interface View extends BaseView<Presenter>{



        // 获取设备全部定时列表成功
        void getScenePartTimerListSuccess(ResGetScenePartTimerList baseResponse);

        //获取设备全部定时列表失败
        void getScenePartTimerListFail(String msg);


        // 设备定时任务开启或关闭成功
        void scenePartTimerStatusChangeSuccess(BaseResponse baseResponse);

        //设备定时任务开启或关闭失败
        void scenePartTimerStatusChangeFail(String msg);


    }

    interface Presenter extends BasePresenter{

        // 获取设备全部定时列表
        void getScenePartTimerList(int page, int  rows,String houseId);

        // 设备定时任务开启或关闭
        void scenePartTimerStatusChange(String id, String status);



    }

    interface Model extends BaseModel{

        //获取设备全部定时列表
        Observable<ResGetScenePartTimerList> getScenePartTimerList(ReqGetCommunityList reqGetCommunityList);

        // 设备定时任务开启或关闭
        Observable<BaseResponse> scenePartTimerStatusChange(ReqSscenePartOperatorTimerStatus request);


    }

}
