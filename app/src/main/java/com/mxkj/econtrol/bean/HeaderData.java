package com.mxkj.econtrol.bean;

import com.mxkj.econtrol.base.BaseRequestEntity;
import com.mxkj.econtrol.utils.SHAHelper;

import java.util.UUID;

/**
 * Created by liangshan on 2017/3/21.
 *
 * @Destription: 请求头信息
 */

public class HeaderData extends BaseRequestEntity {
    private String app;//	接入接口应用
    private String rId;//设备唯一编号
    private String brand;//品牌及机型
    private String os;//操作系统
    private String osVersion;//操作系统版本号
    private String serial;//客户端請求系列号,生成規則：客戶端生成的UUID，轉成64進制
    private String time;//当前时间，日期格式:yyyyMMddHHmmss
    private String digest;//信息摘要,生成規則：客户端传输的信息采取hmacSHA256加密，再轉成64進制，作用是保证数据传输完整信息,信息摘要的使用key值，用戶沒有登陆采用deviceUUID,登陆以后采用token.
    private String language;//	国际化
    private HeaderToken headerToken;//有该的headerToken信息：1、该信息组合是RSA加密(token +userName )；2、当用户登陆后必须填写；

    public HeaderData() {
        this.serial = UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 方便构造请求头的基本信息
     *
     * @param data
     */
    public HeaderData(HeaderData data) {
        this.app = data.app;
        this.rId = data.rId;
        this.brand = data.brand;
        this.os = data.os;
        this.osVersion = data.osVersion;
        this.language=data.language;
        this.serial = UUID.randomUUID().toString().replace("-", "");
        this.headerToken = data.getHeaderToken();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getRId() {
        return rId;
    }

    public void setRId(String rid) {
        this.rId = rid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        //当用户未登录时加密的key为rid，当登录后key就为token
        String key = getHeaderToken().getToken() == null ? getRId() : getHeaderToken().getToken();
        this.digest = SHAHelper.hmacSHA256(key, digest);
    }

    public HeaderToken getHeaderToken() {
        return headerToken;
    }

    public void setHeaderToken(HeaderToken headerToken) {
        this.headerToken = headerToken;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public class HeaderToken {
        private String token;//登陆服务端的返回token值
        private String userName;//	用户名，用户登陆后必填值


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }


    }
}
