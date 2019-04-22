package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description: 获取小区列表响应实体类
 */

public class ResGetHousingEstateList extends BaseResponse {
    private List<HousingEstat> housingEstatList;


    public List<HousingEstat> getHousingEstatList() {
        return housingEstatList;
    }

    public void setHousingEstatList(List<HousingEstat> housingEstatList) {
        this.housingEstatList = housingEstatList;
    }
}

