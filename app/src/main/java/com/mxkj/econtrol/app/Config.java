package com.mxkj.econtrol.app;

import android.os.Environment;
import android.text.TextUtils;

import com.mxkj.econtrol.utils.SharedPreferencesUtils;

/**
 * Created by liangshan on 2017/3/21.
 *
 * @Destription: app的一些配置信息
 */

public class Config {

    public static final String APP_NAME = "e-control";
    public static final String APP_FILE_CAMERA_PIC = Environment.getExternalStorageDirectory().getPath() + "/Fiswink/Camera/Pic";
    public static final String APP_FILE_CAMERA_Video = Environment.getExternalStorageDirectory().getPath() + "/Fiswink/Camera/Video";



    //    public static final String LANGUAGE = "zh_CN";
//    public static final String SERVER_IP = "119.29.229.18";// 正式，华发公用（现在已经停了，部署我们的上去）
//    public static final String SERVER_IP = "139.199.1.77";// 何绍亲测试
//    public static final String SERVER_IP = "www.fiswink.cn";

//    public static final String SERVER_IP = "119.29.234.173";// 后面购买的服务器（我们正式服务器）
//    public static final String SERVER_IP = "192.168.11.81";// 伟杰本地测试的服务器
//    public static final String TCP_SERVER = SERVER_IP; // 旧的tcp连接地址，跟之前的域名访问一样
//    public static final String TCP_SERVER_PORT = "80"; // 伟杰主机TCP的端口号
    //接口地址
//    public static String Base_URL = "http://" + SERVER_IP + "/smarthome/api/v1/";
    //图片前段地址
//    public static String BASE_PIC_URL = "http://" + SERVER_IP + "/smarthome_pic";
    //内网测试服务器


    /***2.0 新的配置*/

//    public static final String SERVER_IP = "api.smarthome.fiswink.com";// 更换域名访问
//    public static final String TCP_SERVER_PORT = "55555";// TCP连接的端口号
//    public static final String TCP_SERVER = "app.tcp.smarthome.fiswink.com";//  新的TCP连接地址
//    //接口地址
//    public static String Base_URL = "http://" + SERVER_IP + "/api/v1/"; // 更换域名访问
//    //图片前段地址
//    public static String BASE_PIC_URL = "http://file.smarthome.fiswink.com/pic";
    // 视频前段地址
//    public static String BASE_VIDEO_URL = "http://file.smarthome.fiswink.com/video";

    /**
     * 测试环境，外网映射地址
     */


    public static final String SERVER_IP = "dev.api.smarthome.ngrok.fiswink.com";// 更换域名访问
    public static final String TCP_SERVER_PORT = "48257";// TCP连接的端口号
    public static final String TCP_SERVER = "ngrok.fiswink.com";//  新的TCP连接地址
    //接口地址
    public static String Base_URL = "http://" + SERVER_IP + "/api/v1/"; // 更换域名访问
    //图片前段地址
    public static String BASE_PIC_URL = "http://dev.file.smarthome.ngrok.fiswink.com/pic";
    // 视频前段地址
//    public static String BASE_VIDEO_URL = "http://dev.file.smarthome.ngrok.fiswink.com/video";®®


    /****伟杰测试本地IP**/
/*    public static final String SERVER_IP = "192.168.11.145:8080";// 更换域名访问
//    public static final String SERVER_IP = "169.254.118.68:8080";// 更换域名访问
    public static final String TCP_SERVER_PORT = "55555";// TCP连接的端口号
    public static final String TCP_SERVER = "192.168.11.145";//  新的TCP连接地址
    //接口地址
    public static String Base_URL = "http://" + SERVER_IP + "/api/v1/"; // 更换域名访问
    //图片前段地址
    public static String BASE_PIC_URL = "http://192.168.11.145:9090/pic";
    */
    /**
     * 测试的外网
     */
  /*  public static final String SERVER_IP = "test.api.smarthome.ngrok.fiswink.com";// 更换域名访问
//    public static final String SERVER_IP = "169.254.118.68:8080";// 更换域名访问
    public static final String TCP_SERVER_PORT = "48256";// TCP连接的端口号
    public static final String TCP_SERVER = "ngrok.fiswink.com";//  新的TCP连接地址
    //接口地址
    public static String Base_URL = "http://" +SERVER_IP+  "/api/v1/"; // 更换域名访问
    //图片前段地址
    public static String BASE_PIC_URL = "http://test.file.smarthome.ngrok.fiswink.com"+"/pic";
*/



}
