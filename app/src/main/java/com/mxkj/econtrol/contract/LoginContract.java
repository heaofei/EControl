package com.mxkj.econtrol.contract;
import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.response.ResUserLogin;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        String getUserName();

        String getPassword();

        void showFailedError(String msg);

        void loginSuccess();

    }

    interface Presenter extends BasePresenter {
        void login();

        void getVeritiyCode();

    }

    interface Model extends BaseModel {
        Observable<ResUserLogin> login(String userName, String password);
    }
}
