package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public interface HousePartInfoContract {

    interface View extends BaseView<InitailContract.Presenter> {

        /**
         * @Desc:获取成功
         * @author:liangshan
         */
        void getHouseAllPartListSuccess(ResGetHouseAllPartList resGetHouseAllPartList);

        /**
         * @Desc:获取失败
         * @author:liangshan
         */
        void getHouseAllPartListFail(String msg);


    }

    interface Presenter extends BasePresenter {

        /**
         * @Desc: 获取房子所有的智能设备部件信息
         * @author:liangshan
         */
        void getHouseAllPartList(String houseId);
    }

    interface Model extends BaseModel {
        /**
         * @Desc: 获取房子所有的智能设备部件信息
         * @author:liangshan
         */
        Observable<ResGetHouseAllPartList> getHouseAllPartList(ReqGetHouseAllPartList reqGetHouseAllPartList);
    }
}
