package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/10/30.
 * 获取系统通知接收实体类
 */

public class ResGetSysAnnouncement extends BaseResponse {


    /**
     * page : {"content":[{"createTime":1509356048000,"descriptions":"描述：02","publishTime":1509356048000,"title":"标题：02","userId":"1","userName":"作者1","updateTime":1509355358000},{"createTime":1509355894000,"descriptions":"描述：02","publishTime":1509355894000,"title":"标题：02","userId":"1","userName":"作者1"},{"createTime":1509355357000,"descriptions":"描述：02","publishTime":1509355357000,"title":"标题：02","userId":"1","userName":"作者1"},{"createTime":1509355357000,"descriptions":"描述：01","title":"标题：01","userId":"2","userName":"作者2"},{"createTime":1509355358000,"descriptions":"描述：03","title":"标题：03","updateTime":1509355358000,"userId":"2","userName":"作者2"},{"createTime":1509355894000,"descriptions":"描述：01","title":"标题：01","userId":"2","userName":"作者2"},{"createTime":1509355894000,"descriptions":"描述：03","title":"标题：03","updateTime":1509355894000,"userId":"2","userName":"作者2"},{"createTime":1509356048000,"descriptions":"描述：01","title":"标题：01","userId":"2","userName":"作者2"},{"createTime":1509356049000,"descriptions":"描述：03","title":"标题：03","updateTime":1509356049000,"userId":"2","userName":"作者2"},{"createTime":1509356049000,"descriptions":"描述：05","title":"标题：05","userId":"2","userName":"作者2"}],"firstPage":true,"lastPage":true,"number":0,"numberOfElements":10,"size":20,"totalElements":10,"totalPages":1}
     */

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * content : [{"createTime":1509356048000,"descriptions":"描述：02","publishTime":1509356048000,"title":"标题：02","userId":"1","userName":"作者1"},{"createTime":1509355894000,"descriptions":"描述：02","publishTime":1509355894000,"title":"标题：02","userId":"1","userName":"作者1"},{"createTime":1509355357000,"descriptions":"描述：02","publishTime":1509355357000,"title":"标题：02","userId":"1","userName":"作者1"},{"createTime":1509355357000,"descriptions":"描述：01","title":"标题：01","userId":"2","userName":"作者2"},{"createTime":1509355358000,"descriptions":"描述：03","title":"标题：03","updateTime":1509355358000,"userId":"2","userName":"作者2"},{"createTime":1509355894000,"descriptions":"描述：01","title":"标题：01","userId":"2","userName":"作者2"},{"createTime":1509355894000,"descriptions":"描述：03","title":"标题：03","updateTime":1509355894000,"userId":"2","userName":"作者2"},{"createTime":1509356048000,"descriptions":"描述：01","title":"标题：01","userId":"2","userName":"作者2"},{"createTime":1509356049000,"descriptions":"描述：03","title":"标题：03","updateTime":1509356049000,"userId":"2","userName":"作者2"},{"createTime":1509356049000,"descriptions":"描述：05","title":"标题：05","userId":"2","userName":"作者2"}]
         * firstPage : true
         * lastPage : true
         * number : 0
         * numberOfElements : 10
         * size : 20
         * totalElements : 10
         * totalPages : 1
         */

        private boolean firstPage;
        private boolean lastPage;
        private int number;
        private int numberOfElements;
        private int size;
        private int totalElements;
        private int totalPages;
        private List<ContentBean> content;

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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * createTime : 1509356048000
             * descriptions : 描述：02
             * publishTime : 1509356048000
             * title : 标题：02
             * userId : 1
             * userName : 作者1
             * updateTime : 1509355358000
             */

            private String createTime;
            private String descriptions;
            private String publishTime;
            private String title;
            private String userId;
            private String id;
            private String userName;
            private String updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDescriptions() {
                return descriptions;
            }

            public void setDescriptions(String descriptions) {
                this.descriptions = descriptions;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
