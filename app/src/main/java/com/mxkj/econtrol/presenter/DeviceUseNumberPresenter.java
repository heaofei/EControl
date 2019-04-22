package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
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
import com.mxkj.econtrol.net.APISubcriber;

import static android.R.attr.id;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class DeviceUseNumberPresenter implements DeviceUseNumberContract.Presenter {
    private DeviceUseNumberContract.Model mModel;
    private DeviceUseNumberContract.View mView;

    public DeviceUseNumberPresenter(DeviceUseNumberContract.View mView, DeviceUseNumberContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void getTimeRanking() {
        ReqGetTimeRanking reqGetTimeRanking = new ReqGetTimeRanking();
        reqGetTimeRanking.setHouseId(mView.getHouseId());
        reqGetTimeRanking.setDateValue(mView.getDateValue());//时间值( yyyy-MM-dd)
        reqGetTimeRanking.setDateType(mView.getDateType()); //时间类型（month-月，day-日）
        mModel.getTimeRanking(reqGetTimeRanking)
                .subscribe(new APISubcriber<ResGetTimeRanking>() {
                    @Override
                    public void onFail(ResGetTimeRanking baseResponse,String msg) {
                        mView.getTimeRankingFali(msg);
                    }

                    @Override
                    public void onSuccess(ResGetTimeRanking resGetTimeRanking) {
                        mView.getTimeRankingSuccess(resGetTimeRanking);
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
