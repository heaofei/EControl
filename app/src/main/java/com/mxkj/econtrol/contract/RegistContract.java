package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqUserRegist;
import com.mxkj.econtrol.bean.request.ReqUserRegistSms;
import com.mxkj.econtrol.bean.response.ResUserRegist;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public interface RegistContract {
    interface View extends BaseView<Presenter> {

        void registFail(String msg);

        void registSuccess();

        String getUserName();

        String getUserPassword();

        String getRePassword();

        String getTel();

        String getCode();

        void showMsg(String msg);

        //发送验证码成功
        void sendCodeSuccess();

    }

    interface Presenter extends BasePresenter {

        void regist();

        void userRegistSms();
    }

    interface Model extends BaseModel {
        Observable<ResUserRegist> regist(ReqUserRegist reqUserRegist);

        //发送注册验证码
        Observable<BaseResponse> userRegistSms(ReqUserRegistSms reqUserRegistSms);
    }
}
