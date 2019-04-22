package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.response.ResAppPushMessageTipCount;
import com.mxkj.econtrol.bean.response.ResAppTodoList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/03.
 *
 * @Description:
 */

public interface FragmentMyContract {
    interface View extends BaseView<Presenter> {

        //消息未读条数接口成功
        void appPushMessageTipCountSuccess(ResAppPushMessageTipCount resAppPushMessageTipCount);

        //消息未读条数接口失败
        void appPushMessageTipCountFali(String msg);


        void appTodoListSuccess(ResAppTodoList resAppTodoList);

        void appTodoListFali(String msg);

        //获取申请列表成功
        void getApplyBindHouseListSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList);
        //获取申请列表失败
        void getApplyBindHouseListFail(String msg);

    }

    interface Presenter extends BasePresenter {
        //消息未读条数接口
        void appPushMessageTipCount();
        //待处理提醒查询接口
        void appTodoList();


        // 获取申请列表
        void getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList);
    }

    interface Model extends BaseModel {

        //消息未读条数接口
        Observable<ResAppPushMessageTipCount> appPushMessageTipCount();

        // 待处理提醒查询接口
        Observable<ResAppTodoList> appTodoList();

        // 获取申请列表
        Observable<ResGetApplyBindHouseList> getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList);
    }

}
