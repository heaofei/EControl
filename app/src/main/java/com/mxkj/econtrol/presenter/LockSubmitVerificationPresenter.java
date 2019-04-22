package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.request.ReqLockUserApplyPartPassword;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.contract.LockSubmitVerificationContract;
import com.mxkj.econtrol.contract.LockTemporaryPswContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: 门锁提交身份证验证
 */

public class LockSubmitVerificationPresenter implements LockSubmitVerificationContract.Presenter {
    private LockSubmitVerificationContract.Model mModel;
    private LockSubmitVerificationContract.View mView;

    public LockSubmitVerificationPresenter(LockSubmitVerificationContract.View mView, LockSubmitVerificationContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }


    @Override
    public void start() {

    }


    @Override
    public void userApplyPartPassword(ReqLockUserApplyPartPassword reqLockUserApplyPartPassword) {
        mModel.userApplyPartPassword(reqLockUserApplyPartPassword)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.userApplyPartPasswordFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.userApplyPartPasswordSuccess(baseResponse);
                    }
                });
    }
}
