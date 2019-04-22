package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetPatternDetail;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetSceneList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternAdd;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternEdit;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;

import rx.Observable;

/**
 * Created by chanjun on 2017/9/6.
 *
 * @Description: 场景编辑或新增，获取房间列表
 */

public interface SceneNewContract {
    interface View extends BaseView<Presenter> {
        //获取HouseId
        String getHouseId();

        //获取房子所有的智能设备部件信息:获取成功
        void getHouseAllPartListSuccess(ResGetHouseAllPartList resGetHouseAllPartList);

        // 获取房子所有的智能设备部件信息失败
        void getHouseAllPartListFail(String msg);


        //模式添加成功
        void patternAddSuccess(BaseResponse baseResponse);

        //模式添加失败
        void patternAddFali(String msg);



    }

    interface Presenter extends BasePresenter {

        // 获取房子所有的智能设备部件信息
        void getHouseAllPartList(String houseId);

        //新增模式
        void patternAdd(String content);


    }

    interface Model extends BaseModel {

        // 获取房子所有的智能设备部件信息
        Observable<ResGetHouseAllPartList> getHouseAllPartList(ReqGetHouseAllPartList reqGetHouseAllPartList);


        //新增模式
        Observable<BaseResponse> patternAdd(String content);


    }

}
