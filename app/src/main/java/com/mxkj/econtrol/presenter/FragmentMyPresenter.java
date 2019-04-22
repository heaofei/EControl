package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetApplyBindHouseList;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.response.ResAppPushMessageTipCount;
import com.mxkj.econtrol.bean.response.ResAppTodoList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;
import com.mxkj.econtrol.contract.FragmentMainContract;
import com.mxkj.econtrol.contract.FragmentMyContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2018/01/03.
 *
 * @Description:
 */

public class FragmentMyPresenter implements FragmentMyContract.Presenter {
    private FragmentMyContract.Model mModel;
    private FragmentMyContract.View mView;

    public FragmentMyPresenter(FragmentMyContract.View mView, FragmentMyContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }


    @Override
    public void start() {

    }


    @Override
    public void appPushMessageTipCount() {


        APISubcriber<ResAppPushMessageTipCount> apiSubcriber = new APISubcriber<ResAppPushMessageTipCount>() {
            @Override
            public void onFail(ResAppPushMessageTipCount resAppPushMessageTipCount, String msg) {
                mView.appPushMessageTipCountFali(msg);
            }
            @Override
            public void onSuccess(ResAppPushMessageTipCount resAppPushMessageTipCount) {
                mView.appPushMessageTipCountSuccess(resAppPushMessageTipCount);
            }
        };
        apiSubcriber.setShowLoding(false); //
        mModel.appPushMessageTipCount()
                .subscribe(apiSubcriber);

    }

    @Override
    public void appTodoList() {
        APISubcriber<ResAppTodoList> apiSubcriber = new APISubcriber<ResAppTodoList>() {
            @Override
            public void onFail(ResAppTodoList resAppTodoList, String msg) {
                mView.appTodoListFali(msg);
            }
            @Override
            public void onSuccess(ResAppTodoList resAppTodoList) {
                mView.appTodoListSuccess(resAppTodoList);
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.appTodoList()
                .subscribe(apiSubcriber);


      /*  mModel.appTodoList()
                .subscribe(new APISubcriber<ResAppTodoList>() {
                    @Override
                    public void onFail(ResAppTodoList baseResponse,String msg) {

                    }
                    @Override
                    public void onSuccess(ResAppTodoList resAppTodoList) {

                    }
                });*/
    }

    //获取申请该房子下申请列表
    @Override
    public void getApplyBindHouseList(ReqGetApplyBindHouseList reqGetApplyBindHouseList) {


        APISubcriber<ResGetApplyBindHouseList> apiSubcriber = new APISubcriber<ResGetApplyBindHouseList>() {
            @Override
            public void onFail(ResGetApplyBindHouseList resGetApplyBindHouseList, String msg) {
                mView.getApplyBindHouseListFail(msg);
            }
            @Override
            public void onSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList) {
                mView.getApplyBindHouseListSuccess(resGetApplyBindHouseList);
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.getApplyBindHouseList(reqGetApplyBindHouseList)
                .subscribe(apiSubcriber);

       /* mModel.getApplyBindHouseList(reqGetApplyBindHouseList)
                .subscribe(new APISubcriber<ResGetApplyBindHouseList>() {
                    @Override
                    public void onFail(ResGetApplyBindHouseList baseResponse,String msg) {
                        mView.getApplyBindHouseListFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetApplyBindHouseList resGetApplyBindHouseList) {
                        mView.getApplyBindHouseListSuccess(resGetApplyBindHouseList);
                    }
                });*/


    }


}
