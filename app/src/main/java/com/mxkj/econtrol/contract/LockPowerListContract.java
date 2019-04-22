package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqLockGetPartPermissionsList;
import com.mxkj.econtrol.bean.request.ReqLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.request.ReqLockPartPermissionsUpdate;
import com.mxkj.econtrol.bean.request.ReqPartBindGateway;
import com.mxkj.econtrol.bean.request.ReqPartUnbindGateway;
import com.mxkj.econtrol.bean.request.ReqPartUnbindSnid;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.response.ResLockPowerList;

import rx.Observable;

/**
 * Created by chanjun on 2018/01/19.
 *
 * @Description: 临时密码
 */

public interface LockPowerListContract {
    interface View extends BaseView<Presenter> {

        //锁权限列表成功
        void getPartPermissionsListSuccess(ResLockPowerList resLockPowerList);
        //锁权限列表失败
        void getPartPermissionsListFali(String msg);

        //// 授权操作成功
        void partPermissionsUpdateSuccess(BaseResponse baseResponse);
        //// 授权操作失败
        void partPermissionsUpdateFali(String msg);
        ////部件-部件解绑网关成功
        void partUnbindGatewaySuccess(BaseResponse baseResponse);
        //// 部件-部件解绑网关失败
        void partUnbindGatewayFali(String msg);
        //// 部件-部件解绑snid成功
        void partUnbindSnidSuccess(BaseResponse baseResponse);
        //// 部件-部件解绑snid失败
        void partUnbindSnidFali(String msg);

    }

    interface Presenter extends BasePresenter {
        //锁权限列表
        void getPartPermissionsList(ReqLockGetPartPermissionsList reqLockGetPartPermissionsList);
        // 授权操作
        void partPermissionsUpdate(ReqLockPartPermissionsUpdate reqLockPartPermissionsUpdate);
        //// 部件-部件解绑网关
        void partUnbindGateway(ReqPartUnbindGateway reqPartUnbindGateway);
        //部件-部件解绑snid
        void partUnbindSnid(ReqPartUnbindSnid reqPartUnbindSnid);

    }

    interface Model extends BaseModel {
        //锁权限列表
        Observable<ResLockPowerList> getPartPermissionsList(ReqLockGetPartPermissionsList reqLockGetPartPermissionsList);
        // 授权操作
        Observable<BaseResponse> partPermissionsUpdate(ReqLockPartPermissionsUpdate reqLockPartPermissionsUpdate);
        // 部件-部件解绑网关
        Observable<BaseResponse> partUnbindGateway(ReqPartUnbindGateway reqPartUnbindGateway);
        //部件-部件解绑snid
        Observable<BaseResponse> partUnbindSnid(ReqPartUnbindSnid reqPartUnbindSnid);
    }

}
