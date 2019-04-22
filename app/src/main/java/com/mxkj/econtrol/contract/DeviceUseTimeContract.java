package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetDurationRanking;
import com.mxkj.econtrol.bean.request.ReqGetRankingDateList;
import com.mxkj.econtrol.bean.request.ReqGetTimeRanking;
import com.mxkj.econtrol.bean.response.ResGetDurationRanking;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 * 获取全部设备使用时长
 * @Description:
 */

public interface DeviceUseTimeContract {

    interface View extends BaseView<Presenter> {

        //获取全部设备时长成功
        void getDurationRankingSuccess(ResGetDurationRanking resGetDurationRanking);

        //获取全部设备使用时长失败
        void getDurationRankingFali(String msg);

        //获取控制排名的日期信息成功
        void getRankingDateListSuccess(ResGetRankingDateList resGetRankingDateList);

        //获取控制排名的日期信息失败
        void getRankingDateListFali(String msg);



        String getHouseId();

        String getDateValue();  //时间值( yyyy-MM-dd)

        String getDateType(); //时间类型（month-月，day-日）

    }

    interface Presenter extends BasePresenter {
        //
        void getDurationRanking();

        void getRankingDateList();

    }
    interface Model extends BaseModel {
        //
        Observable<ResGetDurationRanking> getDurationRanking(ReqGetDurationRanking reqGetDurationRanking);

        Observable<ResGetRankingDateList> getRankingDateList(ReqGetRankingDateList reqGetRankingDateList);
    }

}
