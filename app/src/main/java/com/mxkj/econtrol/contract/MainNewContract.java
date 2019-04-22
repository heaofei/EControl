package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTotal;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public interface MainNewContract {
    interface View extends BaseView<Presenter> {
        //房间名的修改成功
        void sceneEditSuccess(BaseResponse baseResponse);
        //房间名的修改失败
        void sceneEditFali(String msg);
    }

    interface Presenter extends BasePresenter {
        // 房间名的修改
        void sceneEdit(String id, String name);

    }

    interface Model extends BaseModel {
      //房间名的修改
        Observable<BaseResponse> sceneEdit(ReqSceneEdit reqSceneEdit);
    }

}
