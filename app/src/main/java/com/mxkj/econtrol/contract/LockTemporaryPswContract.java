package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordChange;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordGrant;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordInit;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: 临时密码
 */

public interface LockTemporaryPswContract {
    interface View extends BaseView<Presenter> {

        //获取临时密码成功
        void partPasswordTemporarySetSuccess(ResLockPartPasswordTemporarySet resLockPartPasswordTemporarySet);
        //获取临时密码失败
        void partPasswordTemporarySetFali(String msg);

    }

    interface Presenter extends BasePresenter {

        //获取临时密码
        void partPasswordTemporarySet(ReqLockPartPasswordTemporarySet reqLockPartPasswordTemporarySet);


    }

    interface Model extends BaseModel {

        //获取临时密码
        Observable<ResLockPartPasswordTemporarySet> partPasswordTemporarySet(ReqLockPartPasswordTemporarySet reqLockPartPasswordTemporarySet);


    }

}
