package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.request.ReqLockUserApplyPartPassword;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: m门锁提交身份证验证
 */

public interface LockSubmitVerificationContract {
    interface View extends BaseView<Presenter> {

        //业主忘记密码提交审核成功
        void userApplyPartPasswordSuccess(BaseResponse baseResponse);
        //业主忘记密码提交审核失败
        void userApplyPartPasswordFali(String msg);

    }

    interface Presenter extends BasePresenter {

        //业主忘记密码提交审核
        void userApplyPartPassword(ReqLockUserApplyPartPassword reqLockUserApplyPartPassword);


    }

    interface Model extends BaseModel {

        //业主忘记密码提交审核
        Observable<BaseResponse> userApplyPartPassword(ReqLockUserApplyPartPassword reqLockUserApplyPartPassword);


    }

}
