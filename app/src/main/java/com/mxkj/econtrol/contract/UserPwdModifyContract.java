package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqModifyPassword;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/27.
 *
 * @Description:
 */

public interface UserPwdModifyContract {

    interface View extends BaseView<LoginContract.Presenter> {
        String getUserName();

        String getPhone();

        String getCode();

        String getOldPasswd();

        String getNewPasswd();

        String getRePasswd();

        void modifyPasswordSuccess();

        void modifyPasswordFail(String msg);


    }

    interface Presenter extends BasePresenter {
        /**
         * @Desc: 找回密码
         * @author:liangshan
         */
        void modifyPassword();

    }

    interface Model extends BaseModel {
        Observable<BaseResponse> modifyPassword(ReqModifyPassword reqModifyPassword);
    }
}
