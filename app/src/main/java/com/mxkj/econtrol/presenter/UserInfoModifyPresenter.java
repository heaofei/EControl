package com.mxkj.econtrol.presenter;

import android.text.TextUtils;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetAreaList;
import com.mxkj.econtrol.bean.request.ReqUserHeadPicModify;
import com.mxkj.econtrol.bean.request.ReqUserInfoModify;
import com.mxkj.econtrol.bean.response.ResAreaBean;
import com.mxkj.econtrol.bean.response.ResGetAreaLsit;
import com.mxkj.econtrol.bean.response.ResUserHeadPicModify;
import com.mxkj.econtrol.contract.UserInfoModifyContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/3/29.
 *
 * @Description:
 */

public class UserInfoModifyPresenter implements UserInfoModifyContract.Presenter {

    private UserInfoModifyContract.View mView;
    private UserInfoModifyContract.Model mModel;

    public UserInfoModifyPresenter(UserInfoModifyContract.View view, UserInfoModifyContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void userInfoModify() {
        if (!checkData()) {
            return;
        }
        final ReqUserInfoModify modify = new ReqUserInfoModify();
//        modify.setUserName(mView.getUserName());
        modify.setEmail(mView.getEmail());
        modify.setAddress(mView.getAddress());
        modify.setSex(mView.getSex());
        modify.setBorn(mView.getBoth());
        modify.setNiceName(mView.getNiceName());
        modify.setRealName(mView.getRealName());
        mModel.userInfoModify(modify)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.modifyFail(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        APP.user.setBorn(modify.getBorn());
                        APP.user.setUserName(mView.getTel().replace(" ",""));
                        APP.user.setEmail(mView.getEmail());
                        APP.user.setAddress(mView.getAddress());
                        APP.user.setSex(mView.getSex());
                        APP.user.setNiceName(mView.getNiceName());
                        mView.modifySuccess();

                    }
                });
    }

    /**
     * @Desc: 检测输入的数据
     * @author:liangshan
     */
    private boolean checkData() {
        boolean result = true;

        if (TextUtils.isEmpty(mView.getSex())) {

        } else {

        }

        return result;
    }

    @Override
    public void userHeadPicModify(String pic) {
        final ReqUserHeadPicModify reqUserHeadPicModify = new ReqUserHeadPicModify();
        reqUserHeadPicModify.setUserName(APP.user.getUserName());
        reqUserHeadPicModify.setHeadPicture(pic);
        mModel.userHeadPicModify(reqUserHeadPicModify)
                .subscribe(new APISubcriber<ResUserHeadPicModify>() {
                    @Override
                    public void onFail(ResUserHeadPicModify baseResponse,String msg) {
                        mView.showMsg(msg);
                    }

                    @Override
                    public void onSuccess(ResUserHeadPicModify resUserHeadPicModify) {
                        mView.modidyHeaderSuccess(resUserHeadPicModify);
                    }
                });

    }


    @Override
    public void getAreaList(String areaCode) {
        APISubcriber<ResAreaBean> apiSubcriber = new APISubcriber<ResAreaBean>() {
            @Override
            public void onFail(ResAreaBean baseResponse,String msg) {
                mView.getAreaListFail(msg);
            }

            @Override
            public void onSuccess(ResAreaBean resAreaBean) {
                mView.getAreaListSuccess(resAreaBean);
            }
        };
        apiSubcriber.setShowLoding(false);
        ReqGetAreaList req = new ReqGetAreaList();
        req.setAreaCode(areaCode);
//        req.setStatus("0"); //0-禁用；1-启用；不传-全部；
        mModel.getAreaList(req)
                .subscribe(apiSubcriber);

    }
}
