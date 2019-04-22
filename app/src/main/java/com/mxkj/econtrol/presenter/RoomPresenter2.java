package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetScenePartList;
import com.mxkj.econtrol.bean.request.ReqUserScencePicDelete;
import com.mxkj.econtrol.bean.request.ReqUserScencePicModify;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetScenePartList;
import com.mxkj.econtrol.bean.response.ResUserScencePicDelete;
import com.mxkj.econtrol.bean.response.ResUserScencePicModify;
import com.mxkj.econtrol.contract.RoomContract2;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description：
 */

public class RoomPresenter2 implements RoomContract2.Presenter {
    private RoomContract2.View mView;
    private RoomContract2.Model mModel;

    public RoomPresenter2(RoomContract2.View mView, RoomContract2.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

    }

    @Override
    public void getScenePartList(String sceneId) {
        APISubcriber<ResGetScenePartList> apiSubcriber = new APISubcriber<ResGetScenePartList>() {
            @Override
            public void onFail(ResGetScenePartList baseResponse,String msg) {
                mView.getScenePartListFail("");
            }

            @Override
            public void onSuccess(ResGetScenePartList resGetScenePartList) {
                mView.getScenePartListSuccess(resGetScenePartList);
            }
        };
        apiSubcriber.setShowLoding(false);
        mModel.getScenePartList(new ReqGetScenePartList(sceneId))
                .subscribe(apiSubcriber);
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
    public void userScencePicModify() {
        ReqUserScencePicModify reqUserScencePicModify = new ReqUserScencePicModify(mView.getSceneId(), mView.getScenePic());
        mModel.userScencePicModify(reqUserScencePicModify)
                .subscribe(new APISubcriber<ResUserScencePicModify>() {
                    @Override
                    public void onFail(ResUserScencePicModify baseResponse,String msg) {
                        mView.userScencePicModifyFail(msg);
                    }

                    @Override
                    public void onSuccess(ResUserScencePicModify resUserScencePicModify) {
                        mView.userScencePicModifySuccess(resUserScencePicModify.getPic());
                    }
                });
    }

    @Override
    public void userScencePicDelete() {
        ReqUserScencePicDelete request = new ReqUserScencePicDelete(mView.getSceneId());
        mModel.userScencePicDelete(request)
                .subscribe(new APISubcriber<ResUserScencePicDelete>() {
                    @Override
                    public void onFail(ResUserScencePicDelete baseResponse,String msg) {
                        mView.userScencePicModifyFail(msg);
                    }

                    @Override
                    public void onSuccess(ResUserScencePicDelete resUserScencePicDelete) {
                        mView.userScencePicModifySuccess(resUserScencePicDelete.getPic());
                    }
                });
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
