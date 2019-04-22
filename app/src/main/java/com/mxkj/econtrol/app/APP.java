package com.mxkj.econtrol.app;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RemoteViews;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.bean.HeaderData;
import com.mxkj.econtrol.bean.response.ResInitailEntity;
import com.mxkj.econtrol.bean.response.ResUserLogin;
import com.mxkj.econtrol.bean.tcpcmd.HeartBeatCMD;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.DeviceUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mx1 on 2017/3/21.
 */

public class APP extends Application {

    public static final String TAG = "Application";
    public static HeaderData headerData;
    private static Context context;
    public static String publicKey;
    public static String language;
    public static ResUserLogin user; // 登陆或初始化信息保存
    public static boolean isLogin = false ; // 是否登录用户
    public static boolean isExperience = false ; // 是否处于体验状态
    public static ResInitailEntity.AppVersion appVersion; // 服务器版本号更新信息保存
    public static String DeviceId;//中控设备Id，选了房子才会有
    public static double longitude = 0.00;//经度
    public static double latitude = 0.00;//纬度
    public static List<Activity> activities;

    public static int screenWidth;//屏幕宽度
    public static int screenHeight; //屏幕高度


    public static Context getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initHeaderData();
//        TcpClient.getInstacne().init();
        APP.activities = new ArrayList<>();
        getScreen();//获取屏幕宽高值
        initBugly();
        applicationLifeChange();// APP前后台切换监听
        /*AppID：       wxe5541af7ce9ea42e
        AppSecret：  73346380354cdcbf300c2705c36c14f8*/
        UMConfigure.init(this,"5b344ec8a40fa36e0f000012","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin("wxe5541af7ce9ea42e", "abcfeeeccdb62b5067c338a9c9c76a9d");
        UMConfigure.setLogEnabled(false);

        // 保存Version，有大版本更新时，把引导页也显示，
        String appVersionName = DeviceUtil.getVersionName(this);
        String SPappVersionName = (String)SharedPreferencesUtils.getParam(this, "VersionName", "");
        if (!appVersionName.equals(SPappVersionName)) {
            SharedPreferencesUtils.setParam(this, "VersionName", appVersionName);
            SharedPreferencesUtils.setParam(this, "notFirst", false);  // 要是不显示引导页，把这个注释
        }


        UMConfigure.init(this, "5b344ec8a40fa36e0f000012", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "3437a1759fa32402f9c8f60bc3bcda81");
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG,"注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                                R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon,
                                getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
                                getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };

        mPushAgent.setMessageHandler(messageHandler);
        mPushAgent.setDisplayNotificationNumber(9);
    }



    /**
     * 初始化请求接口时的请求头数据
     */
    private void initHeaderData() {
        headerData = new HeaderData();
        headerData.setApp(Config.APP_NAME);
        language = Locale.getDefault().toString();
        if (language.equals("zh_CN")) {
            language = "zh_CN";
        } else {
            language = "en_US";
        }
        headerData.setLanguage(language);
        headerData.setOs("Android");
//        headerData.setBrand(DeviceUtil.getDeviceID(this));
        headerData.setBrand(Build.BRAND);
        headerData.setOsVersion(Build.VERSION.RELEASE + "");
//        headerData.setRId(DeviceUtil.getDeviceID(this));
        headerData.setRId(Build.ID);
        HeaderData.HeaderToken headerToken = APP.headerData.new HeaderToken();
        String token = (String) SharedPreferencesUtils.getParam(this, "token", "");
        String userName = (String) SharedPreferencesUtils.getParam(this, "userName", "");
        //如果以前登录过，设置这些数据后会自动登录
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(userName)) {
            headerToken.setUserName(userName);
            headerToken.setToken(token);
        }
        headerData.setHeaderToken(headerToken);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void getScreen() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        if (screenWidth == 0 || screenHeight == 0) {
            screenWidth = 720;
            screenHeight = 1080;
        }
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "618ec03802", true); // Bugly注册时申请的APPID
    }


    public int count = 0;
    private void applicationLifeChange() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (count == 0) {
                    LogUtil.i("============APP遁入后台=========");
                }
            }
            @Override
            public void onActivityStarted(Activity activity) {
                if (count == 0) {
                    LogUtil.i("============APP遁入前台=========");
                    if (TcpClient.isConnecting) {
                        //在APP遁入前台里面， 发一个心跳包，保证tcp还活着
                        HeartBeatCMD heartBeatCMD = new HeartBeatCMD();
                        heartBeatCMD.setTime(DateTimeUtil.getSecond());
                        TcpClient.getInstacne().sendCMD(heartBeatCMD);
                    }
                }
                count++;
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            @Override
            public void onActivityResumed(Activity activity) { }
            @Override
            public void onActivityPaused(Activity activity) { }
            @Override
            public void onActivityDestroyed(Activity activity) { }
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {  }
        });
    }



}
