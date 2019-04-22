package com.mxkj.econtrol.model;

import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.contract.HousePartInfoContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class HousePartInfoModel implements HousePartInfoContract.Model {
    @Override
    public Observable<ResGetHouseAllPartList> getHouseAllPartList(ReqGetHouseAllPartList reqGetHouseAllPartList) {
        return RetrofitUtil.getInstance()
                .getmApiService()
                .getHouseAllPartList(reqGetHouseAllPartList.toJsonStr())
                .compose(new APITransFormer<ResGetHouseAllPartList>());
    }
}
