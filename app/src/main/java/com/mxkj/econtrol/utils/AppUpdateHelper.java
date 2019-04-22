package com.mxkj.econtrol.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Toast;

import com.mxkj.econtrol.bean.response.ResInitailEntity;
import com.mxkj.econtrol.widget.TipDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liangshan on 2017/4/27.
 *
 * @Description：
 */

public class AppUpdateHelper {

    /**
     * @Title: checkAppVersion @Description: 检查程序的新版本 @return void @throws
     */
    public static boolean checkAppVersion(final Context context, final ResInitailEntity.AppVersion version, boolean isShowToast, final boolean hasPermission) {
        int curVersion = DeviceUtil.getVersionCode(context);
        if (curVersion == version.getAppVersionCode()) {
            if (isShowToast) {
                Toast.makeText(context, "当前已是最新版本", Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        final MyDialogUtil dialogUtil = new MyDialogUtil(context) {
            @Override
            public void confirm() {
                if (hasPermission){
                downLoadApk(context, version.getPackageUrl());
                }else {
                    Toast.makeText(context,"请到，设置-应用管理-权限设置中，开启存储权限",Toast.LENGTH_LONG).show();
                }
            }
        };
        dialogUtil.showUpdateTipDialog(version.getAppVersionName(),version.getAppVersionContent(),version.getAppVersionState());

        return true;
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            pd.setMax(100);
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "upload.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress((int) ((double) total * 100 / conn.getContentLength()));
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

	/*
     *
	 * 弹出对话框通知用户更新程序
	 *
	 * 弹出对话框的步骤： 1.创建alertDialog的builder. 2.要给builder设置属性, 对话框的内容,样式,按钮
	 * 3.通过builder 创建一个对话框 4.对话框show()出来
	 */

    /*
     * 从服务器中下载APK
     */
    protected static void downLoadApk(final Context context, final String url) {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(url, pd);
                    sleep(3000);
                    installApk(context, file);
                    pd.dismiss(); // 结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        pd.setCancelable(false);
    }


    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.mxkj.econtrol.fileprovider.fileprovider", file);
            //Granting Temporary Permissions to a URI
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }



}