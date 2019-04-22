package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by liangshan on 2017/3/22.
 *
 * @Destription:登录返回实体类
 */

public class ResUserLogin extends BaseResponse {

    private String loginState;//登陆状态
    private String token;//令牌
    private String sex;//性别,(1男，0女)
    private String identityNO;//身份证号
    private String cardNO;//银行卡号
    private String email;//邮箱
    private String address;//地址
    private int isSign;//是否签约(1是，0否)
    private String born;//出生年月(YYYYMMDD)
    private String signName;//	签名
    private String headPicture="";//头像，图片的url
    private String userName; //用户名后来改成手机号
    private String realName; //真实姓名
    private String userId;//用户Id
    private String moon;//心情
    private String moonTime;//心情修改时间
    private String defaultHouseId  = "";//默认房子id
    private String defaultHouseName; // 默认房子名（房号）
    private String defaultBuidingId; // 默认栋数id
    private String defaultBuidingName; // 默认栋数名
    private String defaultEstateId; // 默认小区id
    private String defaultEstateName; // 默认小区名
    private boolean isOwner = false; // 是否业主，后面有房子的时候增加区分的字段，不是登陆返回的，在房子列表里面判断设置的


    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDefaultHouseName() {
        return defaultHouseName;
    }

    public void setDefaultHouseName(String defaultHouseName) {
        this.defaultHouseName = defaultHouseName;
    }

    public String getDefaultBuidingId() {
        return defaultBuidingId;
    }

    public void setDefaultBuidingId(String defaultBuidingId) {
        this.defaultBuidingId = defaultBuidingId;
    }

    public String getDefaultBuidingName() {
        return defaultBuidingName;
    }

    public void setDefaultBuidingName(String defaultBuidingName) {
        this.defaultBuidingName = defaultBuidingName;
    }

    public String getDefaultEstateId() {
        return defaultEstateId;
    }

    public void setDefaultEstateId(String defaultEstateId) {
        this.defaultEstateId = defaultEstateId;
    }

    public String getDefaultEstateName() {
        return defaultEstateName;
    }

    public void setDefaultEstateName(String defaultEstateName) {
        this.defaultEstateName = defaultEstateName;
    }

    public String getDefaultHouseId() {
        return defaultHouseId;
    }

    public void setDefaultHouseId(String defaultHouseId) {
        this.defaultHouseId = defaultHouseId;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    private String niceName=" ";//昵称

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getIdentityNO() {
        return identityNO;
    }

    public void setIdentityNO(String identityNO) {
        this.identityNO = identityNO;
    }

    public String getCardNO() {
        return cardNO;
    }

    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public String getMoonTime() {
        return moonTime.substring(0, 16);
    }

    public void setMoonTime(String moonTime) {
        this.moonTime = moonTime;
    }


}
