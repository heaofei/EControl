package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetHouseUserList;
import com.mxkj.econtrol.bean.request.ReqGetUserCommunity;
import com.mxkj.econtrol.bean.request.ReqHouseManagerSwitch;
import com.mxkj.econtrol.bean.request.ReqUnbindHouseUser;
import com.mxkj.econtrol.bean.request.ReqUserCheckBindHouse;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.bean.response.HouseUser;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.bean.response.ResGetUserCommunity;

import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public interface UserCommunityContract {

    interface View extends BaseView<Presenter> {
        void getUserCommunitySuccess(ResGetUserCommunity resGetUserCommunity);

        void getUserCommunityFail(String msg);

        String getUserId();

    }

    interface Presenter extends BasePresenter {
        void getUserCommunity(int page);
    }


    interface Model extends BaseModel {
        Observable<ResGetUserCommunity> getUserCommunity(ReqGetUserCommunity request);

    }
}
