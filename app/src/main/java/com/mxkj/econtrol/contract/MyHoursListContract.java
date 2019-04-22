package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqSetHouseDefault;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResSetDefaultUserHouse;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/2
 *
 * @Description:
 */

public interface MyHoursListContract {
    interface View extends BaseView<Presenter> {
        //获取我的家列表成功
        void getHousingEstateListSuccess(ResGetUserHouseList resGetUserHouseList);

        //获取我的家列表失败
        void getHousingEstateListFail(String msg);

        //修改默认住宅成功
        void setDefaultUserHouseSuccess(ResSetDefaultUserHouse resSetDefaultUserHouse);

        //修改默认住宅失败
        void setDefaultUserHouseFail(String msg);

        //获取某个房子的id
        String getHouseId();

        //自己退出房子
        void unbindHouseUser4RejectSuccess();  //删除已拒绝的请求

        void unbindHouseUser4RejectFail(String msg);

    }

    interface Presenter extends BasePresenter {
        //获取我的家列表
        void getHousingEstateList();

        //修改默认住宅
        void setDefaultUserHouse();

        /**
         * 自己退出房子
         * @param houseUserId
         */
        void unbindHouseUser4Reject(String houseUserId);

    }

    interface Model extends BaseModel {
        //获取我的家列表
        Observable<ResGetUserHouseList> getHousingEstateList(ReqGetUserHouseList reqGetUserHouseList);
        //修改默认住宅
        Observable<ResSetDefaultUserHouse> setDefaultUserHouse(ReqSetHouseDefault reqSetHouseDefault);

        //自己退出房子
        Observable<BaseResponse> unbindHouseUser4Reject(ReqUnbindHouseUser4Reject request);
    }

}
