package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
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
import com.mxkj.econtrol.bean.response.ResInitailEntity;
import com.mxkj.econtrol.contract.FragmentMainContract;
import com.mxkj.econtrol.contract.HouseMainContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class FragmentMainPresenter implements FragmentMainContract.Presenter {
    private FragmentMainContract.Model mModel;
    private FragmentMainContract.View mView;

    public FragmentMainPresenter(FragmentMainContract.View mView, FragmentMainContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    /*****获取场景列表***/
    @Override
    public void getPatternList() {
        final ReqPatternList reqPatternList = new ReqPatternList(mView.getHouseId());
        if (APP.isLogin) {
        reqPatternList.setUserId(APP.user.getUserId());
        }
        APISubcriber<ResGetPatternList> subcriber  = new APISubcriber<ResGetPatternList>() {
            @Override
            public void onFail(ResGetPatternList resGetPatternList, String msg) {
                mView.getPatternListFali(msg);
            }
            @Override
            public void onSuccess(ResGetPatternList resGetPatternList) {
                mView.getPatternListSuccess(resGetPatternList);
            }
        };
        subcriber.setShowLoding(false);
        mModel.getPatternList(reqPatternList).subscribe(subcriber);

        /*mModel.getPatternList(reqPatternList)
                .subscribe(new APISubcriber<ResGetPatternList>() {
                    @Override
                    public void onFail(ResGetPatternList baseResponse,String msg) {
                        mView.getPatternListFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetPatternList resGetPatternList) {
                        mView.getPatternListSuccess(resGetPatternList);
                    }
                });*/
    }

    /******获取房间列表、每个房间设备列表、****/
    @Override
    public void getRoomList() {
        final ReqGetRoomList resGetRoomList = new ReqGetRoomList(mView.getHouseId());
        mModel.getRoomList(resGetRoomList)
                .subscribe(new APISubcriber<ResGetRoomList>() {
                    @Override
                    public void onFail(ResGetRoomList baseResponse,String msg) {
                        mView.getRoomListFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetRoomList resGetSceneList) {
                        mView.getRoomListSuccess(resGetSceneList);
                    }
                });
    }

    /******获取房子列表******/
    @Override
    public void getHoursList() {
        if (APP.user == null) {
            return;
        }
        ReqGetUserHouseList reqGetUserHouseList = new ReqGetUserHouseList(APP.user.getUserId());
        mModel.getHoursList(reqGetUserHouseList)
                .subscribe(new APISubcriber<ResGetUserHouseList>() {
                    @Override
                    public void onFail(ResGetUserHouseList baseResponse,String msg) {
                        mView.getHoursListFali(msg);
                    }

                    @Override
                    public void onSuccess(ResGetUserHouseList resGetUserHouseList) {
                        mView.getHoursListSuccess(resGetUserHouseList);
                    }
                });
    }

    /**获取设备常用列表**/
    @Override
    public void scenePartTotal( ) {
        if (APP.user == null) {
            return;
        }
        final ReqGetRoomList resGetRoomList = new ReqGetRoomList(mView.getHouseId());
//        resGetRoomList.setUserId(APP.user.getUserId());
        resGetRoomList.setUserId(APP.user.getUserName()); // 这里备注一下， 因为发送tcp指令操作的时候是用了手机号，后台使用这个手机号在注册的时候绑定通道号，导致查常用数据的时候也要传手机号进去查常用列表

        mModel.scenePartTotal(resGetRoomList)
                .subscribe(new APISubcriber<ResGetScenePartTotal>() {
                    @Override
                    public void onFail(ResGetScenePartTotal baseResponse,String msg) {
                        mView.scenePartTotalFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetScenePartTotal resGetUserHouseList) {
                        mView.scenePartTotalSuccess(resGetUserHouseList);
                    }
                });
    }

    /*****场景模式应用***/
    @Override
    public void patternActive(String id) {
        ReqPatternActive reqPatternActive = new ReqPatternActive(id);
        mModel.patternActive(reqPatternActive)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.patternActiveFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.patternActiveSuccess(baseResponse);
                    }
                });
    }
      /***编辑设备名字***/
    @Override
    public void scenePartEdit(String id , String name) {
        ReqScenePartEdit reqScenePartTimerStatusChange = new ReqScenePartEdit();
        reqScenePartTimerStatusChange.setId(id);
        reqScenePartTimerStatusChange.setStateTimer("0"); // 这个是必填，旧的，写死0为关闭
        reqScenePartTimerStatusChange.setName(name);

        mModel.scenePartEdit(reqScenePartTimerStatusChange)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.scenePartEditFail(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.scenePartEditSuccess(baseResponse);
                    }
                });
    }

    /*****获取用户信息，具体是获取默认房子id ****/
    @Override
    public void getUserInfo() {

        APISubcriber<ResGetUserInfo> subcriber = new APISubcriber<ResGetUserInfo>() {
            @Override
            public void onFail(ResGetUserInfo resGetUserInfo, String msg) {
                mView.getUserInfoFali(msg);
            }
            @Override
            public void onSuccess(ResGetUserInfo resGetUserInfo) {
                mView.getUserInfoSuccess(resGetUserInfo);
            }
        };
        subcriber.setShowLoding(false);
        mModel.getUserInfo().subscribe(subcriber);

       /* mModel.getUserInfo()
                .subscribe(new APISubcriber<ResGetUserInfo>() {
                    @Override
                    public void onFail(ResGetUserInfo baseResponse,String msg) {
                        mView.getUserInfoFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetUserInfo resGetUserInfo) {
                        mView.getUserInfoSuccess(resGetUserInfo);
                    }
                });*/
    }

    @Override
    public void start() {

    }


}
