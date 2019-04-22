package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetPatternDetail;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetPatternList;

import rx.Observable;

/**
 * Created by chanjun on 2017/9/6.
 *
 * @Description: 场景编辑或新增，获取房间列表
 */

public interface SceneEditContract {
    interface View extends BaseView<Presenter> {
        //获取HouseId
        String getHouseId();

        //获取场景模式列表成功
        void getPatternListSuccess(ResGetPatternList resGetPatternList);

        //获取场景模式列表失败
        void getPatternListFali(String msg);

    }

    interface Presenter extends BasePresenter {

        //获取场景模式列表
        void getPatternList();

    }

    interface Model extends BaseModel {

        //获取场景模式列表
        Observable<ResGetPatternList> getPatternList(ReqPatternList reqPatternList);

    }

}
