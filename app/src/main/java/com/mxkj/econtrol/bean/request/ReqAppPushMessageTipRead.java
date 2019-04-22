package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

/**
 * Created by ${  chenjun  } on 2017/10/27.
 */

public class ReqAppPushMessageTipRead extends BaseRequestEntity {

    private String[] ids ;

    public ReqAppPushMessageTipRead(String[] pushId) {
        this.ids = pushId;
    }

    public String[] getPushId() {
        return ids;
    }

    public void setPushId(String[] pushId) {
        this.ids = pushId;
    }
}
