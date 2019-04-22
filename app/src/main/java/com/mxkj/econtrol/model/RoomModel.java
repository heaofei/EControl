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
import com.mxkj.econtrol.contract.RoomContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description：
 */

public class RoomModel implements RoomContract.Model {
    @Override
    public Observable<ResGetScenePartList> getScenePartList(ReqGetScenePartList reqGetScenePartList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getScenePartList(reqGetScenePartList.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartList>());

    }
}