package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqCommunityCommentThumbUp;
import com.mxkj.econtrol.bean.request.ReqGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.request.ReqGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.contract.InformationCommentContract;
import com.mxkj.econtrol.contract.InformationContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2018/7/6.
 *
 * @Description:
 */

public class InformationCommentModel implements InformationCommentContract.Model {


    /**获取我的评论列表**/
    @Override
    public Observable<ResGetCommunityCommentsMyList> getCommunityCommentsMyList(ReqGetCommunityCommentsMyList reqCommunityCommentThumbUp) {
        return RetrofitUtil.getInstance().getmApiService()
                .getCommunityCommentsMyList(reqCommunityCommentThumbUp.toJsonStr())
                .compose(new APITransFormer<ResGetCommunityCommentsMyList>());
    }
}
