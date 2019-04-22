package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by liangshan on 2017/4/19.
 *
 * @Description： 1.3.8.2.    获取评论信息响应类
 */

public class ResGetCommunityCommentsList extends BaseResponse {

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public class Page {
        List<CommentContent> content;
        private boolean firstPage;//是否第一页
        private boolean lastPage;//	是否最后一页

        public List<CommentContent> getContent() {
            return content;
        }

        public void setContent(List<CommentContent> content) {
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
