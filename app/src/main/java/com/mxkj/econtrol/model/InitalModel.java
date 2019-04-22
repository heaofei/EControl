package com.mxkj.econtrol.model;

import com.mxkj.econtrol.bean.request.ReqInitailEntity;
import com.mxkj.econtrol.bean.response.ResInitailEntity;
import com.mxkj.econtrol.contract.InitailContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public class InitalModel implements InitailContract.Model {

    @Override
    public Observable<ResInitailEntity> initail(ReqInitailEntity reqInitailEntity) {

        return RetrofitUtil.getInstance().getmApiService()
                .initail(reqInitailEntity.toJsonStr())
                .compose(new APITransFormer<ResInitailEntity>());


    }
}
