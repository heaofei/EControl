package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.request.ReqGetTimeRanking;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2018/7/6.
 */

public interface InformationContract {


    interface View extends BaseView<InformationContract.Presenter> {

        //获取社区信息成功
        void getCommunityListSuccess(ResGetCommunityList resGetTimeRanking);

        //获取社区信息失败
        void getCommunityListFali(String msg);


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
        //获取社区信息
        void getCommunityList(int page, int rows);

        // 添加收藏
        void publicCommunityCollectionUp(String communityId ,String userId );

        // 添加.取消，点赞社区文章
        void publicCommunityThumbUp(String communityId ,String userId );

    }
    interface Model extends BaseModel {
        //
        Observable<ResGetCommunityList> getCommunityList(ReqGetCommunityList reqGetCommunityList);

        // 添加收藏
        Observable<BaseResponse> publicCommunityCollectionUp(ReqPublicCommunityUser reqPublicCommunityUser);
        // 添加.取消，点赞社区文章
        Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityUser reqPublicCommunityUser);


    }

}
