package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseEntity;

import java.io.Serializable;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description： 智能部件实体类
 */

public class SmartPart extends BaseEntity {
    private String id;//智能部件Id
    private String name = "";//部件名称
    private String type;//1-灯，2-空调，3-吹风机 4-窗帘 5-门锁 6- 雾化玻璃 7-地暖
    private String typeName = "";//
    private String code;
    private String switchStatus = "0"; // 0关  1开
    private String permissions = "";
    private FunctionsBean functions; // 部件支持功能
    private Gateway gateway; // 网关
    private BrandBean brand; // 部件支持功能
    private SmartPartState state = new SmartPartState();//部件状态
    private String stateTimer="";//定时 0未定式 1定时
    private String rgb="";//灯光颜色（首页展现的状态）
    private String kt_t="";//空调温度（首页展现的状态）
    private String kt_f="";//空调风速（首页展现的状态）
    private String kt_m="";//空调模式（首页展现的状态）
    private int position;// 灯光设置用到的，记录在列表的位置


    public String getStateTimer() {
        return stateTimer;
    }

    public void setStateTimer(String stateTimer) {
        this.stateTimer = stateTimer;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getKt_t() {
        return kt_t;
    }

    public void setKt_t(String kt_t) {
        this.kt_t = kt_t;
    }

    public String getKt_f() {
        return kt_f;
    }

    public void setKt_f(String kt_f) {
        this.kt_f = kt_f;
    }

    public String getKt_m() {
        return kt_m;
    }

    public void setKt_m(String kt_m) {
        this.kt_m = kt_m;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public SmartPartState getState() {
        return state;
    }

    public void setState(SmartPartState state) {
        this.state = state;
    }

    public FunctionsBean getFunctions() {
        return functions;
    }

    public void setFunctions(FunctionsBean functions) {
        this.functions = functions;
    }

    public BrandBean getBrand() {
        return brand;
    }

    public void setBrand(BrandBean brand) {
        this.brand = brand;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public class FunctionsBean implements Serializable {
        // 都是 1 是支持，0 是不支持
        private int d;//亮度调节
        private int a; // 开关
        private int t; // 温度调节
        private int rgb; // rgb颜色值

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getRgb() {
            return rgb;
        }

        public void setRgb(int rgb) {
            this.rgb = rgb;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
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

    public class BrandBean implements Serializable {
        private String id;//
        private String name;//品牌名字
        private String code;// 02青松 01 耶鲁

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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
