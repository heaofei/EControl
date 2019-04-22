package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/5/2.
 *
 * @Description：
 */

public class ResGetProductTypeList extends BaseResponse {
    private List<ProductType> productTypeList;

    public List<ProductType> getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List<ProductType> productTypeList) {
        this.productTypeList = productTypeList;
    }

    public static class ProductType {
        private String type;//产品类型编号
        private String name;//产品类型名称
        private boolean isSelected = false;//是否选择该类型，为了显示添加后台并不会返回

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
