package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitch;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.request.ReqUserCheckBindHouse;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;

import java.util.List;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public interface HouseUserListContract {

    interface View extends BaseView<Presenter> {
        /**
         * @Desc: 获取房子控制用户列表失败
         * @author:liangshan
         */
        void getHouseUserListFail(String msg);

        /**
         * @Desc: 获取房子控制用户成功
         * @author:liangshan
         */
        void getHouseUserListSuccess(List<HouseUser> houseUsers);

        //自己退出房子
        void unbindHouseUser4RejectSuccess();  //删除已拒绝的请求

        void unbindHouseUser4RejectFail(String msg);


        //获取申请列表成功
        void getApplyBindHouseListSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList);

        //获取申请列表失败
        void getApplyBindHouseListFail(String msg);

    }

    interface Presenter extends BasePresenter {
        /**
         * @Desc: 获取房子控制用户
         * @author:liangshan
         */
        void getHouseUserList(String houseId);

        /**
         * 自己退出房子
         * @param houseUserId
         */
        void unbindHouseUser4Reject(String houseUserId);

        // 获取申请列表
        void getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList);
    }

    interface Model extends BaseModel {
        /**
         * @Desc: 获取房子控制用户列表
         * @author:liangshan
         */
        Observable<ResGetHouseUserList> getHouseUserList(ReqGetHouseUserList reqGetHouseUserList);

        //自己退出房子
        Observable<BaseResponse> unbindHouseUser4Reject(ReqUnbindHouseUser4Reject request);


        // 获取申请列表
        Observable<ResGetApplyBindHouseList> getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList);

    }
}
