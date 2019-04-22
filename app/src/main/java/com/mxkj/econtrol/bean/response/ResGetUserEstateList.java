package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/6/2.
 *
 * @Description： 1.3.5.3.    获取用户的小区信息列表
 */

public class ResGetUserEstateList extends BaseResponse {

    private List<HousingEstat> housingEstatList;

    public List<HousingEstat> getHousingEstatList() {
        return housingEstatList;
    }

    public void setHousingEstatList(List<HousingEstat> housingEstatList) {
        this.housingEstatList = housingEstatList;
    }

    public static class HousingEstat {
        private String housingEstatId;//	小区编码
        private String housingEstatName;//buildingList[]		小区名称

        public String getHousingEstatName() {
            return housingEstatName;
        }

        public void setHousingEstatName(String housingEstatName) {
            this.housingEstatName = housingEstatName;
        }

        public String getHousingEstatId() {
            return housingEstatId;
        }

        public void setHousingEstatId(String housingEstatId) {
            this.housingEstatId = housingEstatId;
        }

        @Override
        public String toString() {
            return getHousingEstatName();
        }
    }
}
