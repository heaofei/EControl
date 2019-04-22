package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserInfo;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser4Reject;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.bean.response.ResGethouseUserInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by chan on 2017/4/17.
 *
 * @Description：
 */

public interface HouseUserInfoContract {

    interface View extends BaseView<Presenter> {

        /***
         * 获取房子控制用户信息成功
         */
        void getHouseUserInfoSuccess(ResGethouseUserInfo resGethouseUserInfo);
        /***
         * 获取房子控制用户信息失败
         */
        void getHouseUserInfoFail(String msg);

    }

    interface Presenter extends BasePresenter {
        /***
         * 获取房子控制用户信息
         */
        void getHouseUserInfo(String string);

    }

    interface Model extends BaseModel {
        /**
         * @Desc:  获取房子控制用户信息
         *
         */
        Observable<ResGethouseUserInfo> getHouseUserInfo(ReqGetHouseUserInfo reqGetHouseUserInfo);

    }


}
