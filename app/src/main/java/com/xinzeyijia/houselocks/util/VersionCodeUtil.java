package com.xinzeyijia.houselocks.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.xinzeyijia.houselocks.App;


/**
 * Created by EDZ on 2019/1/9.
 */

public class VersionCodeUtil {
    private static class SingTon {
        private static final VersionCodeUtil VERSION_CODE_UTIL = new VersionCodeUtil();
    }

    public static VersionCodeUtil getInstance() {
        return SingTon.VERSION_CODE_UTIL;
    }

    public int getVersionCode() {
        PackageManager manager = App.getAppContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(App.getAppContext().getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getVersionName() {
        PackageManager manager = App.getAppContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(App.getAppContext().getPackageName(), 0);
            String versionName = info.versionName;
            String[] split = versionName.split("-");
            return split[0];
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
