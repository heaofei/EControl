package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetScenePartDetail;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2018/1/24.
 */

public interface LockFragmentContract {


    interface View extends BaseView<Presenter>{

        String getDeviceId();

        // 获取设备详情
        void getScenePartDetailSuccess(ResGetScenePartDetail resGetScenePartDetail);
        // 获取设备失败
        void getScenePartDetailFail(String msg);

    }

    interface Presenter extends BasePresenter{

        // 获取部件详情接口
        void getScenePartDetail(String id);


    }

    interface Model extends BaseModel{

        //获取部件详情接口
        Observable<ResGetScenePartDetail> getScenePartDetail(ReqGetScenePartDetail reqGetScenePartDetail);


    }

}
