package com.mxkj.econtrol.model;

import com.mxkj.econtrol.bean.request.ReqGetDurationRanking;
import com.mxkj.econtrol.bean.request.ReqGetRankingDateList;
import com.mxkj.econtrol.bean.request.ReqGetTimeRanking;
import com.mxkj.econtrol.bean.response.ResGetDurationRanking;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;
import com.mxkj.econtrol.contract.DeviceUseNumberContract;
import com.mxkj.econtrol.contract.DeviceUseTimeContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class DeviceUseTimeModel implements DeviceUseTimeContract.Model {

    @Override
    public Observable<ResGetDurationRanking> getDurationRanking(ReqGetDurationRanking reqGetDurationRanking) {
        return RetrofitUtil.getInstance().getmApiService()
                .getDurationRanking(reqGetDurationRanking.toJsonStr())
                .compose(new APITransFormer<ResGetDurationRanking>());
    }

    @Override
    public Observable<ResGetRankingDateList> getRankingDateList(ReqGetRankingDateList reqGetRankingDateList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getRankingDateList(reqGetRankingDateList.toJsonStr())
                .compose(new APITransFormer<ResGetRankingDateList>());
    }


}
