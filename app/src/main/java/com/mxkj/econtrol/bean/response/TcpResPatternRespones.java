package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseTCPCMDResponse;

/**
 * Created by ${  chenjun  } on 2017/11/7.
 */

public class TcpResPatternRespones extends BaseTCPCMDResponse {

    private ExtraData extraData;//后面加的房子id类

    public  ExtraData getExtraData() {
        return extraData;
    }

    public void setExtraData(ExtraData extraData) {
        this.extraData = extraData;
    }

    public   class  ExtraData{

        /**
         * pattern : {"house":{"deviceNo":"00001","houseNo":"1009","id":"ff8080815cc99db0015d4f3be3bf02d9"},"id":"40288b115f718a95015f718d8e440005","name":"40288b115f718a95015f718d8e440005","type":"40288b115f718a95015f718d8e440005","user":{"headPicture":"/img/user/head/2017/11/02/094037_764195.png","id":"402871815f3338e3015f335139cc0007","niceName":"测试admin","userName":"17620999268"}}
         */

        private PatternBean pattern;

        public PatternBean getPattern() {
            return pattern;
        }

        public void setPattern(PatternBean pattern) {
            this.pattern = pattern;
        }

        public  class PatternBean {
            /**
             * house : {"deviceNo":"00001","houseNo":"1009","id":"ff8080815cc99db0015d4f3be3bf02d9"}
             * id : 40288b115f718a95015f718d8e440005
             * name : 40288b115f718a95015f718d8e440005
             * type : 40288b115f718a95015f718d8e440005
             * user : {"headPicture":"/img/user/head/2017/11/02/094037_764195.png","id":"402871815f3338e3015f335139cc0007","niceName":"测试admin","userName":"17620999268"}
             */

            private HouseBean house;
            private String id;
            private String name;
            private String type;
            private UserBean user;

            public HouseBean getHouse() {
                return house;
            }

            public void setHouse(HouseBean house) {
                this.house = house;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public  class HouseBean {
                /**
                 * deviceNo : 00001
                 * houseNo : 1009
                 * id : ff8080815cc99db0015d4f3be3bf02d9
                 */

                private String deviceNo;
                private String houseNo;
                private String id;

                public String getDeviceNo() {
                    return deviceNo;
                }

                public void setDeviceNo(String deviceNo) {
                    this.deviceNo = deviceNo;
                }

                public String getHouseNo() {
                    return houseNo;
                }

                public void setHouseNo(String houseNo) {
                    this.houseNo = houseNo;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public  class UserBean {
                /**
                 * headPicture : /img/user/head/2017/11/02/094037_764195.png
                 * id : 402871815f3338e3015f335139cc0007
                 * niceName : 测试admin
                 * userName : 17620999268
                 */

                private String headPicture;
                private String id;
                private String niceName;
                private String userName;

                public String getHeadPicture() {
                    return headPicture;
                }

                public void setHeadPicture(String headPicture) {
                    this.headPicture = headPicture;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getNiceName() {
                    return niceName;
                }

                public void setNiceName(String niceName) {
                    this.niceName = niceName;
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


}
