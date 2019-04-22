package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by liangshan on 2017/5/9.
 *
 * @Description： 用户场景图片修改响应类
 */

public class ResUserScencePicModify extends BaseResponse {
    private String pic; //修改后的图片url

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
