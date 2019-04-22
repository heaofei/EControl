package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetDurationRanking;
import com.mxkj.econtrol.bean.request.ReqGetRankingDateList;
import com.mxkj.econtrol.bean.request.ReqGetTimeRanking;
import com.mxkj.econtrol.bean.response.ResGetDurationRanking;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;
import com.mxkj.econtrol.contract.DeviceUseNumberContract;
import com.mxkj.econtrol.contract.DeviceUseTimeContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class DeviceUseTimePresenter implements DeviceUseTimeContract.Presenter {
    private DeviceUseTimeContract.Model mModel;
    private DeviceUseTimeContract.View mView;

    public DeviceUseTimePresenter(DeviceUseTimeContract.View mView, DeviceUseTimeContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void getDurationRanking() {
        ReqGetDurationRanking reqGetDurationRanking = new ReqGetDurationRanking();
        reqGetDurationRanking.setHouseId(mView.getHouseId());
        reqGetDurationRanking.setDateValue(mView.getDateValue());//时间值( yyyy-MM-dd)
        reqGetDurationRanking.setDateType(mView.getDateType()); //时间类型（month-月，day-日）

        mModel.getDurationRanking(reqGetDurationRanking)
                .subscribe(new APISubcriber<ResGetDurationRanking>() {
                    @Override
                    public void onFail(ResGetDurationRanking baseResponse, String msg) {
                        mView.getDurationRankingFali(msg);
                    }

                    @Override
                    public void onSuccess(ResGetDurationRanking resGetTimeRanking) {
                        mView.getDurationRankingSuccess(resGetTimeRanking);
                    }
                });
    }

    @Override
    public void getRankingDateList() {
        ReqGetRankingDateList reqGetRankingDateList = new ReqGetRankingDateList();
        reqGetRankingDateList.setHouseId(mView.getHouseId());
        reqGetRankingDateList.setDateType(mView.getDateType());//时间类型（month-月，day-日）
        mModel.getRankingDateList(reqGetRankingDateList)
                .subscribe(new APISubcriber<ResGetRankingDateList>() {
                    @Override
                    public void onFail(ResGetRankingDateList baseResponse,String msg) {
                        mView.getRankingDateListFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetRankingDateList resGetRankingDateList) {
                        mView.getRankingDateListSuccess(resGetRankingDateList);
                    }
                });

    }

}
