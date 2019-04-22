package com.mxkj.econtrol.test;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by ${  chenjun  } on 2017/7/20.
 */

public class TestStringPresenter implements TestStringContract.Presenter{


    private TestStringContract.Model mLoginModel;
    private TestStringContract.View mLoginView;

    public TestStringPresenter(TestStringContract.Model mLoginModel, TestStringContract.View mLoginView) {
        this.mLoginModel = mLoginModel;
        this.mLoginView = mLoginView;
    }


    @Override
    public void login() {


        mLoginModel.getLoginText(mLoginView.getUserName(), mLoginView.getPassword())
                .subscribe(new APISubcriber<String>() {
                    @Override
                    public void onFail(String baseResponse, String msg) {
                        mLoginView.showFailedError(msg);

                    }

                    @Override
                    public void onSuccess(String resUserLogin) {

                        mLoginView.loginSuccess();

                    }
                });

    }

    @Override
    public void getVeritiyCode() {

    }



}
