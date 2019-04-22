package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetPatternDetail;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetPatternList;

import rx.Observable;

/**
 * Created by chanjun on 2017/9/6.
 *
 * @Description: 场景编辑选择设备列表
 */

public interface EditDeviceListContract {
    interface View extends BaseView<Presenter> {
        //获取HouseId
        String getHouseId();
        //获取场景模式详情成功
        void getPatternDetailSuccess(ResGetPatternDetail resGetPatternDetail);
        //获取场景模式列表失败
        void getPatternDetailFali(String msg);


        //获取房子所有的智能设备部件信息成功
        void getHouseAllPartListSuccess(ResGetHouseAllPartList resGetHouseAllPartList);

        // 获取房子所有的智能设备部件信息失败
        void getHouseAllPartListFail(String msg);

        //编辑模式成功
        void patternEditSuccess(BaseResponse baseResponse);

        //编辑模式失败
        void patternEditFali(String msg);

        //删除模式成功
        void patternDeleteSuccess(BaseResponse baseResponse);

        //删除模式失败
        void patternDeleteFali(String msg);

    }

    interface Presenter extends BasePresenter {

        //获取场景模式详情
        void getPatternDetail(String id );

        // 获取房子所有的智能设备部件信息
        void getHouseAllPartList(String houseId);

        //编辑模式
        void patternEdit(String content);

        //删除模式
        void patternDelete(String id);


    }

    interface Model extends BaseModel {

        //获取场景模式详情
        Observable<ResGetPatternDetail> getPatternDetail(ReqGetPatternDetail reqGetPatternDetail);

        // 获取房子所有的智能设备部件信息
        Observable<ResGetHouseAllPartList> getHouseAllPartList(ReqGetHouseAllPartList reqGetHouseAllPartList);

        ////编辑模式
        Observable<BaseResponse> patternEdit(String content);

        //删除模式
        Observable<BaseResponse> patternDelete(ReqPatternDelete reqPatternDelete);


    }

}
