package com.mxkj.econtrol.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.request.ReqInitailEntity;
import com.mxkj.econtrol.bean.response.ResInitailEntity;
import com.mxkj.econtrol.contract.InitailContract;
import com.mxkj.econtrol.contract.LoginContract;
import com.mxkj.econtrol.model.InitalModel;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.net.TcpClient;
import com.mxkj.econtrol.presenter.InitailPresenter;
import com.mxkj.econtrol.ui.activity.GuideActivity;
import com.mxkj.econtrol.ui.activity.LoginRegistActivity;
import com.mxkj.econtrol.utils.AppUpdateHelper;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.DeviceUtil;
import com.mxkj.econtrol.utils.LocationUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 欢迎页
 * Created by liangshan on 2017/4/1.
 *
 * @Description:
 */

public class SplashActivity extends BaseActivity implements InitailContract.View {
    private TextView mTvSkip;
    private InitailContract.Presenter mPresenter;
    private CountDownTimer timer;
    private GifImageView imvAd;
    private ImageView imv;
    private ResInitailEntity mInitailEntity;
    //默认进入的房子
    private ResInitailEntity.House mHouse;
    //是否播放广告
    private boolean isPlayAd;
    private static Handler mHandler;
    private long mGitTime = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        LocationUtil.updateLocation(this);
    }

    @Override
    public void setStatusBar(boolean isShow, int drawable) {
        super.setStatusBar(false, drawable);
    }

    @Override
    protected void initView() {
        mTvSkip = findView(R.id.tv_skip);
        imvAd = findView(R.id.imv_ad);
        imv = findView(R.id.imv);
//        init();
    }

    @Override
    protected void initData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                init();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(mGitTime); // 动画的时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1000);
            }
        }).start();
    }

    public void init() {

//        mTvSkip.setVisibility(View.VISIBLE);

        mPresenter = new InitailPresenter(this, new InitalModel());
        ReqInitailEntity reqInitailEntity = new ReqInitailEntity();
        int adVersionCode = (int) SharedPreferencesUtils.getParam(this, "adVersionCode", 0);
        String adVersionName = (String) SharedPreferencesUtils.getParam(this, "adVersionName", "v1.0");
        int appVersionCode = DeviceUtil.getVersionCode(this);
        String appVersionName = DeviceUtil.getVersionName(this);
        reqInitailEntity.setAdVersionCode(adVersionCode);
        reqInitailEntity.setAdVersionName(adVersionName);
        reqInitailEntity.setAppVersionCode(appVersionCode);
        reqInitailEntity.setAppVersionName(appVersionName);
        reqInitailEntity.setX(APP.latitude);
        reqInitailEntity.setY(APP.longitude);

        if (!(Boolean) SharedPreferencesUtils.getParam(this, "notFirst", false)) {// 是第一次使用app
            startToActivity(GuideActivity.class);
            finish();
        } else {
            mPresenter.initail(reqInitailEntity);
        }


        long startDatetime = (Long) SharedPreferencesUtils.getParam(this, "startDatetime", (long) 0);
        long endDatetime = (Long) SharedPreferencesUtils.getParam(this, "endDatetime", (long) 0);
        if (DateTimeUtil.isEffectiveDate(startDatetime, endDatetime)) {
            checkAdtime(); // 播放广告
        }
    }

    private void checkAdtime() {
        //上一次播放广告时间
        long lastPalyAdTime = (long) SharedPreferencesUtils.getParam(this, "lastPalyAdTime", 0l);
        String adPicUrl = (String) SharedPreferencesUtils.getParam(this, "adPicUrl", "");

        //有缓存广告并距离上一次播放时间大于一天才播放广告
        if (System.currentTimeMillis() - lastPalyAdTime > 0 * 60 * 60 * 1000 && !TextUtils.isEmpty(adPicUrl)) {
            isPlayAd = true;
            mTvSkip.setVisibility(View.VISIBLE);
            SharedPreferencesUtils.setParam(this, "lastPalyAdTime", System.currentTimeMillis());
            long adAnimationTime = (long) SharedPreferencesUtils.getParam(this, "adAnimationTime", (long) 3);
            timer = new CountDownTimer(adAnimationTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTvSkip.setText("跳过（" + millisUntilFinished / 1000 + " )");
                }

                @Override
                public void onFinish() {
                    startToMain();
                }
            };
            showAd(); //播放广告
        } else {
            mTvSkip.setVisibility(View.GONE);
            isPlayAd = false;
        }
    }

    //播放广告
    private void showAd() {
        timer.start();
        String adPicUrl = (String) SharedPreferencesUtils.getParam(this, "adPicUrl", "");


        if (TextUtils.isEmpty(adPicUrl)) {
            startToMain();
        }

        try {
            String fileHouzhui = "";
            if (adPicUrl.length() > 4) {
                fileHouzhui = adPicUrl.substring(adPicUrl.length() - 4, adPicUrl.length());
            }
            if (fileHouzhui.equals(".gif")) {
                imvAd.setVisibility(View.VISIBLE);
                imv.setVisibility(View.GONE);
                GifDrawable gifDrawable = new GifDrawable(adPicUrl);
                imvAd.setImageDrawable(gifDrawable);
            } else {
                imvAd.setVisibility(View.GONE);
                imv.setVisibility(View.VISIBLE);
            /*    FileInputStream f = new FileInputStream(adPicUrl);
                Bitmap bm = null;
                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 8;//图片的长宽都是原来的1/8
                BufferedInputStream bis = new BufferedInputStream(f);
                bm = BitmapFactory.decodeStream(bis, null, options);
             */
                //加载内存卡里面的图片
                File file = new File(adPicUrl);
                Bitmap bm = BitmapFactory.decodeFile(adPicUrl);
                imv.setImageBitmap(bm);

//                scaleImage(this, imv, bm);
//                scaleImage(this, imv, R.drawable.ic_qidong);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            startToMain();
        } catch (IOException e) {
            e.printStackTrace();
            startToMain();
        }
    }

//    private void scaleImage(SplashActivity splashActivity, final View view, Bitmap bm) {
    private void scaleImage(SplashActivity splashActivity, final View view, int drawableResId) {
// 获取屏幕的高宽
        Point outSize = new Point();
        splashActivity.getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);

        // 解析将要被处理的图片
        Bitmap resourceBitmap = BitmapFactory.decodeResource(splashActivity.getResources(), drawableResId);
//        Bitmap resourceBitmap = bm;

        if (resourceBitmap == null) {
            return;
        }

        // 开始对图片进行拉伸或者缩放

        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize.x * 1.0f / resourceBitmap.getWidth());

        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize.x, bitmapScaledHeight, false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;


                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = view.getMeasuredHeight();


                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;


                // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了

                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(), scaledBitmap.getHeight() - offset * 2);


                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                    scaledBitmap.recycle();
                    System.gc();
                }


                // 设置图片显示
                view.setBackgroundDrawable(new BitmapDrawable(getResources(), finallyBitmap));
                return true;
            }
        });

    }

    @Override
    protected void initListener() {
        mTvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (mTvSkip == v) {
            startToMain();
        }
    }

    @Override
    public void initailFail(String msg) {
        showToast("初始化失败，请检查网络后重新登录!");
//        startToActivity(FirstPageActivity.class);
        startToActivity(LoginRegistActivity.class);
//                startToActivity(MainNewActivity.class);
//        startToActivity(MainNewActivity.class);
        finish();
    }

    @Override
    public void initailSuccess(ResInitailEntity resInitailEntity) {
        Gson gson = new Gson();
        String content = gson.toJson(resInitailEntity);
        LogUtil.i("==返回数据==初始化消息=：" + content);
        mInitailEntity = resInitailEntity;
        mHouse = resInitailEntity.getHouse();
        // 服务器版本号信息保存
        if (resInitailEntity.getAppVersion() != null) {
            APP.appVersion = resInitailEntity.getAppVersion();
        }
//        SharedPreferencesUtils.setParam(this, "appVersionCode", resInitailEntity.getAppVersion().getAppVersionCode());
        if (resInitailEntity.getUser() != null) {
            APP.user = resInitailEntity.getUser();
            APP.headerData.getHeaderToken().setToken(APP.user.getToken());
            APP.headerData.getHeaderToken().setUserName(APP.user.getUserName());
            SharedPreferencesUtils.setParam(this, "token", APP.user.getToken());
            SharedPreferencesUtils.setParam(this, "userName", APP.user.getUserName());
            String mHouseId = resInitailEntity.getUser().getDefaultHouseId();
            SharedPreferencesUtils.setParam(this, "houseId", mHouseId);// 初始化的时候，把默认房子id肤赋值给，当前用户房子id
            APP.isLogin = true;
            TcpClient.getInstacne().init();
        } else {
            APP.user = null;
            APP.isLogin = false;
        }
        // 广告
        ResInitailEntity.AdVersion adVersion = resInitailEntity.getAdVersion();
        if (adVersion.getAdVersionState().equals("0")) {

        }
        int curAdVersionCode = (int) SharedPreferencesUtils.getParam(this, "adVersionCode", 0);
        if (TextUtils.isEmpty(adVersion.getAdPic())) {
            SharedPreferencesUtils.setParam(this, "adPicUrl", "");  // 后台没有广告返回时就设置为空，下载就不加载广告
        }
        if (adVersion.getAdVersionCode() != curAdVersionCode) {  // 根本地的adVersionCode 与 服务器的对比
            //当有新版广告时下载最新广告图片
            downloadAd(Config.BASE_PIC_URL + adVersion.getAdPic());
        }
        if (!isPlayAd && (Boolean) SharedPreferencesUtils.getParam(this, "notFirst", false)) {// 不播放广告并且不是第一次使用app
            startToMain();
        }
    }


    @Override
    public void setPresenter(InitailContract.Presenter presenter) {

    }

    private void downloadAd(final String picUrl) {
//        picUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510147539426&di=340e5fe082281ae896c602a1c560f275&imgtype=0&src=http%3A%2F%2Fimg1.imeee.cn%2Fallimg%2Fc160822%2F14GW94E30410-23007.gif";
        RetrofitUtil.getInstance().getmApiService().downloadPicFromNet(picUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        InputStream inputStream = responseBody.byteStream();
                        if (picUrl.length() > 4) {
                            String fileHouzhui = picUrl.substring(picUrl.length() - 4, picUrl.length());
                            saveAd(inputStream, fileHouzhui);
                        }
                    }
                });
    }

    /**
     * @Desc: 保存广告图片
     * @author:liangshan
     */
    private void saveAd(InputStream input, String fileHouzhui) {
        File filesDir = getExternalCacheDir();
        File newAd = new File(filesDir, DateTimeUtil.getSecond() + fileHouzhui);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newAd);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.flush();
            LogUtil.i("保存成功---------------------" + newAd.getAbsolutePath());
            String oldAd = (String) SharedPreferencesUtils.getParam(this, "adPicUrl", "");
            File file = new File(oldAd);
            //删除旧的广告
            if (file.exists()) {
                file.delete();
            }
            SharedPreferencesUtils.setParam(this, "adVersionCode", mInitailEntity.getAdVersion().getAdVersionCode());
            SharedPreferencesUtils.setParam(this, "adVersionName", mInitailEntity.getAdVersion().getAdVersionName());
            SharedPreferencesUtils.setParam(this, "adAnimationTime", mInitailEntity.getAdVersion().getAdAnimationTime());
            SharedPreferencesUtils.setParam(this, "startDatetime", mInitailEntity.getAdVersion().getStartDatetime());
            SharedPreferencesUtils.setParam(this, "endDatetime", mInitailEntity.getAdVersion().getEndDatetime());
            SharedPreferencesUtils.setParam(this, "adPicUrl", newAd.getAbsolutePath());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * @Desc: 跳转到主页
     * @author:liangshan
     */
    private void startToMain() {
        //设置非透明主题
        setTheme(R.style.AppTheme);
        if (mHouse != null && APP.user != null) {
            //有默认的房子，直接跳到该房子
//            Intent intent = new Intent(this, HouseMainActivity.class);
            Intent intent = new Intent(this, MainNewActivity.class);
            startToActivity(intent);
            TcpClient.getInstacne().init();
        } else if (APP.user != null) {
            //已登录
//            startToActivity(MainActivity.class);
            startToActivity(MainNewActivity.class);
        } else {
//            Intent intent = new Intent(SplashActivity.this, FirstPageActivity.class);
//            startToActivity(LoginRegistActivity.class);
            startToActivity(MainNewActivity.class);
        }

//        startToActivity(MainNewActivity.class);

        if (timer != null) {
            timer.cancel();
        }
        finish();
    }
}

