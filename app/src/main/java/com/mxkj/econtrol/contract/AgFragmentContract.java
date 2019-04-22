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
 * Created by ${  chenjun  } on 2018/2/5.
 */

public interface AgFragmentContract {


    interface View extends BaseView<Presenter>{

        //  修改部件状态（修改名字以及开关状态）成功
        void scenePartEditSuccess(BaseResponse baseResponse);
        //  修改部件状态（修改名字以及开关状态）失败
        void scenePartEditFail(String msg);

    }

    interface Presenter extends BasePresenter{

        // 修改部件状态（修改名字以及开关状态）
        void scenePartEdit(String DeviceId ,String DeviceName,String stateTimer);

    }

    interface Model extends BaseModel{

        // 修改部件状态（修改名字以及开关状态）
        Observable<BaseResponse> scenePartEdit(ReqScenePartEdit request);

    }

}
