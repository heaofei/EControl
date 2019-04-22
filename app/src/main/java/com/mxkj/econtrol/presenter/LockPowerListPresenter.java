package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqLockGetPartPermissionsList;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.request.ReqLockPartPermissionsUpdate;
import com.mxkj.econtrol.bean.request.ReqPartUnbindGateway;
import com.mxkj.econtrol.bean.request.ReqPartUnbindSnid;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.response.ResLockPowerList;
import com.mxkj.econtrol.contract.LockPowerListContract;
import com.mxkj.econtrol.contract.LockTemporaryPswContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: 门锁授权列表
 */

public class LockPowerListPresenter implements LockPowerListContract.Presenter {
    private LockPowerListContract.Model mModel;
    private LockPowerListContract.View mView;

    public LockPowerListPresenter(LockPowerListContract.View mView, LockPowerListContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void getPartPermissionsList(ReqLockGetPartPermissionsList reqLockGetPartPermissionsList) {
        mModel.getPartPermissionsList(reqLockGetPartPermissionsList)
                .subscribe(new APISubcriber<ResLockPowerList>() {
                    @Override
                    public void onFail(ResLockPowerList baseResponse,String msg) {
                        mView.getPartPermissionsListFali(msg);
                    }
                    @Override
                    public void onSuccess(ResLockPowerList baseResponse) {
                        mView.getPartPermissionsListSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void partPermissionsUpdate(ReqLockPartPermissionsUpdate reqLockPartPermissionsUpdate) {
        mModel.partPermissionsUpdate(reqLockPartPermissionsUpdate)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.partPermissionsUpdateFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.partPermissionsUpdateSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void partUnbindGateway(ReqPartUnbindGateway reqPartUnbindGateway) {
        mModel.partUnbindGateway(reqPartUnbindGateway)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.partUnbindGatewayFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.partUnbindGatewaySuccess(baseResponse);
                    }
                });
    }

    @Override
    public void partUnbindSnid(ReqPartUnbindSnid reqPartUnbindSnid) {
        mModel.partUnbindSnid(reqPartUnbindSnid)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.partUnbindSnidFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.partUnbindSnidSuccess(baseResponse);
                    }
                });
    }
}
