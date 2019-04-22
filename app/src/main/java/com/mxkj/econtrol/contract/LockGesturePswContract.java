package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqAppPushMessageReply;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordChange;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordGrant;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordInit;
import com.mxkj.econtrol.bean.response.ResAppPushMessageTipCount;
import com.mxkj.econtrol.bean.response.ResLockPasswordGrant;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description:
 */

public interface LockGesturePswContract {
    interface View extends BaseView<Presenter> {

        //初始房子的部件操作密码成功
        void partPasswordInitSuccess(BaseResponse baseResponse);
        //初始房子的部件操作密码失败
        void partPasswordInitFali(String msg);


        //房子的部件操作密码授权 (验证密码)成功
        void partPasswordGrantSuccess(ResLockPasswordGrant baseResponse);
        //房子的部件操作密码授权 (验证密码)失败
        void partPasswordGrantFali(ResLockPasswordGrant baseResponse);


        //更改房子的部件操作密码成功
        void partPasswordChangeSuccess(BaseResponse baseResponse);
        //更改房子的部件操作密码失败
        void partPasswordChangeFali(String msg);




    }

    interface Presenter extends BasePresenter {

        //初始房子的部件操作密码
        void partPasswordInit(ReqLockPartPasswordInit reqLockPartPasswordInit);

        // 房子的部件操作密码授权 (验证密码)
        void partPasswordGrant(ReqLockPartPasswordGrant ReqLockPartPasswordGrant);

        // 更改房子的部件操作密码
        void partPasswordChange(ReqLockPartPasswordChange reqLockPartPasswordChange);


    }

    interface Model extends BaseModel {

        //初始房子的部件操作密码
        Observable<BaseResponse> partPasswordInit(ReqLockPartPasswordInit reqLockPartPasswordInit);

        // 房子的部件操作密码授权 (验证密码)
        Observable<ResLockPasswordGrant> partPasswordGrant(ReqLockPartPasswordGrant ReqLockPartPasswordGrant);

        // 更改房子的部件操作密码
        Observable<BaseResponse> partPasswordChange(ReqLockPartPasswordChange reqLockPartPasswordChange);

    }

}
