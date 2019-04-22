package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordChange;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordGrant;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordInit;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.contract.LockGesturePswContract;
import com.mxkj.econtrol.contract.LockTemporaryPswContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: 临时密码
 */

public class LockTemporaryPswModel implements LockTemporaryPswContract.Model {


    @Override
    public Observable<ResLockPartPasswordTemporarySet> partPasswordTemporarySet(ReqLockPartPasswordTemporarySet reqLockPartPasswordTemporarySet) {
        return RetrofitUtil.getInstance().getmApiService()
                .partPasswordTemporarySet(reqLockPartPasswordTemporarySet.toJsonStr())
                .compose(new APITransFormer<ResLockPartPasswordTemporarySet>());
    }
}
