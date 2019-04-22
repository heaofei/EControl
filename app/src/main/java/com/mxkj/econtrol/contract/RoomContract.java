package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetScenePartList;
import com.mxkj.econtrol.bean.request.ReqUserInfoModify;
import com.mxkj.econtrol.bean.request.ReqUserScencePicDelete;
import com.mxkj.econtrol.bean.request.ReqUserScencePicModify;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetScenePartList;
import com.mxkj.econtrol.bean.response.ResUserScencePicDelete;
import com.mxkj.econtrol.bean.response.ResUserScencePicModify;

import java.util.List;

import rx.Observable;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description：
 */

public interface RoomContract {
    interface View extends BaseView<Presenter> {
        void getScenePartListSuccess(ResGetScenePartList resGetScenePartList);

        void getScenePartListFail(String msg);



    }

    interface Presenter extends BasePresenter {
        /**
         * @Desc: 获取场景下的智能部件和状态
         * @author:liangshan
         */
        void getScenePartList(String sceneId);
    }

    interface Model extends BaseModel {
        /**
         * @Desc: 获取场景下的智能部件和状态
         * @author:liangshan
         */
        Observable<ResGetScenePartList> getScenePartList(ReqGetScenePartList reqGetScenePartList);

    }
}
