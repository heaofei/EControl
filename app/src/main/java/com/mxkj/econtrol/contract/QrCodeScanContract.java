package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetAreaList;
import com.mxkj.econtrol.bean.request.ReqGetBuildingList;
import com.mxkj.econtrol.bean.request.ReqGetHouseList;
import com.mxkj.econtrol.bean.request.ReqGetHousingEstateList;
import com.mxkj.econtrol.bean.request.ReqUserApplyBindHouse;
import com.mxkj.econtrol.bean.response.Building;
import com.mxkj.econtrol.bean.response.City;
import com.mxkj.econtrol.bean.response.House;
import com.mxkj.econtrol.bean.response.HousingEstat;
import com.mxkj.econtrol.bean.response.ResGetAreaLsit;
import com.mxkj.econtrol.bean.response.ResGetBuildingList;
import com.mxkj.econtrol.bean.response.ResGetHouseList;
import com.mxkj.econtrol.bean.response.ResGetHousingEstateList;

import java.util.List;

import rx.Observable;

/**
 * Created by chan
 *
 * @Description:
 */

public interface QrCodeScanContract {

    interface View extends BaseView<Presenter> {

        //用户提交绑定房子申请成功
        void userApplyBindHouseSuccess();
        //用户提交绑定房子申请失败
        void userApplyBindHouseFail(String msg);

    }

    interface Presenter extends BasePresenter {
        //用户提交绑定房子申请接口
        void userApplyBindHouse(ReqUserApplyBindHouse reqUserApplyBindHouse);
    }

    interface Model extends BaseModel {
        //用户提交绑定房子申请接口
        Observable<BaseResponse> userApplyBindHouse(ReqUserApplyBindHouse reqUserApplyBindHouse);
    }
}