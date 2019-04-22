package com.mxkj.econtrol.model;

import com.google.gson.Gson;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetSceneList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTotal;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;
import com.mxkj.econtrol.contract.FragmentMainContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class FragmentMainModel implements FragmentMainContract.Model {

    /***获取主页场景模式列表***/
    @Override
    public Observable<ResGetPatternList> getPatternList(ReqPatternList reqPatternList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getPatternList(reqPatternList.toJsonStr())
                .compose(new APITransFormer<ResGetPatternList>());
    }

    @Override
    public Observable<ResGetRoomList> getRoomList(ReqGetRoomList reqGetRoomList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getRoomList(reqGetRoomList.toJsonStr())
                .compose(new APITransFormer<ResGetRoomList>());
    }

    @Override
    public Observable<ResGetUserHouseList> getHoursList(ReqGetUserHouseList reqGetUserHouseList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getUserHouseList(reqGetUserHouseList.toJsonStr())
                .compose(new APITransFormer<ResGetUserHouseList>());
    }

    @Override
    public Observable<ResGetScenePartTotal> scenePartTotal(ReqGetRoomList reqGetRoomList) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartTotal(reqGetRoomList.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartTotal>());
    }

    @Override
    public Observable<BaseResponse> patternActive(ReqPatternActive reqPatternActive) {
        return RetrofitUtil.getInstance().getmApiService()
                .patternActive(reqPatternActive.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    /****编辑设备名字***/
    @Override
    public Observable<BaseResponse> scenePartEdit(ReqScenePartEdit request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartEdit(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

    @Override
    public Observable<ResGetUserInfo> getUserInfo() {
        return RetrofitUtil.getInstance().getmApiService()
                .getUserInfo("{}")
                .compose(new APITransFormer<ResGetUserInfo>());
    }





}
