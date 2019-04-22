package com.mxkj.econtrol.test;

import com.mxkj.econtrol.bean.response.ResUserLogin;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2017/7/20.
 */

public interface TestStringContract {

    interface View {
        String getUserName();

        String getPassword();

        void showFailedError(String msg);
        void loginSuccess();
    }

    interface  Presenter {
        void login();

        void getVeritiyCode();
    }

    interface Model{
        Observable<String> getLoginText(String userName, String password);
    }



}
