package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
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

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public interface DeviceUseNumberContract {

    interface View extends BaseView<Presenter> {

        //获取全部设备次数成功
        void getTimeRankingSuccess(ResGetTimeRanking resGetTimeRanking);

        //获取全部设备使用次数失败
        void getTimeRankingFali(String msg);

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
        void getTimeRanking();

        void getRankingDateList();

    }
    interface Model extends BaseModel {
        //
        Observable<ResGetTimeRanking> getTimeRanking(ReqGetTimeRanking reqGetTimeRanking);

        Observable<ResGetRankingDateList> getRankingDateList(ReqGetRankingDateList reqGetRankingDateList);
    }

}
