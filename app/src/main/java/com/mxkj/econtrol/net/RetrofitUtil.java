package com.mxkj.econtrol.net;

import android.text.TextUtils;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.bean.HeaderData;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liangshan on 2017/3/21.
 *
 * @Destription:
 */


public class RetrofitUtil {

    public static final int DEFAULT_TIMEOUT = 15;

    private Retrofit mRetrofit;
    private APIService mApiService;

    private static RetrofitUtil mInstance;

    /**
     * 私有构造方法
     */
    private RetrofitUtil() {

        mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(Config.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        mApiService = mRetrofit.create(APIService.class);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                RequestBody body = request.body();
                //获取请求data的内容加密作为摘要
                if (body instanceof FormBody) {
                    FormBody formBody = (FormBody) body;
                    for (int i = 0; i < formBody.size(); i++) {
                        LogUtil.i("==请求数据==：" + formBody.name(i) + ":" + formBody.value(i));
                        if (formBody.name(i).equals("data")) {
                            String data = formBody.value(i);
                            HeaderData headerData = new HeaderData(APP.headerData);
                            headerData.setDigest(data);
                            headerData.setTime(DateTimeUtil.getSecond());
                            LogUtil.i("请求头："+headerData.toJsonStr());
                            request = request.newBuilder()
                                    .addHeader("headerData", headerData.toJsonStr())
                                    .build();
                        }
                    }
                }
                long start = System.nanoTime();//请求发起的时间

                Response response = chain.proceed(request);
                long end = System.nanoTime();//收到响应的时间

                //response.body.string()只能使用一次所以不能直接用
                ResponseBody responseBody = response.peekBody(1024 * 1024);
                String bodyString = responseBody.string();
                LogUtil.i("==返回数据==：" + bodyString + "用时" + (end - start) / 1e6d);
//                String content=responseBody.string();  // responseBody.string() 用来一次之后就不能再读取tostring了
//                String content2=responseBody.toString();
                return response;
            }
        });
        return builder.build();
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null)
                    mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    /**
     * 提供APIService
     */
    public APIService getmApiService() {
        return mApiService;
    }

}

