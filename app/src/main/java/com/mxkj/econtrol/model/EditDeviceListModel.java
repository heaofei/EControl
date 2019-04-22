package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetPatternDetail;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.contract.EditDeviceListContract;
import com.mxkj.econtrol.contract.SceneEditContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class EditDeviceListModel implements EditDeviceListContract.Model {

    /***获取场景详情***/
    @Override
    public Observable<ResGetPatternDetail> getPatternDetail(ReqGetPatternDetail reqGetPatternDetail) {
        return RetrofitUtil.getInstance().getmApiService()
                .getPatternDetail(reqGetPatternDetail.toJsonStr())
                .compose(new APITransFormer<ResGetPatternDetail>());
    }

    /****获取房子下所有设备列表**/
    @Override
    public Observable<ResGetHouseAllPartList> getHouseAllPartList(ReqGetHouseAllPartList reqGetHouseAllPartList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getHouseAllPartList(reqGetHouseAllPartList.toJsonStr())
                .compose(new APITransFormer<ResGetHouseAllPartList>());
    }

        /****场景模式编辑**/
    @Override
    public Observable<BaseResponse> patternEdit(String content) {
        return RetrofitUtil.getInstance().getmApiService()
                .patternEdit(content)
                .compose(new APITransFormer<BaseResponse>());
    }

    /***场景模式删除**/
    @Override
    public Observable<BaseResponse> patternDelete(ReqPatternDelete reqPatternDelete) {
        return RetrofitUtil.getInstance().getmApiService()
                .patternDelete(reqPatternDelete.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }

}
