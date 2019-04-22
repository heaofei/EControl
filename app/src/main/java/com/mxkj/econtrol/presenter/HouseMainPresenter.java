package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteCommunity;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetSceneList;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;
import com.mxkj.econtrol.contract.HouseMainContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class HouseMainPresenter implements HouseMainContract.Presenter {
    private HouseMainContract.Model mModel;
    private HouseMainContract.View mView;

    public HouseMainPresenter(HouseMainContract.View mView, HouseMainContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void getSceneList() {
        final ReqGetSceneList reqGetSceneList = new ReqGetSceneList(mView.getHouseId());
        mModel.getSceneList(reqGetSceneList)
                .subscribe(new APISubcriber<ResGetSceneList>() {
                    @Override
                    public void onFail(ResGetSceneList baseResponse,String msg) {
                        mView.getSceneListFali(msg);
                    }

                    @Override
                    public void onSuccess(ResGetSceneList resGetSceneList) {
                        mView.getSceneListSuccess(resGetSceneList);
                    }
                });
    }

    @Override
    public void getAtHomeUserList() {
        ReqGetAtHomeUserList request = new ReqGetAtHomeUserList(mView.getHouseId());
        APISubcriber<ResGetAtHomeUserList> apiSubcriber = new APISubcriber<ResGetAtHomeUserList>() {
            @Override
            public void onFail(ResGetAtHomeUserList baseResponse,String msg) {
                mView.getAtHomeUserListFail(msg);
            }

            @Override
            public void onSuccess(ResGetAtHomeUserList resGetAtHomeUserList) {
                mView.getAtHomeUserListSuccess(resGetAtHomeUserList.getAtHomeUserList());
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.getAtHomeUserList(request)
                .subscribe(apiSubcriber);
    }

    @Override
    public void deleteOtherHomeUser(String houseBindUserId) {
        ReqDeleteOtherHomeUser request = new ReqDeleteOtherHomeUser(houseBindUserId);
        mModel.deleteOtherHomeUser(request).subscribe(new APISubcriber<BaseResponse>() {
            @Override
            public void onFail(BaseResponse baseResponse,String msg) {
                mView.deleteOtherHomeUserFail(msg);
            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.deleteOtherHomeUserSuccess();
            }
        });
    }
}
