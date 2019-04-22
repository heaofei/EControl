package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.BaseView;
import com.mxkj.econtrol.bean.request.ReqDeleteOtherHomeUser;
import com.mxkj.econtrol.bean.request.ReqGetAtHomeUserList;
import com.mxkj.econtrol.bean.request.ReqGetScenePartList;
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

public interface RoomContract2 {
    interface View extends BaseView<Presenter> {
        void getScenePartListSuccess(ResGetScenePartList resGetScenePartList);

        void getScenePartListFail(String msg);

        //获取HouseId
        String getHouseId();

        //获取在家用户列表成功
        void getAtHomeUserListSuccess(List<ResGetAtHomeUserList.AtHomeUser> users);

        //获取在家用户列表失败
        void getAtHomeUserListFail(String msg);

        //用户场景图片修改成功
        void userScencePicModifySuccess(String picUrl);

        //用户场景图片修改失败
        void userScencePicModifyFail(String msg);

        //获取当前修改场景图片的场景Id
        String getSceneId();

        //获取修改场景的图片
        String getScenePic();

        //移交管理权时删除其它控制用户成功
        void deleteOtherHomeUserSuccess();

        //移交管理权时删除其它控制用户失败
        void deleteOtherHomeUserFail(String msg);

    }

    interface Presenter extends BasePresenter {
        /**
         * @Desc: 获取场景下的智能部件和状态
         * @author:liangshan
         */
        void getScenePartList(String sceneId);

        //获取在家用户列表
        void getAtHomeUserList();

        /**
         * @Desc:用户场景图片修改
         * @author:liangshan
         */
        void userScencePicModify();

        /**
         * @Desc:用户场景图片删除
         * @author:liangshan
         */
        void userScencePicDelete();

        //移交管理权时删除其它控制用户
        void deleteOtherHomeUser(String houseBindUserId);
    }

    interface Model extends BaseModel {
        /**
         * @Desc: 获取场景下的智能部件和状态
         * @author:liangshan
         */
        Observable<ResGetScenePartList> getScenePartList(ReqGetScenePartList reqGetScenePartList);


        //获取在家用户列表
        Observable<ResGetAtHomeUserList> getAtHomeUserList(ReqGetAtHomeUserList request);

        /**
         * @Desc:用户场景图片修改
         * @author:liangshan
         */
        Observable<ResUserScencePicModify> userScencePicModify(ReqUserScencePicModify request);

        /**
         * @Desc:用户场景图片删除
         * @author:liangshan
         */
        Observable<ResUserScencePicDelete> userScencePicDelete(ReqUserScencePicDelete request);


        //移交管理权时删除其它控制用户
        Observable<BaseResponse> deleteOtherHomeUser(ReqDeleteOtherHomeUser request);
    }
}
