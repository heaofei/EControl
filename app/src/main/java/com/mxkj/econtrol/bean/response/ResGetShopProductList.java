package com.mxkj.econtrol.bean.response;

import com.google.gson.annotations.SerializedName;
import com.mxkj.econtrol.base.BaseEntity;
import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/26.
 *
 * @Description： 获取商城商品房子响应实体类
 */

public class ResGetShopProductList extends BaseResponse {


    /**
     * page : {"content":[{"bigPic":"/shop-product/2017-04-22/14928309238766aMk.jpg","description":"描述内容","id":"40288f815b8a183e015b8a19359a0001","orderNumber":1,"smallPic":"/shop-product/2017-04-22/14928309238637s1d.jpg","state":"1","tel":"2222","title":"吸顶灯","type":"t-1"}]}
     * firstPage : true
     * lastPage : true
     */

    private Page page;


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


    public static class Page extends BaseEntity {
        private List<Product> content;

        public List<Product> getContent() {
            return content;
        }

        public void setContent(List<Product> content) {
            this.content = content;
        }

        private boolean firstPage;
        private boolean lastPage;

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public static class Product extends BaseEntity {
            /**
             * bigPic : /shop-product/2017-04-22/14928309238766aMk.jpg
             * description : 描述内容
             * id : 40288f815b8a183e015b8a19359a0001
             * orderNumber : 1
             * smallPic : /shop-product/2017-04-22/14928309238637s1d.jpg
             * state : 1
             * tel : 2222
             * title : 吸顶灯
             * type : t-1
             */

            private String bigPic;
            private String description;
            private String id;
            private int orderNumber;
            private String smallPic;
            @SerializedName("state")
            private String stateX;
            private String tel;
            private String title;
            private String type;

            public String getBigPic() {
                return bigPic;
            }

            public void setBigPic(String bigPic) {
                this.bigPic = bigPic;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(int orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getSmallPic() {
                return smallPic;
            }

            public void setSmallPic(String smallPic) {
                this.smallPic = smallPic;
            }

            public String getStateX() {
                return stateX;
            }

            public void setStateX(String stateX) {
                this.stateX = stateX;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
