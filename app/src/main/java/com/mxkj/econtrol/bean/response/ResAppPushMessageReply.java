package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by liangshan on 2017/5/2.
 *
 * @Description：
 */

public class ResAppPushMessageReply extends BaseResponse {

    private PushResult pushResult;

    public PushResult getPushResult() {
        return pushResult;
    }

    public void setPushResult(PushResult pushResult) {
        this.pushResult = pushResult;
    }


    public static class PushResult {
        private String isMember; //处理绑定申请消息返回结果，0是管理员申请，1是会员
        private String houseBindUserId;//清除其他用户时用到

        public String getIsMember() {
            return isMember;
        }

        public void setIsMember(String isMember) {
            this.isMember = isMember;
        }

        public String getHouseBindUserId() {
            return houseBindUserId;
        }

        public void setHouseBindUserId(String houseBindUserId) {
            this.houseBindUserId = houseBindUserId;
        }
    }
}
