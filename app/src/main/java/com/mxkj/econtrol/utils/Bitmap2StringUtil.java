package com.mxkj.econtrol.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangshan on 2017/3/29.
 *
 * @Description: 将图片转为字符串
 */

public class Bitmap2StringUtil {
    public static String bitmapToString(String imgFilePath) {
        if (imgFilePath == null || imgFilePath == "") {
            return "";
        }
        File file = new File(imgFilePath);
        if (!file.exists()) {
            return "";
        }
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = getByte(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageString = Base64.encodeToString(data, Base64.DEFAULT);
        return imageString;
    }

    public static byte[] getByte(InputStream in) {
        if (in == null) {
            return null;
        }
        int sumSize = 0;
        List<byte[]> totalBytes = new ArrayList<byte[]>();
        byte[] buffer = new byte[1024];
        int length = -1;
        try {
            while ((length = in.read(buffer)) != -1) {
                sumSize += length;
                byte[] tmp = new byte[length];
                System.arraycopy(buffer, 0, tmp, 0, length);
                totalBytes.add(tmp);
            }
            byte[] data = new byte[sumSize];
            int start = 0;
            for (byte[] tmp : totalBytes) {
                System.arraycopy(tmp, 0, data, start, tmp.length);
                start += tmp.length;
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bitmapToString(Bitmap mBitmap) {
        String imageString = "";
        if (mBitmap != null) {
            Matrix matrix = new Matrix();
            int mWidth = mBitmap.getWidth();
            int mHeight = mBitmap.getHeight();
            float scaleWidth = (float) 150 / mWidth;
            float scaleHeight = (float) 150 / mHeight;
            LogUtil.i("scale:" + scaleWidth + "++++++++++++" + scaleHeight);
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap newBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            byte[] bytes = out.toByteArray();
            imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
            System.out.println(imageString);
        }
        return imageString;
    }
}


