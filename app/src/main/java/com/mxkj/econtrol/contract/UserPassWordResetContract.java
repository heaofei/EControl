package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqSUserSms;
import com.mxkj.econtrol.bean.request.ReqUserPassWordReset;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/28.
 *
 * @Description:
 */

public interface UserPassWordResetContract {

    interface View extends BaseView<Presenter> {
        //获取验证码失败
        void getCodeFail(String msg);

        //获取验证码成功
        void getCodeSuccess();

        //重置密码失败
        void resetFail(String msg);

        //重置密码成功
        void resetSuccess();

        //获取用户输入的新密码
        String getPassword();

        //获取用户输入的确认密码
        String getRePassword();

        //获取用户输入的验证码
        String getVerityCode();

        //获取输入的手机
        String getPhone();


    }

    interface Presenter extends BasePresenter {
        /**
         * 获取验证码成功
         */
        void getCode();

        /**
         * 重置密码
         */
        void resetPassword();

    }

    interface Model extends BaseModel {

        Observable<BaseResponse> getVerifyCode(ReqSUserSms reqSUserSms);

        Observable<BaseResponse> userPasswordRest(ReqUserPassWordReset reqUserPassWordReset);
    }
}
