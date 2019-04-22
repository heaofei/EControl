package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqCommunityCommentThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityCommeants;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityUser;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetNewsDetail;

import rx.Observable;

/**
 * Created by ${  chenjun  } on 2018/7/6.
 */

public interface InformationCommentContract {


    interface View extends BaseView<InformationCommentContract.Presenter> {


        //获取我的评论列表成功
        void getCommunityCommentsMyListSuccess(ResGetCommunityCommentsMyList resGetCommunityCommentsMyList);
        //获取我的评论列表失败
        void getCommunityCommentsMyListFali(String msg);

    }

    interface Presenter extends BasePresenter {
        // 获取我的评论列表           参数 page 0 rows 10 userId type 1 收到的评论 2 发出的评论
        void getCommunityCommentsMyList(int page,int rows, String userId,String type);
    }

    interface Model extends BaseModel {
        // 获取我的评论列表
        Observable<ResGetCommunityCommentsMyList> getCommunityCommentsMyList(ReqGetCommunityCommentsMyList reqGetCommunityCommentsMyList);
    }

}
