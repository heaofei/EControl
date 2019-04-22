package com.mxkj.econtrol.presenter;

import com.google.gson.Gson;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.response.ResUserLogin;
import com.mxkj.econtrol.contract.LoginContract;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.Model mLoginModel;
    private LoginContract.View mLoginView;

    public LoginPresenter(LoginContract.View view, LoginContract.Model model) {
        this.mLoginView = view;
        this.mLoginModel = model;
    }

    @Override
    public void login() {


        mLoginModel.login(mLoginView.getUserName(), mLoginView.getPassword())
                .subscribe(new APISubcriber<ResUserLogin>() {
                    @Override
                    public void onFail(ResUserLogin baseResponse, String msg) {
                        mLoginView.showFailedError(msg);
                    }
                    @Override
                    public void onSuccess(ResUserLogin resUserLogin) {
                        Gson gson = new Gson();
                        String content = gson.toJson(resUserLogin);
                        APP.user = resUserLogin;
                        APP.isLogin = true;
                        APP.headerData.getHeaderToken().setToken(resUserLogin.getToken());
                        APP.headerData.getHeaderToken().setUserName(resUserLogin.getUserName());
                        SharedPreferencesUtils.setParam(APP.getInstance(), "houseId",resUserLogin.getDefaultHouseId());// 默认的房子id
//                        SharedPreferencesUtils.setParam(APP.getInstance(), "houseId","ff8080815d4f7e2d015d5e1af6e9007e");// 默认的房子id
                        mLoginView.loginSuccess();
                    }
                });

    }

    @Override
    public void getVeritiyCode() {

    }

    @Override
    public void start() {

    }
}
