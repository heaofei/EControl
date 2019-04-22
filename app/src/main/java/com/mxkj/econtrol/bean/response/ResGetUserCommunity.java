package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/6/13.
 *
 * @Description：
 */

public class ResGetUserCommunity extends BaseResponse {
    private ResGetCommunityList.Page page;

    public ResGetCommunityList.Page getPage() {
        return page;
    }

    public void setPage(ResGetCommunityList.Page page) {
        this.page = page;
    }

    public class Page {
        List<CommunityContent> content;
        private boolean firstPage;//是否第一页
        private boolean lastPage;//	是否最后一页


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
