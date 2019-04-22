package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordChange;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordGrant;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordInit;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.contract.LockGesturePswContract;
import com.mxkj.econtrol.contract.LockTemporaryPswContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: 临时密码
 */

public class LockTemporaryPswPresenter implements LockTemporaryPswContract.Presenter {
    private LockTemporaryPswContract.Model mModel;
    private LockTemporaryPswContract.View mView;

    public LockTemporaryPswPresenter(LockTemporaryPswContract.View mView, LockTemporaryPswContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }


    @Override
    public void start() {

    }

    @Override
    public void partPasswordTemporarySet(ReqLockPartPasswordTemporarySet reqLockPartPasswordTemporarySet) {
        mModel.partPasswordTemporarySet(reqLockPartPasswordTemporarySet)
                .subscribe(new APISubcriber<ResLockPartPasswordTemporarySet>() {
                    @Override
                    public void onFail(ResLockPartPasswordTemporarySet baseResponse,String msg) {
                        mView.partPasswordTemporarySetFali(msg);
                    }
                    @Override
                    public void onSuccess(ResLockPartPasswordTemporarySet resLockPartPasswordTemporarySet) {
                        mView.partPasswordTemporarySetSuccess(resLockPartPasswordTemporarySet);
                    }
                });
    }
}
