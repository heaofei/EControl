package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerDelete;
import com.mxkj.econtrol.bean.request.ReqScenePartOperatorTimerSave;
import com.mxkj.econtrol.bean.request.ReqScenePartTimerSave;
import com.mxkj.econtrol.bean.request.ReqSscenePartOperatorTimerStatus;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTimerList;
import com.mxkj.econtrol.contract.DeviceTimingListContract;
import com.mxkj.econtrol.contract.DiviceTimingEditContract;
import com.mxkj.econtrol.contract.LightTimingEditContract;
import com.mxkj.econtrol.contract.LightTimingListContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class DeviceTimingListModel implements DeviceTimingListContract.Model {

    /***获取设备定时列表（新）(getScenePartTimerList)***/
    @Override
    public Observable<ResGetScenePartTimerList> getScenePartTimerList(ReqGetCommunityList reqGetCommunityList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getScenePartTimerList(reqGetCommunityList.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartTimerList>());
    }

    /****设备定时任务开始/关闭（新）****/
    @Override
    public Observable<BaseResponse> scenePartTimerStatusChange(ReqSscenePartOperatorTimerStatus request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartTimerStatusChange(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
