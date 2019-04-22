package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqInitailEntity;
import com.mxkj.econtrol.bean.response.ResInitailEntity;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public interface InitailContract {

    interface View extends BaseView<Presenter> {

        void initailFail(String msg);

        void initailSuccess(ResInitailEntity resInitailEntity);
    }

    interface Presenter extends BasePresenter {
        void initail(ReqInitailEntity reqInitailEntity);
    }

    interface Model extends BaseModel {
        Observable<ResInitailEntity> initail(ReqInitailEntity reqInitailEntity);
    }
}
