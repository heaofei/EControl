package com.mxkj.econtrol.bean.response;

import com.mxkj.econtrol.base.BaseResponse;

/**
 * Created by liangshan on 2017/3/21.
 *
 * @Destription:
 */

public class ResInitailEntity extends BaseResponse {

    private AppVersion appVersion;//app版本信息
    private AdVersion adVersion;//广告版本信息
    private ResUserLogin user;//自动登录时返回
    private House house;//默认进入的房子


    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public AppVersion getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersion appVersion) {
        this.appVersion = appVersion;
    }

    public AdVersion getAdVersion() {
        return adVersion;
    }

    public void setAdVersion(AdVersion adVersion) {
        this.adVersion = adVersion;
    }

    public ResUserLogin getUser() {
        return user;
    }

    public void setUser(ResUserLogin user) {
        this.user = user;
    }

    public static class AppVersion {
        private int appVersionCode;//最新版本号
        private String appVersionName;//	最新版本名称
        private String appVersionState;//	APP版本使用状态, 详细列表
        private String packageUrl;//apk下载路径
        private String appVersionContent;//apk更新说明

        public String getAppVersionContent() {
            return appVersionContent;
        }

        public void setAppVersionContent(String appVersionContent) {
            this.appVersionContent = appVersionContent;
        }

        public String getPackageUrl() {
            return packageUrl;
        }

        public void setPackageUrl(String packageUrl) {
            this.packageUrl = packageUrl;
        }


        public int getAppVersionCode() {
            return appVersionCode;
        }

        public void setAppVersionCode(int appVersionCode) {
            this.appVersionCode = appVersionCode;
        }

        public String getAppVersionName() {
            return appVersionName;
        }

        public void setAppVersionName(String appVersionName) {
            this.appVersionName = appVersionName;
        }

        public String getAppVersionState() {
            return appVersionState;
        }

        public void setAppVersionState(String appVersionState) {
            this.appVersionState = appVersionState;
        }
    }

    public static class AdVersion {
        private int adVersionCode;//最新版本号[APP保存该信息]
        public String adVersionName;//最新版本名称[APP]
        public String adVersionState;//广告版本使用状态, 狀態，0：未發布，1：已經發布，2：已經下線
        public String adPic;//开机广告路径
        public long adAnimationTime;//开机广告播放时间
        public long endDatetime;//广告结束时间
        public long startDatetime;//广告开启时间


        public long getEndDatetime() {
            return endDatetime;
        }

        public void setEndDatetime(long endDatetime) {
            this.endDatetime = endDatetime;
        }

        public long getStartDatetime() {
            return startDatetime;
        }

        public void setStartDatetime(long startDatetime) {
            this.startDatetime = startDatetime;
        }

        public int getAdVersionCode() {
            return adVersionCode;
        }

        public void setAdVersionCode(int adVersionCode) {
            this.adVersionCode = adVersionCode;
        }

        public String getAdVersionName() {
            return adVersionName;
        }

        public void setAdVersionName(String adVersionName) {
            this.adVersionName = adVersionName;
        }

        public String getAdVersionState() {
            return adVersionState;
        }

        public void setAdVersionState(String adVersionState) {
            this.adVersionState = adVersionState;
        }

        public String getAdPic() {
            return adPic;
        }

        public void setAdPic(String adPic) {
            this.adPic = adPic;
        }

        public long getAdAnimationTime() {
            return adAnimationTime;
        }

        public void setAdAnimationTime(long adAnimationTime) {
            this.adAnimationTime = adAnimationTime;
        }
    }

    public static class House {
        private String houseId;//
        private String houseName;//
        private String deviceNo;//
        private String buidingId;//
        private String buidingName;// 栋数
        private String estateId; // 小区id
        private String estateName;  // 小区名


        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getBuidingId() {
            return buidingId;
        }

        public void setBuidingId(String buidingId) {
            this.buidingId = buidingId;
        }

        public String getBuidingName() {
            return buidingName;
        }

        public void setBuidingName(String buidingName) {
            this.buidingName = buidingName;
        }

        public String getEstateId() {
            return estateId;
        }

        public void setEstateId(String estateId) {
            this.estateId = estateId;
        }

        public String getEstateName() {
            return estateName;
        }

        public void setEstateName(String estateName) {
            this.estateName = estateName;
        }
    }



}
