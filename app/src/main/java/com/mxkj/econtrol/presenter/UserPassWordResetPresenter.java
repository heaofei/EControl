package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqSUserSms;
import com.mxkj.econtrol.bean.request.ReqUserPassWordReset;
import com.mxkj.econtrol.contract.UserPassWordResetContract;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.utils.RSAPKCS8X509Utils;


/**
 * Created by liangshan on 2017/3/28.
 *
 * @Description:
 */

public class UserPassWordResetPresenter implements UserPassWordResetContract.Presenter {

    private UserPassWordResetContract.View mView;
    private UserPassWordResetContract.Model mModel;


    public UserPassWordResetPresenter(UserPassWordResetContract.View view, UserPassWordResetContract.Model model) {
        this.mView = view;
        this.mModel = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void getCode() {

        ReqSUserSms reqSUserSms = new ReqSUserSms();
        reqSUserSms.setTel(mView.getPhone());
        mModel.getVerifyCode(reqSUserSms)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.getCodeFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.getCodeSuccess();
                    }
                });
    }

    @Override
    public void resetPassword() {
        ReqUserPassWordReset reqUserPassWordReset = new ReqUserPassWordReset();
        reqUserPassWordReset.setTel(mView.getPhone());
        reqUserPassWordReset.setPassWord(RSAPKCS8X509Utils.getInstance().encryptWithBase64(mView.getPassword()));
        reqUserPassWordReset.setRePassWord(RSAPKCS8X509Utils.getInstance().encryptWithBase64(mView.getRePassword()));
        reqUserPassWordReset.setSmsCode(mView.getVerityCode());
        mModel.userPasswordRest(reqUserPassWordReset)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.resetFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.resetSuccess();
                    }
                });
    }
}
