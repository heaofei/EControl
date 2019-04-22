package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTotal;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;
import com.mxkj.econtrol.contract.FragmentMainContract;
import com.mxkj.econtrol.contract.MainNewContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class MainNewModel implements MainNewContract.Model {

    /****编辑房间名字***/
    @Override
    public Observable<BaseResponse> sceneEdit(ReqSceneEdit reqSceneEdit) {
        return RetrofitUtil.getInstance().getmApiService()
                .sceneEdit(reqSceneEdit.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

}
