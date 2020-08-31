package com.xinzeyijia.houselocks.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;


/**
 * Created by lyw on 2017/6/12.
 */
public class PermissionUtil {
    /**
     * 请求地理位置
     *
     * @param context
     */
    public static void requestLocationPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!isHasLocationPermission(context)) {
                ActivityCompat.requestPermissions((Activity) context, PermissionManager.PERMISSION_LOCATION, PermissionManager.REQUEST_LOCATION);
            }
        }
    }

    /**
     * 判断是否有地理位置
     *
     * @param context
     * @return
     */
    public static boolean isHasLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}

