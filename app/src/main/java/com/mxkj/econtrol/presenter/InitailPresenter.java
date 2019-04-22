package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqInitailEntity;
import com.mxkj.econtrol.bean.response.ResInitailEntity;
import com.mxkj.econtrol.contract.InitailContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription: 初始化presenter
 */

public class InitailPresenter implements InitailContract.Presenter {

    private InitailContract.View mView;
    private InitailContract.Model mModel;

    public InitailPresenter(InitailContract.View view, InitailContract.Model model) {
        this.mView = view;
        this.mModel = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void initail(ReqInitailEntity reqInitailEntity) {
        APISubcriber<ResInitailEntity> subcriber = new APISubcriber<ResInitailEntity>() {
            @Override
            public void onFail(ResInitailEntity baseResponse, String msg) {
                mView.initailFail(msg);
            }

            @Override
            public void onSuccess(ResInitailEntity resInitailEntity) {

                mView.initailSuccess(resInitailEntity);
            }
        };
        subcriber.setShowLoding(false);
        mModel.initail(reqInitailEntity)
                .subscribe(subcriber);

    }
}
