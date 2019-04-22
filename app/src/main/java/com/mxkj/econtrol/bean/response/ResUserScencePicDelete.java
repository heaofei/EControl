package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by liangshan on 2017/5/10.
 *
 * @Description： 用户场景图片删除分析反应实体类
 */

public class ResUserScencePicDelete extends BaseResponse {
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
