package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetAreaList;
import com.mxkj.econtrol.bean.request.ReqGetBuildingList;
import com.mxkj.econtrol.bean.request.ReqGetHouseList;
import com.mxkj.econtrol.bean.request.ReqGetHousingEstateList;
import com.mxkj.econtrol.bean.request.ReqUserApplyBindHouse;
import com.mxkj.econtrol.bean.response.ResGetAreaLsit;
import com.mxkj.econtrol.bean.response.ResGetBuildingList;
import com.mxkj.econtrol.bean.response.ResGetHouseList;
import com.mxkj.econtrol.bean.response.ResGetHousingEstateList;
import com.mxkj.econtrol.contract.NewHouseContract;
import com.mxkj.econtrol.contract.QrCodeScanContract;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan
 *
 * @Description: 新建方案model实现类
 */

public class QrCodeScanModel implements QrCodeScanContract.Model {
    @Override
    public Observable<BaseResponse> userApplyBindHouse(ReqUserApplyBindHouse reqUserApplyBindHouse) {
        return RetrofitUtil.getInstance().getmApiService()
                .API(reqUserApplyBindHouse.toJsonStr(), APIService.USER_APPLY_BIND_HOUSE)
                .compose(new APITransFormer<BaseResponse>());
    }
}
