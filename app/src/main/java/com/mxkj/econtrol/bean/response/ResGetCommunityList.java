package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 响应获取社区信息接口响应实体类
 */

public class ResGetCommunityList extends BaseResponse {
    private  Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public class Page {
       public List<CommunityContent> content;
        private boolean firstPage;//是否第一页
        private boolean lastPage;//	是否最后一页
        private int totalPages;//	总页数


        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<CommunityContent> getContent() {
            return content;
        }

        public void setContent(List<CommunityContent> content) {
            this.content = content;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

    }
}


