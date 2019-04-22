package com.mxkj.econtrol.bean.tcpcmd;

import com.mxkj.econtrol.base.BaseEntity;
import com.mxkj.econtrol.base.BaseTCPCMDResponse;

/**
 * Created by liangshan on 2017/4/23.
 *
 * @Description：
 */

public class MessageNotify extends BaseTCPCMDResponse {
    private String action;//APP命令，请参考详细定义
    private FromUser fromUser;//发送用户编号
    private String pushId;//消息编号
    private String title;//消息标题


    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public FromUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(FromUser fromUser) {
        this.fromUser = fromUser;
    }

    public static class FromUser {
        public String id;//	用户编号
        public String headPicture;//	用户头像

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }
    }

}
