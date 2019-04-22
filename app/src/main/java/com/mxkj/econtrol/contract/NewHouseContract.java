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
 * Created by liangshan on 2017/4/12.
 *
 * @Description:
 */

public interface NewHouseContract {

    interface View extends BaseView<Presenter> {
        //获取地区列表信息成功
        void getAreaListSuccess(List<City> citys);

        //获取地区列表信息失败
        void getAreaListFail(String msg);

        //获取小区列表信息成功
        void getHousingEstateListSuccess(List<HousingEstat> housingEstats);

        //获取小区列表信息失败
        void getHousingEstateListFail(String msg);

        //获取楼盘单位列表信息成功
        void getBuildingListSuccess(List<Building> buildings);

        //获取楼盘单位列表信息失败
        void getBuildingListFail(String msg);

        //获取房子列表信息成功
        void getHouseListSuccess(List<House> houses);

        //获取房子列表信息失败
        void getHouseListFail(String msg);

        //用户提交绑定房子申请成功
        void userApplyBindHouseSuccess();

        //用户提交绑定房子申请失败
        void userApplyBindHouseFail(String msg);


    }

    interface Presenter extends BasePresenter {
        //获取地区列表信息
        void getAreaList(String areaCode);

        //获取小区列表信息
        void getHousingEstateList(ReqGetHousingEstateList reqGetHousingEstateList);

        //获取楼盘单位列表信息
        void getBuildingList(ReqGetBuildingList reqGetBuildingAndHouseList);

        //获取房子列表信息
        void getHouseList(ReqGetHouseList reqGetBuildingAndHouseList);

        //用户提交绑定房子申请接口
        void userApplyBindHouse(ReqUserApplyBindHouse reqUserApplyBindHouse);

    }

    interface Model extends BaseModel {

        //获取地区列表信息
        Observable<ResGetAreaLsit> getAreaList(ReqGetAreaList reqGetAreaList);

        //获取小区列表信息
        Observable<ResGetHousingEstateList> getHousingEstateList(ReqGetHousingEstateList reqGetHousingEstateList);

        //获取楼盘单位列表信息
        Observable<ResGetBuildingList> getBuildingList(ReqGetBuildingList reqGetBuildingAndHouseList);

        //获取房子列表信息
        Observable<ResGetHouseList> getHouseList(ReqGetHouseList reqGetBuildingAndHouseList);

        //用户提交绑定房子申请接口
        Observable<BaseResponse> userApplyBindHouse(ReqUserApplyBindHouse reqUserApplyBindHouse);
    }
}