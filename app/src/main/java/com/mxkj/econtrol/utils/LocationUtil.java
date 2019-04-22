package com.mxkj.econtrol.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;

import com.mxkj.econtrol.app.APP;

/**
 * Created by liangshan on 2017/5/9.
 *
 * @Description： 位置工具类
 */

public class LocationUtil {

    /**
     * @Desc: 更新位置坐标
     * @author:liangshan
     */
    public static void updateLocation(Activity context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= 23) {
                String d[] = {Manifest.permission.ACCESS_FINE_LOCATION};
                context.requestPermissions(d, 12);
            }
            return;

        }
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LogUtil.i("Latitude:================" + location.getLatitude());
                LogUtil.i("longitude:================" + location.getLongitude());
                APP.latitude = location.getLatitude();
                APP.longitude = location.getLongitude();
                locationManager.removeUpdates(this);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LogUtil.i(provider + "0000000000");
            }

            @Override
            public void onProviderEnabled(String provider) {
                LogUtil.i("onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                LogUtil.i("onProviderDisabled");
            }
        };
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, locationListener);
            //等待600毫秒，等待位置更新
            SystemClock.sleep(600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
