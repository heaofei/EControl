package com.mxkj.econtrol.bean.request;

import com.mxkj.econtrol.base.BaseRequestEntity;

import java.util.List;

/**
 * Created by chanjun on 2017/10/31.
 *
 * @Description: 增加模式请求实体类(用来装载模式下面的部件选中状态的实体类)
 */

public class ReqPatternAddBean extends BaseRequestEntity {
    private String name; //模式名
    private String houseId; //房子id
    private String descriptions; // 描述，可为空
    private String patternId;     //  模式id ，只有在编辑的时候才有。新增时为空
    private List<PartSettingsBean> partSettings; //模式名

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getPatternId() {
        return patternId;
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }

    public List<PartSettingsBean> getPartSettings() {
        return partSettings;
    }

    public void setPartSettings(List<PartSettingsBean> partSettings) {
        this.partSettings = partSettings;
    }


}
