package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by liangshan on 2017/3/29.
 *
 * @Description:
 */

public class ResUserHeadPicModify extends BaseResponse {
    private String userHeadPicture;

    public String getUserHeadPicture() {
        return userHeadPicture;
    }

    public void setUserHeadPicture(String userHeadPicture) {
        this.userHeadPicture = userHeadPicture;
    }
}
