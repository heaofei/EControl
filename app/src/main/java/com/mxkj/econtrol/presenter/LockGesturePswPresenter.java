package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordChange;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordGrant;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordInit;
import com.mxkj.econtrol.bean.response.ResAppPushMessageTipCount;
import com.mxkj.econtrol.bean.response.ResLockPasswordGrant;
import com.mxkj.econtrol.contract.FragmentMyContract;
import com.mxkj.econtrol.contract.LockGesturePswContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description:
 */

public class LockGesturePswPresenter implements LockGesturePswContract.Presenter {
    private LockGesturePswContract.Model mModel;
    private LockGesturePswContract.View mView;

    public LockGesturePswPresenter(LockGesturePswContract.View mView, LockGesturePswContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }


    @Override
    public void start() {

    }
    @Override
    public void partPasswordInit(ReqLockPartPasswordInit reqLockPartPasswordInit) {
        mModel.partPasswordInit(reqLockPartPasswordInit)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.partPasswordInitFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.partPasswordInitSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void partPasswordGrant(ReqLockPartPasswordGrant ReqLockPartPasswordGrant) {
        mModel.partPasswordGrant(ReqLockPartPasswordGrant)
                .subscribe(new APISubcriber<ResLockPasswordGrant>() {
                    @Override
                    public void onFail(ResLockPasswordGrant baseResponse, String msg) {
                        mView.partPasswordGrantFali(baseResponse);
                    }
                    @Override
                    public void onSuccess(ResLockPasswordGrant baseResponse) {
                        mView.partPasswordGrantSuccess(baseResponse);
                    }
                });

    }

    @Override
    public void partPasswordChange(ReqLockPartPasswordChange reqLockPartPasswordChange) {
        mModel.partPasswordChange(reqLockPartPasswordChange)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.partPasswordChangeFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.partPasswordChangeSuccess(baseResponse);
                    }
                });
    }
}
