package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetScenePartList;
import com.mxkj.econtrol.bean.request.ReqUserScencePicDelete;
import com.mxkj.econtrol.bean.request.ReqUserScencePicModify;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetScenePartList;
import com.mxkj.econtrol.bean.response.ResUserScencePicDelete;
import com.mxkj.econtrol.bean.response.ResUserScencePicModify;
import com.mxkj.econtrol.contract.RoomContract2;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description：
 */

public class RoomModel2 implements RoomContract2.Model {
    @Override
    public Observable<ResGetScenePartList> getScenePartList(ReqGetScenePartList reqGetScenePartList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getScenePartList(reqGetScenePartList.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartList>());

    }

    @Override
    public Observable<ResGetAtHomeUserList> getAtHomeUserList(ReqGetAtHomeUserList request) {
        return RetrofitUtil.getInstance().getmApiService()
                .getAtHomeUserList(request.toJsonStr())
                .compose(new APITransFormer<ResGetAtHomeUserList>());

    }

    @Override
    public Observable<ResUserScencePicModify> userScencePicModify(ReqUserScencePicModify request) {
        return RetrofitUtil.getInstance().getmApiService()
                .userScencePicModify(request.toJsonStr())
                .compose(new APITransFormer<ResUserScencePicModify>());
    }

    @Override
    public Observable<ResUserScencePicDelete> userScencePicDelete(ReqUserScencePicDelete request) {
        return RetrofitUtil.getInstance().getmApiService()
                .userScencePicDelete(request.toJsonStr())
                .compose(new APITransFormer<ResUserScencePicDelete>());
    }

    @Override
    public Observable<BaseResponse> deleteOtherHomeUser(ReqDeleteOtherHomeUser request) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(request.toJsonStr(), APIService.DELETE_OTHER_HOME_USER)
                .compose(new APITransFormer<BaseResponse>());
    }
}