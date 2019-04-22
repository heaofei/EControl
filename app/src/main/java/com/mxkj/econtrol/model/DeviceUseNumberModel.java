package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetRankingDateList;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetTimeRanking;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;
import com.mxkj.econtrol.contract.DeviceUseNumberContract;
import com.mxkj.econtrol.contract.FragmentMainContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class DeviceUseNumberModel implements DeviceUseNumberContract.Model {

    @Override
    public Observable<ResGetTimeRanking> getTimeRanking(ReqGetTimeRanking reqGetTimeRanking) {
        return RetrofitUtil.getInstance().getmApiService()
                .getTimeRanking(reqGetTimeRanking.toJsonStr())
                .compose(new APITransFormer<ResGetTimeRanking>());
    }

    @Override
    public Observable<ResGetRankingDateList> getRankingDateList(ReqGetRankingDateList reqGetRankingDateList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getRankingDateList(reqGetRankingDateList.toJsonStr())
                .compose(new APITransFormer<ResGetRankingDateList>());
    }


}
