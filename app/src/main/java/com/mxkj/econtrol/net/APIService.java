package com.mxkj.econtrol.net;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqQrLogin;
import com.mxkj.econtrol.bean.response.CommentList;
import com.mxkj.econtrol.bean.response.CommunityCommentsInfoList;
import com.mxkj.econtrol.bean.response.ResAppPushMessageReply;
import com.mxkj.econtrol.bean.response.ResAppPushMessageTipCount;
import com.mxkj.econtrol.bean.response.ResAppTodoList;
import com.mxkj.econtrol.bean.response.ResAreaBean;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessage;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetApplyBindHouseList;
import com.mxkj.econtrol.bean.response.ResGetAreaLsit;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.bean.response.ResGetBuildingList;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsList;
import com.mxkj.econtrol.bean.response.ResGetCommunityCommentsMyList;
import com.mxkj.econtrol.bean.response.ResGetCommunityList;
import com.mxkj.econtrol.bean.response.ResGetCommunityThumbUpList;
import com.mxkj.econtrol.bean.response.ResGetDurationRanking;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetHouseControlLogList;
import com.mxkj.econtrol.bean.response.ResGetHouseList;
import com.mxkj.econtrol.bean.response.ResGetHouseUserList;
import com.mxkj.econtrol.bean.response.ResGetHousingEstateList;
import com.mxkj.econtrol.bean.response.ResGetNewsDetail;
import com.mxkj.econtrol.bean.response.ResGetPartUseTime;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetProductTypeList;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.ResGetSceneList;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.bean.response.ResGetScenePartList;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTimerList;
import com.mxkj.econtrol.bean.response.ResGetScenePartTotal;
import com.mxkj.econtrol.bean.response.ResGetShopProductList;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncement;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncementDetail;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;
import com.mxkj.econtrol.bean.response.ResGetUserCamera;
import com.mxkj.econtrol.bean.response.ResGetUserCommunity;
import com.mxkj.econtrol.bean.response.ResGetUserEstateList;
import com.mxkj.econtrol.bean.response.ResGetUserHouseList;
import com.mxkj.econtrol.bean.response.ResGetUserInfo;
import com.mxkj.econtrol.bean.response.ResGethouseUserInfo;
import com.mxkj.econtrol.bean.response.ResInitailEntity;
import com.mxkj.econtrol.bean.response.ResLockPartPasswordTemporarySet;
import com.mxkj.econtrol.bean.response.ResLockPasswordGrant;
import com.mxkj.econtrol.bean.response.ResLockPowerList;
import com.mxkj.econtrol.bean.response.ResPartPasswordImmediatelySet;
import com.mxkj.econtrol.bean.response.ResSetDefaultUserHouse;
import com.mxkj.econtrol.bean.response.ResUserHeadPicModify;
import com.mxkj.econtrol.bean.response.ResUserLogin;
import com.mxkj.econtrol.bean.response.ResUserRegist;
import com.mxkj.econtrol.bean.response.ResUserScencePicDelete;
import com.mxkj.econtrol.bean.response.ResUserScencePicModify;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by liangshan on 2017/3/21.
 *
 * @Destription:
 */

public interface APIService {

    //注册发送验证码
    String USER_REGIST_SMS = "userRegistSms";
    //修改用户信息
    String USER_INFO_MODIFY = "userInfoModify";
    //修改用户密码
    String USER_PWD_MODIFY = "userPwdModify";
    //重置密码
    String USER_PASSWORD_RESET = "userPassWordReset";
    //用户登出
    String USER_LOGOUT = "userLogout";
    //用户提交绑定房子申请接口
    String USER_APPLY_BIND_HOUSE = "userApplyBindHouse";
    //户主审核申请
    String USER_CHECK_BIND_HOUSE = "userCheckBindHouse";
    //解除绑定控制用户
    String UNBIND_HOUSE_USER = "unbindHouseUser";
    //用户心情修改
    String USER_MOOD_MODIFY = "userMoodModify";
    //社区评论接口
    String PUBLIC_COMMUNITY_COMMENTS = "publicCommunityComments";
    //社区点赞接口
    String PUBLIC_COMMUNITY_THUMBUP = "publicCommunityThumbUp";
    //删除社区消息
    String DELETE_COMMUNITY = "deleteCommunity";
    //删除评论
    String DELETE_COMMUNITY_COMMENTS = "deleteCommunityComments";
    //删除点赞
    String DELETE_COMMUNITYT_HUMBUP = "deleteCommunityThumbUp";
    //发布社区消息
    String PUBLI_CCOMMUNITY = "publicCommunity";
    //1.3.10.2.	推送信息处理（appPushMessageReply）
    String APP_PUSH_MESSAGE_REPLY = "appPushMessageReply";
    String UNBIND_HOUSE_USER_4_REJECT = "unbindHouseUser4Reject";
    //移交房子管理权时删除其他控制用户
    String DELETE_OTHER_HOME_USER = "deleteOtherHomeUser";
    //转换管理权
    String HOUSE_MANAGER_SWITCH = "houseManagerSwitch";
    // 社区分享
    String COMMUNITY_SHARE = "getCommunityShare?id=";


    /**
     * 初始化接口
     *
     * @param reqInitailEntity
     * @return
     */
    @FormUrlEncoded
    @POST("initail")
    Observable<ResInitailEntity> initail(@Field("data") String reqInitailEntity);

    /**
     * 用户登录接口
     *
     * @param reqInitailEntity
     * @return
     */
    @FormUrlEncoded
    @POST("userLogin")
    Observable<ResUserLogin> userLogin(@Field("data") String reqInitailEntity);


    /**
     * 用户注册接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("userRegist")
    Observable<ResUserRegist> userRegist(@Field("data") String data);


    /**
     * 更改头像
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("userHeadPicModify")
    Observable<ResUserHeadPicModify> userHeadPicModify(@Field("data") String data);

    /**
     * 获取地区列表信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getAreaList")
    Observable<ResGetAreaLsit> getAreaList(@Field("data") String data);

    /**
     * 获取地区列表信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getAreaList")
    Observable<ResAreaBean> getAreaList02(@Field("data") String data);


    /**
     * 获取小区列表信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getHousingEstateList")
    Observable<ResGetHousingEstateList> getHousingEstateList(@Field("data") String data);


    /**
     * 获取楼盘单位列表信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getBuildingList")
    Observable<ResGetBuildingList> getBuildingList(@Field("data") String data);

    /**
     * 获取房子列表信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getHouseList")
    Observable<ResGetHouseList> getHouseList(@Field("data") String data);

    /**
     * 用户提交绑定房子申请接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("userApplyBindHouse")
    Observable<BaseResponse> userApplyBindHouse(@Field("data") String data);

    /**
     * 获取用户绑定的房子列表接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getUserHouseList")
    Observable<ResGetUserHouseList> getUserHouseList(@Field("data") String data);

    /**
     * 获取房子的场景接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getSceneList")
    Observable<ResGetSceneList> getSceneList(@Field("data") String data);

    /**
     * 获取房子的列表接口(直接定义的叫获取场景接口所以是 getSceneList)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getSceneList")
    Observable<ResGetRoomList> getRoomList(@Field("data") String data);

    /**
     * 获取房子常用部件
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("scenePartTotal")
    Observable<ResGetScenePartTotal> scenePartTotal(@Field("data") String data);

    /**
     * 房子控制用户列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getHouseUserList")
    Observable<ResGetHouseUserList> getHouseUserList(@Field("data") String data);

    /**
     * 获取场景下所有部件和状态
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getScenePartList")
    Observable<ResGetScenePartList> getScenePartList(@Field("data") String data);


    /**
     * 获取房子所有的智能设备部件信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getHouseAllPartList")
    Observable<ResGetHouseAllPartList> getHouseAllPartList(@Field("data") String data);




    /**
     * 获取社区信息评论
     *
     * @param data
     * @return
     */
  /*  @FormUrlEncoded
    @POST("getCommunityCommentsList")
    Observable<ResGetCommunityCommentsList> getCommunityCommentsList(@Field("data") String data);*/

    /**
     * 1.3.8.3.	获取点赞信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getCommunityThumbUpList")
    Observable<ResGetCommunityThumbUpList> getCommunityThumbUpList(@Field("data") String data);

    /**
     * 用户操作日志
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getAppPushMessage")
    Observable<ResGetAppPushMessage> getAppPushMessage(@Field("data") String data);

    /**
     * 1.3.10.1.获取房子操作日子列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getHouseControlLogList")
    Observable<ResGetHouseControlLogList> getHouseControlLogList(@Field("data") String data);

    /**
     * 获取商城商品信息列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getShopProductList")
    Observable<ResGetShopProductList> getShopProductList(@Field("data") String data);

    /**
     * 送信息处理
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("appPushMessageReply")
    Observable<ResAppPushMessageReply> appPushMessageReply(@Field("data") String data);

    /**
     * 获取用户的小区信息列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getUserEstateList")
    Observable<ResGetUserEstateList> getUserEstateList(@Field("data") String data);

    /**
     * 获取在家用户列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getAtHomeUserList")
    Observable<ResGetAtHomeUserList> getAtHomeUserList(@Field("data") String data);

    /**
     * 1.3.9.2.	获取产品类型信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getProductTypeList")
    Observable<ResGetProductTypeList> getProductTypeList(@Field("data") String data);

    /**
     * 用户场景图片修改
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("userScencePicModify")
    Observable<ResUserScencePicModify> userScencePicModify(@Field("data") String data);

    /**
     * 用户场景图片删除
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("userScencePicDelete")
    Observable<ResUserScencePicDelete> userScencePicDelete(@Field("data") String data);

    /**
     * 获取用户发布过的社区
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getUserCommunityList")
    Observable<ResGetUserCommunity> getUserCommunity(@Field("data") String data);


    /**
     * 请求返回BaseResponse的接口
     *
     * @param data
     * @param api
     * @return
     */
    @FormUrlEncoded
    @POST("{api}")
    Observable<BaseResponse> API(@Field("data") String data, @Path("api") String api);

    //下载广告图片
    @GET
    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);


    //-------------------------------------------------------------------------------------------------------------
    //二期修改接口




    /**
     * 1.3.6.8.	设置默认房子
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("setDefaultUserHouse")
    Observable<ResSetDefaultUserHouse> setDefaultUserHouse(@Field("data") String data);

 /**
     * 1.3.6.6.	房子控制用户信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getHouseUserInfo")
    Observable<ResGethouseUserInfo> getHouseUserInfo(@Field("data") String data);


    /**
     * 消息推送列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getAppPushMessageList")
    Observable<ResGetAppPushMessageList> getAppPushMessageList(@Field("data") String data);

    /**
     * 系统通知列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("GetSysAnnouncementPage")
    Observable<ResGetSysAnnouncement> GetSysAnnouncementPage(@Field("data") String data);


    /**
     * 系统通知详情
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("GetSysAnnouncementDetail")
    Observable<ResGetSysAnnouncementDetail> GetSysAnnouncementDetail(@Field("data") String data);

    /**
     * 意见反馈
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("feedbackSubmit")
    Observable<ResGetSysAnnouncementDetail> feedbackSubmit(@Field("data") String data);

    /**
     * 获取模式（主页场景模式）
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getPatternList")
    Observable<ResGetPatternList> getPatternList(@Field("data") String data);

    /**
     *  房间名的修改
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("sceneEdit")
    Observable<BaseResponse> sceneEdit(@Field("data") String data);

    /**
     * 获取模式详情
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getPatternDetail")
    Observable<ResGetPatternDetail> getPatternDetail(@Field("data") String data);

    /**
     * 模式应用接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("patternActive")
    Observable<BaseResponse> patternActive(@Field("data") String data);

    /**
     * 模式删除接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("patternDelete")
    Observable<BaseResponse> patternDelete(@Field("data") String data);

    /**
     * 模式新增接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("patternAdd")
    Observable<BaseResponse> patternAdd(@Field("data") String data);


    /**
     * 模式编辑接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("patternEdit")
    Observable<BaseResponse> patternEdit(@Field("data") String data);

    /**
     * 获取部件详情接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getScenePartDetail")
    Observable<ResGetScenePartDetail> getScenePartDetail(@Field("data") String data);

    /**
     * 改变部件的定时开启状态
     *
     * @param data
     * @return
     */
//    @FormUrlEncoded
//    @POST("scenePartTimerStatusChange")
//    Observable<BaseResponse> scenePartTimerStatusChange(@Field("data") String data);

     /**
     * 获取场景的部件设备-操作的定时列表信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getScenePartOperatorTimerList")
    Observable<ResGetScenePartOperatorTimerList> getScenePartOperatorTimerList(@Field("data") String data);

     /**
     * 场景的部件设备操作定时器保存
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("scenePartOperatorTimerSave")
    Observable<BaseResponse> scenePartOperatorTimerSave(@Field("data") String data);

    /**
     * 场景的部件设备操作定时器删除
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("scenePartOperatorTimerDelete")
    Observable<BaseResponse> scenePartOperatorTimerDelete(@Field("data") String data);

    /**
     * 场景的部件设备操作定时器状态修改（某个定时的开关，定时的时间）
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("scenePartOperatorTimerStatusChange")
    Observable<BaseResponse> scenePartOperatorTimerStatusChange(@Field("data") String data);

    /**
     * 场景的部件设备操作定时器状态修改(修改状态和开关、名字)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("scenePartEdit")
    Observable<BaseResponse> scenePartEdit(@Field("data") String data);

    /**
     * 用户信息获取
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getUserInfo")
    Observable<ResGetUserInfo> getUserInfo(@Field("data") String data);


    /**
     * 获取房间使用次数接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getTimeRanking")
    Observable<ResGetTimeRanking> getTimeRanking(@Field("data") String data);

    /**
     * 获取房间使用时长接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getDurationRanking")
    Observable<ResGetDurationRanking> getDurationRanking(@Field("data") String data);

    /**
     * 获取控制排名的日期信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getRankingDateList")
    Observable<ResGetRankingDateList> getRankingDateList(@Field("data") String data);

    /**
     * 消息未读条数接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("appPushMessageTipCount")
    Observable<ResAppPushMessageTipCount> appPushMessageTipCount(@Field("data") String data);

    /**
     * 设置消息已读接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("appPushMessageTipRead")
    Observable<ResGetRankingDateList> appPushMessageTipRead(@Field("data") String data);

   /**
     * getApplyBindHouseList 获取申请列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getApplyBindHouseList")
    Observable<ResGetApplyBindHouseList> getApplyBindHouseList(@Field("data") String data);

 /**
     * applyBindHouseAudit 审核申请接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("applyBindHouseAudit")
    Observable<BaseResponse> applyBindHouseAudit(@Field("data") String data);

//////////////////////////////门锁////////////////////////--------------------------------------------------------------------------------------------------------------

    /**
     * partPasswordInit 初始房子的部件操作密码
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partPasswordInit")
    Observable<BaseResponse> partPasswordInit(@Field("data") String data);


    /**
     * partPasswordGrant 房子的部件操作密码授权
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partPasswordGrant")
    Observable<ResLockPasswordGrant> partPasswordGrant(@Field("data") String data);

     /**
     * partPasswordChange 更改房子的部件操作密码
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partPasswordChange")
    Observable<BaseResponse> partPasswordChange(@Field("data") String data);

    /**
     * partPasswordTemporarySet 门锁临时密码
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partPasswordTemporarySet")
    Observable<ResLockPartPasswordTemporarySet> partPasswordTemporarySet(@Field("data") String data);

    /**
     * partPasswordImmediatelySet 青松门锁立即开锁
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partPasswordImmediatelySet")
    Observable<BaseResponse> partPasswordImmediatelySet(@Field("data") String data);

    /**
     * partPasswordTemporarySet 门锁权限列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getPartPermissionsList")
    Observable<ResLockPowerList> getPartPermissionsList(@Field("data") String data);

    /**
     * partPasswordTemporarySet 门锁授权操作
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partPermissionsUpdate")
    Observable<BaseResponse> partPermissionsUpdate(@Field("data") String data);

     /**
     * userApplyPartPassword  业主忘记密码提交审核
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("userApplyPartPassword")
    Observable<BaseResponse> userApplyPartPassword (@Field("data") String data);

    /**
     * houseManagerSwitchAssign  旧业主转让指派接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("houseManagerSwitchAssign")
    Observable<BaseResponse> houseManagerSwitchAssign (@Field("data") String data);

    /**
     * houseManagerSwitchHandle  新业主确认转让接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("houseManagerSwitchHandle")
    Observable<BaseResponse> houseManagerSwitchHandle (@Field("data") String data);

    /**
     * appTodoList  待处理提醒查询接口
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("appTodoList")
    Observable<ResAppTodoList> appTodoList(@Field("data") String data);

    /**
     * gatewayBindHouse  部件-部件绑定网关
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partBindGateway")
    Observable<BaseResponse> partBindGateway(@Field("data") String data);

    /**
     * partUnbindGateway  部件-部件解绑网关
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partUnbindGateway")
    Observable<BaseResponse> partUnbindGateway(@Field("data") String data);

    /**
     * partBindSn  部件-部件绑定snid
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partBindSn")
    Observable<BaseResponse> partBindSn(@Field("data") String data);

     /**
     * partUnbindSn  部件-部件解绑snid
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("partUnbindSn")
    Observable<BaseResponse> partUnbindSn(@Field("data") String data);

    /**
     * gatewayUnbindHouse  网关解绑房子
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("gatewayUnbindHouse")
    Observable<BaseResponse> gatewayUnbindHouse(@Field("data") String data);

//---APP2.0版本开始---------------------------------------------------------------------------------------------------------------------------------------------------------------



    /**
     * 1.3.8.1.	获取社区信息(getCommunityList)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getCommunityList")
    Observable<ResGetCommunityList> getCommunityList(@Field("data") String data);


    /**
     * 获取社区文章详细内容及评论信息(getCommunityCommentsList)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getCommunityCommentsList")
    Observable<ResGetNewsDetail> getCommunityCommentsList(@Field("data") String data);

    /**
     * 添加社区评论信息(publicCommunityComments)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("publicCommunityComments")
    Observable<BaseResponse> publicCommunityComments(@Field("data") String data);

    /**
     * 添加收藏(publicCommunityCollectionUp)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("publicCommunityCollectionUp")
    Observable<BaseResponse> publicCommunityCollectionUp(@Field("data") String data);

    /**
     * 添加阅读数(publicCommunityReadingUp)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("publicCommunityReadingUp")
    Observable<BaseResponse> publicCommunityReadingUp(@Field("data") String data);

    /**
     * 添加.取消，点赞社区文章(publicCommunityThumbUp)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("publicCommunityThumbUp")
    Observable<BaseResponse> publicCommunityThumbUp(@Field("data") String data);

     /**
     * 添加文章的评论点赞(publicCommunityCommentThumbUp)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("publicCommunityCommentThumbUp")
    Observable<BaseResponse> publicCommunityCommentThumbUp(@Field("data") String data);


    /**
     * 查询我的评论列表(getCommunityCommentsMyList)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getCommunityCommentsMyList")
    Observable<ResGetCommunityCommentsMyList> getCommunityCommentsMyList(@Field("data") String data);

    /**
     * 获取二级评论列表(getCommunityCommentsInfoList)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getCommunityCommentsInfoList")
    Observable<CommunityCommentsInfoList>getCommunityCommentsInfoList(@Field("data") String data);

     /**
     * 查询我的收藏列表(getCommunityCollectionList)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getCommunityCollectionList")
    Observable<ResGetCommunityList> getCommunityCollectionList(@Field("data") String data);

    /**
     * 获取设备定时列表（新）(getScenePartTimerList)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getScenePartTimerList")
    Observable<ResGetScenePartTimerList> getScenePartTimerList(@Field("data") String data);

    /**
     * 设备定时任务开始/关闭（新）(getScenePartTimerList)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("scenePartTimerStatusChange")
    Observable<BaseResponse> scenePartTimerStatusChange(@Field("data") String data);

    /**
     * 定时保存/修改(scenePartTimerSave)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("scenePartTimerSave")
    Observable<BaseResponse> scenePartTimerSave(@Field("data") String data);

    /**
     * 定时删除(scenePartTimerDelete)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("scenePartTimerDelete")
    Observable<BaseResponse> scenePartTimerDelete(@Field("data") String data);

    /**
     * 扫描二维码，授权ipda登陆(qrLogin)
     *
     *   @Field 作用于方法的参数，用于发送一个表单请求
     *
     *  这里是直接提交json 数据（非表单）
     *
     *  详细注解的含义请看 https://blog.csdn.net/qiang_xi/article/details/53959437
     * @param data
     * @return
     */
    @POST("qrLogin")
    Observable<BaseResponse> qrLogin(@Body ReqQrLogin data);

    /**
     * 消息中心清空(deleteAppPushMessage)
     *
     *  清空消息列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("deleteAppPushMessage")
    Observable<BaseResponse> deleteAppPushMessage(@Field("data") String data);




    //----安防摄像头接口----------------------------------------------------------------------------------------------------------------------------------------------------------------------------



    /**
     * 业主绑定视频  传 deviceId 视频设备ID 和 业主ID(publicUserCamera)
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("publicUserCamera")
    Observable<BaseResponse> publicUserCamera(@Field("data") String data);

    /**
     * 视频监控删除 deleteUserCamera   userId 用户ID houseId 房间ID    id 视频监控摄像头UUID
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("deleteUserCamera")
    Observable<BaseResponse> deleteUserCamera(@Field("data") String data);

    /**
     * 视频监控列表 getUserCamera   page rows 分页    userId 用户ID houseId 房间ID
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getUserCamera")
    Observable<ResGetUserCamera> getUserCamera(@Field("data") String data);

    /**
     * 视频监控分享 publicCameraShare  deviceId 设备ID  userId 用户ID houseId 房间ID
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("publicCameraShare")
    Observable<BaseResponse> publicCameraShare(@Field("data") String data);

    /**
     * 视频监控修改  设备ID  userId 用户ID houseId 房间ID /  可选传参数  name摄像头名称  label标签
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("updateUserCamera")
    Observable<BaseResponse> updateUserCamera(@Field("data") String data);


     /**
     * getPartUseTime  灯光时间统计
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("getPartUseTime")
    Observable<ResGetPartUseTime> getPartUseTime(@Field("data") String data);


    // 统计使用（统计使用时长）
    String getPartUseTimeShare = "getPartUseTimeShare";



}
