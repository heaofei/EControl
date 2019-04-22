package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.request.ReqUserMoodModify;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        //获取我的家列表成功
        void getHousingEstateListSuccess(ResGetUserHouseList resGetUserHouseList);

        //获取我的家列表失败
        void getHousingEstateListFail(String msg);

        //用户心情修改成功
        void userMoodModifySuccess();

        //用户心情修改失败
        void userMoodModifyFail(String msg);

        //删除已拒绝的请求
        void unbindHouseUser4RejectSuccess();  //删除已拒绝的请求

        void unbindHouseUser4RejectFail(String msg);

    }

    interface Presenter extends BasePresenter {
        //获取我的家列表
        void getHousingEstateList();

        //用户心情修改
        void userMoodModify(String mood);

        //删除已拒绝的请求
        void unbindHouseUser4Reject(String houseUserId);
    }

    interface Model extends BaseModel {

        //获取我的家列表
        Observable<ResGetUserHouseList> getHousingEstateList(ReqGetUserHouseList reqGetUserHouseList);

        //用户心情修改
        Observable<BaseResponse> userMoodModify(ReqUserMoodModify reqUserMoodModify);

        //删除已拒绝的请求
        Observable<BaseResponse> unbindHouseUser4Reject(ReqUnbindHouseUser4Reject request);
    }

}
