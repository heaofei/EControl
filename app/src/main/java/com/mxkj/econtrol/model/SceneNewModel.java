package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetPatternDetail;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.contract.SceneNewContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class SceneNewModel implements SceneNewContract.Model {

    /***获取所有房间的设备列表**/
    @Override
    public Observable<ResGetHouseAllPartList> getHouseAllPartList(ReqGetHouseAllPartList reqGetHouseAllPartList) {
        return RetrofitUtil.getInstance()
                .getmApiService()
                .getHouseAllPartList(reqGetHouseAllPartList.toJsonStr())
                .compose(new APITransFormer<ResGetHouseAllPartList>());
    }


    @Override
    public Observable<BaseResponse> patternAdd(String content) {
        return RetrofitUtil.getInstance().getmApiService()
                .patternAdd(content)
                .compose(new APITransFormer<BaseResponse>());
    }

}
