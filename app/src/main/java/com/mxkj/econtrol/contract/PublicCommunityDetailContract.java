package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqDeleteCommunityComments;
import com.mxkj.econtrol.bean.request.ReqDeleteCommunityThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityThumbUpList;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityComment;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityThumbUp;
import com.mxkj.econtrol.bean.response.CommentContent;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsList;
import com.mxkj.econtrol.bean.response.ResGetCommunityThumbUpList;

import java.util.List;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/21.
 *
 * @Description：
 */

public interface PublicCommunityDetailContract {
    interface View extends BaseView<Presenter> {
        /**
         * @Desc: 设置评论列表适配器
         * @author:liangshan
         */
        void setAdapter(List<CommentContent> datas);

        /**
         * @Desc: 提示消息
         * @author:liangshan
         */
        void showMsg(String msg);

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
         * @Desc: 获取评论信息
         * @author:liangshan
         */
        void getCommunityCommentsList(String coummunityId);

        /**
         * @Desc: 评论
         * @author:liangshan
         */
        void publicCommunityComments(String id, String comment,String replyUserId);

        /**
         * @Desc: 点赞
         * @author:liangshan
         */
        void publicCommunityThumbUp(String id);

        /**
         * @Desc: 获取点赞信息
         * @author:liangshan
         */
        void getCommunityThumbUpList();


        /**
         * @Desc: 删除评论消息
         * @author:liangshan
         */
        void deleteCommunityComments(String id);

        /**
         * @Desc: 取消评论
         * @author:liangshan
         */
        void deleteCommunityThumbUp(String id);

    }

    interface Model extends BaseModel {
        /**
         * @Desc: 获取评论信息
         * @author:liangshan
         */
        Observable<ResGetCommunityCommentsList> getCommunityCommentsList(ReqGetCommunityCommentsList request);

        /**
         * @Desc: 评论
         * @author:liangshan
         */
        Observable<BaseResponse> publicCommunityComments(ReqPublicCommunityComment request);

        /**
         * @Desc: 点赞
         * @author:liangshan
         */
        Observable<BaseResponse> publicCommunityThumbUp(ReqPublicCommunityThumbUp request);

        /**
         * @Desc: 获取点赞信息
         * @author:liangshan
         */
        Observable<ResGetCommunityThumbUpList> getCommunityThumbUpList(ReqGetCommunityThumbUpList reauest);


        /**
         * @Desc: 删除评论消息
         * @author:liangshan
         */
        Observable<BaseResponse> deleteCommunityComments(ReqDeleteCommunityComments request);

        /**
         * @Desc: 取消评论
         * @author:liangshan
         */
        Observable<BaseResponse> deleteCommunityThumbUp(ReqDeleteCommunityThumbUp request);
    }
}
