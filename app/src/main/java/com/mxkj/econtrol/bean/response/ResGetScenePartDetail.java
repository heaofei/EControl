package com.mxkj.econtrol.bean.response;

import com.google.gson.annotations.SerializedName;
import com.mxkj.econtrol.base.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/11/2.
 *
 * @Description: 获取部件详情接口响应类
 */

public class ResGetScenePartDetail extends BaseResponse {


    /**
     * name : 门锁1
     * part : {"code":"LK001","id":"40288b11610d5cab01611bb9172a000b","name":"门锁1","state":{"lockState":{"permissions":"00,01","gesturePasswordStatus":"uninited","applyGesturePasswordStatus":"waiting"}},"type":"LK"}
     * partOperators : [{"code":"a-01","id":"40288b1161262dec016126c1719e0006","name":"定时打开","partId":"40288b11610d5cab01611bb9172a000b","partType":"5","status":"0"},{"code":"a-00","id":"40288b1161262dec016126c171bf0007","name":"定时关闭","partId":"40288b11610d5cab01611bb9172a000b","partType":"5","status":"0"}]
     * timerState : 0
     */

    private String name;
    private PartBean part;
    private String timerState;
    private List<PartOperatorsBean> partOperators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PartBean getPart() {
        return part;
    }

    public void setPart(PartBean part) {
        this.part = part;
    }

    public String getTimerState() {
        return timerState;
    }

    public void setTimerState(String timerState) {
        this.timerState = timerState;
    }

    public List<PartOperatorsBean> getPartOperators() {
        return partOperators;
    }

    public void setPartOperators(List<PartOperatorsBean> partOperators) {
        this.partOperators = partOperators;
    }

    public static class PartBean {
        /**
         * code : LK001
         * id : 40288b11610d5cab01611bb9172a000b
         * name : 门锁1
         * state : {"lockState":{"permissions":"00,01","gesturePasswordStatus":"uninited","applyGesturePasswordStatus":"waiting"}}
         * type : LK
         */

        private String code;
        private String id;
        private String name;
        @SerializedName("state")
        private StateBean state;
        private String type;
        private String permissions = "";
        private String sn = "";
        private String ieeeAddress = "";// 门锁短地址
        private String shortAddress = "";// 门锁短地址
        private String endPoint = ""; // 门锁端点
        private int power = 1; // 门锁电量状态 返回 1正常 0低电压
        private Gateway gateway; // 网关
        private Functions functions; // 网关


        public Functions getFunctions() {
            return functions;
        }

        public void setFunctions(Functions functions) {
            this.functions = functions;
        }

        public int getPower() {
            return power;
        }
        public void setPower(int power) {
            this.power = power;
        }
        public String getIeeeAddress() {
            return ieeeAddress;
        }

        public void setIeeeAddress(String ieeeAddress) {
            this.ieeeAddress = ieeeAddress;
        }

        public String getShortAddress() {
            return shortAddress;
        }

        public void setShortAddress(String shortAddress) {
            this.shortAddress = shortAddress;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public Gateway getGateway() {
            return gateway;
        }

        public void setGateway(Gateway gateway) {
            this.gateway = gateway;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
        public String getPermissions() {
            return permissions;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }

        public StateBean getState() {
            return state;
        }

        public void setState(StateBean stateX) {
            this.state = stateX;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class StateBean {
            /**
             * lockState : {"permissions":"00,01","gesturePasswordStatus":"uninited","applyGesturePasswordStatus":"waiting"}
             */
            private String d = "";//
            private String rgb = "";//
            private LockStateBean lockState;


            public String getD() {
                return d;
            }

            public void setD(String d) {
                this.d = d;
            }

            public String getRgb() {
                return rgb;
            }

            public void setRgb(String rgb) {
                this.rgb = rgb;
            }

            public LockStateBean getLockState() {
                return lockState;
            }

            public void setLockState(LockStateBean lockState) {
                this.lockState = lockState;
            }

            public static class LockStateBean {
                /**
                 * permissions : 00,01
                 * gesturePasswordStatus : uninited
                 * applyGesturePasswordStatus : waiting
                 */


                private String gesturePasswordStatus = "";
                private String applyGesturePasswordStatus = "";

                public String getGesturePasswordStatus() {
                    return gesturePasswordStatus;
                }

                public void setGesturePasswordStatus(String gesturePasswordStatus) {
                    this.gesturePasswordStatus = gesturePasswordStatus;
                }

                public String getApplyGesturePasswordStatus() {
                    return applyGesturePasswordStatus;
                }

                public void setApplyGesturePasswordStatus(String applyGesturePasswordStatus) {
                    this.applyGesturePasswordStatus = applyGesturePasswordStatus;
                }
            }
        }
    }

    public static class PartOperatorsBean {
        /**
         * code : a-01
         * id : 40288b1161262dec016126c1719e0006
         * name : 定时打开
         * partId : 40288b11610d5cab01611bb9172a000b
         * partType : 5
         * status : 0
         */

        private String code;
        private String id;
        private String name;
        private String partId;
        private String partType;
        private String status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getPartId() {
            return partId;
        }

        public void setPartId(String partId) {
            this.partId = partId;
        }

        public String getPartType() {
            return partType;
        }

        public void setPartType(String partType) {
            this.partType = partType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class Gateway implements Serializable {
        private String id;//
        private String snid;//
        private String name;//

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSnid() {
            return snid;
        }

        public void setSnid(String snid) {
            this.snid = snid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Functions implements Serializable{
        private int tp;
        private int a;

        public int getTp() {
            return tp;
        }

        public void setTp(int tp) {
            this.tp = tp;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }
}
