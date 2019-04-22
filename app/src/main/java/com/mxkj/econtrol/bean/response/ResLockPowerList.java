package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

import java.util.List;

/**
 * Created by chan on 2018/1/19.
 *
 * @Description: 门锁权限列表
 */

public class ResLockPowerList extends BaseResponse {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * createTime : 2018-01-22 15:51
         * permissions :
         * user : {"headPicture":"/img/user/head/2018/01/17/173851_769565.png","niceName":"何伟杰","sex":"1","userName":"1"}
         */

        private String createTime;
        private String permissions;
        private UserBean user;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPermissions() {
            return permissions;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * headPicture : /img/user/head/2018/01/17/173851_769565.png
             * niceName : 何伟杰
             * sex : 1
             * userName : 1
             */

            private String headPicture;
            private String niceName;
            private String sex;
            private String id;
            private String userName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHeadPicture() {
                return headPicture;
            }

            public void setHeadPicture(String headPicture) {
                this.headPicture = headPicture;
            }

            public String getNiceName() {
                return niceName;
            }

            public void setNiceName(String niceName) {
                this.niceName = niceName;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}

