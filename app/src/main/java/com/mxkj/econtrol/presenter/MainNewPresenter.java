package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetRoomList;
import com.mxkj.econtrol.bean.request.ReqGetUserHouseList;
import com.mxkj.econtrol.bean.request.ReqPatternActive;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.request.ReqSceneEdit;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTotal;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;
import com.mxkj.econtrol.contract.FragmentMainContract;
import com.mxkj.econtrol.contract.MainNewContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class MainNewPresenter implements MainNewContract.Presenter {
    private MainNewContract.Model mModel;
    private MainNewContract.View mView;

    public MainNewPresenter(MainNewContract.View mView, MainNewContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    /***编辑房间名字***/
    @Override
    public void sceneEdit(String id, String name) {
        ReqSceneEdit reqSceneEdit = new ReqSceneEdit(id,name);
        mModel.sceneEdit(reqSceneEdit)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.sceneEditFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.sceneEditSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void start() {

    }


}
