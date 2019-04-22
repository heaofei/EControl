package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetSceneList;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;

import java.util.List;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/13.
 *
 * @Description:
 */

public interface HouseMainContract {
    interface View extends BaseView<Presenter> {
        //获取场景列表成功
        void getSceneListSuccess(ResGetSceneList resGetSceneList);

        //获取场景列表失败
        void getSceneListFali(String msg);

        //获取HouseId
        String getHouseId();

        //获取在家用户列表成功
        void getAtHomeUserListSuccess(List<ResGetAtHomeUserList.AtHomeUser> users);

        //获取在家用户列表失败
        void getAtHomeUserListFail(String msg);

        //移交管理权时删除其它控制用户成功
        void deleteOtherHomeUserSuccess();

        //移交管理权时删除其它控制用户失败
        void deleteOtherHomeUserFail(String msg);

    }

    interface Presenter extends BasePresenter {
        //获取场景列表
        void getSceneList();

        //获取在家用户列表
        void getAtHomeUserList();

        //移交管理权时删除其它控制用户
        void deleteOtherHomeUser(String houseBindUserId);

    }

    interface Model extends BaseModel {
        //获取场景列表
        Observable<ResGetSceneList> getSceneList(ReqGetSceneList reqGetSceneList);

        //获取在家用户列表
        Observable<ResGetAtHomeUserList> getAtHomeUserList(ReqGetAtHomeUserList request);

        //移交管理权时删除其它控制用户
        Observable<BaseResponse> deleteOtherHomeUser(ReqDeleteOtherHomeUser request);


    }

}
