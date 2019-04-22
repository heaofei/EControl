package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqGetUserCommunity;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2018/7/6.
 */

public interface InformationCollectionContract {


    interface View extends BaseView<InformationCollectionContract.Presenter> {


        //获取我的收藏列表成功
        void getCommunityCollectionListSuccess(ResGetCommunityList resGetCommunityList);
        //获取我的收藏列表失败
        void getCommunityCollectionListFali(String msg);

        //添加添加收藏成功
        void publicCommunityCollectionUpSuccess(BaseResponse baseResponse);
        //添加收藏失败
        void publicCommunityCollectionUpFali(String msg);

        //添加.取消，点赞社区文章成功
        void publicCommunityThumbUpSuccess(BaseResponse baseResponse);
        //添加.取消，点赞社区文章失败
        void publicCommunityThumbUpFali(String msg);



    }

    interface Presenter extends BasePresenter {
        // 获取我的收藏列表           参数 page 0 rows 10 userId
        void getCommunityCollectionList(int page, int rows, String userId);


        // 添加收藏
        void publicCommunityCollectionUp(String communityId ,String userId );

        // 添加.取消，点赞社区文章
        void publicCommunityThumbUp(String communityId ,String userId );
    }

    interface Model extends BaseModel {
        // 获取我的收藏列表
        Observable<ResGetCommunityList> getCommunityCollectionList(ReqGetUserCommunity reqGetUserCommunity);


        // 添加收藏
        Observable<BaseResponse> publicCommunityCollectionUp(ReqPublicCommunityUser reqPublicCommunityUser);
        // 添加.取消，点赞社区文章
        Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityUser reqPublicCommunityUser);
    }

}
