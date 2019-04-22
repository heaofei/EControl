package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqDeleteCommunity;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunity;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityThumbUp;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetUserEstateList;

import java.util.List;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description：
 */

public interface PublicCommunityContract {

    interface View extends BaseView<Presenter> {



        void showMsg(String msg);

        /**
         * @Desc: 获取列表成功
         * @author:liangshan
         */
        void getCommunityListSuccess(ResGetCommunityList resGetCommunityList);

        /**
         * @Desc: 获取列表失败
         * @author:liangshan
         */
        void getCommunityListFail(String msg);

        /**
         * @Desc: 点赞成功
         * @author:liangshan
         */
        void tumpUpSuccess();

        /**
         * @Desc: 点赞失败
         * @author:liangshan
         */
        void tumpUpFail();

    }

    interface Presenter extends BasePresenter {
        /**
         * @Desc: 获取社区消息列表
         * @author:liangshan
         */
        void getCommunityList(int page);

        /**
         * @Desc: 发布社区消息
         * @author:liangshan
         */
        void publicCommunity();

        /**
         * @Desc: 删除消息
         * @author:liangshan
         */
        void deleteCommunity(String id);
        /**
         * @Desc: 点赞
         * @author:liangshan
         */
        void publicCommunityThumbUp(String id);


    }

    interface Model extends BaseModel {

        /**
         * @Desc: 获取社区消息列表
         * @author:liangshan
         */
        Observable<ResGetCommunityList> getCommunityList(ReqGetCommunityList reqGetCommunityList);

        /**
         * @Desc: 发布社区消息
         * @author:liangshan
         */
        Observable<BaseResponse> publicCommunity(ReqPublicCommunity reqPublicCommunity);

        /**
         * @Desc: 删除消息
         * @author:liangshan
         */
        Observable<BaseResponse> deleteCommunity(ReqDeleteCommunity reqDeleteCommunity);


        /**
         * @Desc: 点赞
         * @author:liangshan
         */
        Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityThumbUp request);
        /**
         * @Desc: 获取用户的小区信息列表
         * @author:liangshan
         */
        Observable<ResGetUserEstateList> getUserEstateList();

    }
}
