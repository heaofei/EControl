package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetSceneList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTotal;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public interface FragmentMainContract {
    interface View extends BaseView<Presenter> {
        //获取场景模式列表成功
        void getPatternListSuccess(ResGetPatternList resGetPatternList);

        //获取场景模式列表失败
        void getPatternListFali(String msg);

        //获取HouseId
        String getHouseId();

        //获取房间列表成功
        void getRoomListSuccess(ResGetRoomList resGetRoomList);

        //获取房间列表失败
        void getRoomListFali(String msg);

        //获取房子列表成功
        void getHoursListSuccess(ResGetUserHouseList resGetUserHouseList);

        //获取房子列表失败
        void getHoursListFali(String msg);

        //获取房子设备使用总数成功
        void scenePartTotalSuccess(ResGetScenePartTotal resGetScenePartTotal);
        //获取房子设备使用总数失败
        void scenePartTotalFali(String msg);


        //模式应用接口成功
        void patternActiveSuccess(BaseResponse baseResponse);

        //模式应用接口失败
        void patternActiveFali(String msg);

        //获取用户信息成功
        void getUserInfoSuccess(ResGetUserInfo resGetUserInfo);

        //获取用户信息失败
        void getUserInfoFali(String msg);

        //  修改部件状态（修改名字以及开关状态）成功
        void scenePartEditSuccess(BaseResponse baseResponse);
        //  修改部件状态（修改名字以及开关状态）失败
        void scenePartEditFail(String msg);

    }

    interface Presenter extends BasePresenter {
        //获取场景模式列表
        void getPatternList();

        //获取房间列表
        void getRoomList();

        //获取房子列表
        void getHoursList();

        // 常用设备列表
        void scenePartTotal ( );

        //模式应用接口
        void patternActive(String id);


        //用户信息获取
        void getUserInfo();

        // 修改部件状态（修改名字以及开关状态）
        void scenePartEdit(String id,String name);



    }

    interface Model extends BaseModel {
        //获取场景模式列表
        Observable<ResGetPatternList> getPatternList(ReqPatternList reqPatternList);

        //获取房间列表
        Observable<ResGetRoomList> getRoomList(ReqGetRoomList reqGetSceneList);

        //获取房子列表
        Observable<ResGetUserHouseList> getHoursList(ReqGetUserHouseList reqGetUserHouseList);

        // 获取常用设备列表
        Observable<ResGetScenePartTotal> scenePartTotal(ReqGetRoomList reqGetRoomList);

        //模式应用接口
        Observable<BaseResponse> patternActive(ReqPatternActive reqPatternActive);

        //用户信息获取
        Observable<ResGetUserInfo> getUserInfo();

        // 修改部件状态（修改名字以及开关状态）
        Observable<BaseResponse> scenePartEdit(ReqScenePartEdit request);
    }

}
