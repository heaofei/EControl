package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetAreaList;
import com.mxkj.econtrol.bean.request.ReqUserHeadPicModify;
import com.mxkj.econtrol.bean.request.ReqUserInfoModify;
import com.mxkj.econtrol.bean.response.City;
import com.mxkj.econtrol.bean.response.ResAreaBean;
import com.mxkj.econtrol.bean.response.ResGetAreaLsit;
import com.mxkj.econtrol.bean.response.ResUserHeadPicModify;

import java.util.List;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/27.
 *
 * @Description:
 */

public interface UserInfoModifyContract {
    interface View extends BaseView<Presenter> {
        //获取用户名
        String getUserName();

        //获取性别
        String getSex();

        //获取电话
        String getTel();

        //获取用户出生时间
        String getBoth();

        //获取用户地址
        String getAddress();

        //获取邮箱
        String getEmail();

        //修改信息成功
        void modifySuccess();

        //修改信息失败
        void modifyFail(String msg);

        //显示消息
        void showMsg(String msg);

        //修改头像成功
        void modidyHeaderSuccess(ResUserHeadPicModify result);

        //获取昵称
        String getNiceName();

        //获取昵称
        String getRealName();


        //获取地区列表信息成功
        void getAreaListSuccess(ResAreaBean resAreaBean);

        //获取地区列表信息失败
        void getAreaListFail(String msg);




    }

    interface Presenter extends BasePresenter {
        //修改用户信息
        void userInfoModify();

        //修改头像
        void userHeadPicModify(String pic);

        //获取地区列表信息
        void getAreaList(String areaCode);

    }

    interface Model extends BaseModel {
        //修改用户信息
        Observable<BaseResponse> userInfoModify(ReqUserInfoModify reqUserInfoModify);

        //修改头像
        Observable<ResUserHeadPicModify> userHeadPicModify(ReqUserHeadPicModify reqUserHeadPicModify);

        //获取地区列表信息
        Observable<ResAreaBean> getAreaList(ReqGetAreaList reqGetAreaList);
    }
}
