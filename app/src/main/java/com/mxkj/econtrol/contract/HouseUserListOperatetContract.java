package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitch;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser;
import com.mxkj.econtrol.bean.request.ReqUserCheckBindHouse;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;

import java.util.List;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public interface HouseUserListOperatetContract {

    interface View extends BaseView<Presenter> {


        /**
         * @Desc: 解除绑定控制用户成功
         * @author:liangshan
         */
        void unbindHouseUserSuccess();

        /**
         * @Desc: 解除绑定控制用户失败
         * @author:liangshan
         */
        void unbindHouseUserFail(String msg);

        /**
         * @Desc: 转交管理权成功
         * @author:liangshan
         */
        void houseManagerSwitchSuccess();

        /**
         * @Desc: 转交管理权失败
         * @author:liangshan
         */
        void houseManagerSwitchFail(String msg);





    }

    interface Presenter extends BasePresenter {

        /**
         * @Desc: 解除绑定控制用户
         * @author:liangshan
         */
        void unbindHouseUser(String userId);

        /**
         * @Desc: 转交管理权
         * @author:liangshan
         */
        void houseManagerSwitch(String toUserId, String houseId, String isRemove);

    }

    interface Model extends BaseModel {

        /**
         * @Desc: 解除绑定控制用户
         * @author:liangshan
         */
        Observable<BaseResponse> unbindHouseUser(ReqUnbindHouseUser reqUnbindHouseUser);

        /**
         * @Desc: 转交管理权
         * @author:liangshan
         */
        Observable<BaseResponse> houseManagerSwitch(ReqHouseManagerSwitch request);


    }
}
