package com.xinzeyijia.houselocks.util.chicun;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import java.text.DecimalFormat;

public class MyDensity {
    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;
    private static int width=1000;

    //此方法在Application的onCreate方法中调用    Density.setDensity(this);
    public static void setDensity(@NonNull final Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (appDensity == 0) {
            //初始化的时候赋值（只在Application里面初始化的时候会调用一次）
            appDensity = appDisplayMetrics.density;   //首先获取系统的density值
            appScaledDensity = appDisplayMetrics.scaledDensity;  //然后获取系统字体的scaledDensity值

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {}
            });
        }

        //调用修改density值的方法(默认以宽度作为基准)
        setAppOrientation(null, AppUtils.WIDTH,width);
    }

    //此方法用于在某一个Activity里面更改适配的方向    Density.setOrientation(mActivity, "width/height");
    public static void setOrientation(Activity activity, String orientation,int width) {
        setAppOrientation(activity, orientation,width);
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * <p>
     * orientation:方向值,传入width或height
     */
    private static void setAppOrientation(@Nullable Activity activity, String orientation,int width) {

        float targetDensity = 0;
        try {
            Double division;
            //根据带入参数选择不同的适配方向
            if (orientation.equals("height")) {
                //appDisplayMetrics.heightPixels/667
                /*
                 * 用屏幕的有效高度除以真实的dp值
                 */
                division= Operation.division(appDisplayMetrics.heightPixels, 667);
            } else {
                division = Operation.division(appDisplayMetrics.widthPixels,width);
            }
            //由于手机的长宽不尽相同,肯定会有除不尽的情况,有失精度,所以在这里把所得结果做了一个保留两位小数的操作
            DecimalFormat df = new DecimalFormat("0.00");
            String s = df.format(division);
            targetDensity = Float.parseFloat(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         *
         * (因为最开始初始化的时候,activity为null,所以只设置application的值就可以了...
         * 所以在这里判断了一下,如果传有activity的话,再设置Activity的值)
         */
        if (activity != null) {
            DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = targetDensity;
            activityDisplayMetrics.scaledDensity = targetScaledDensity;
            activityDisplayMetrics.densityDpi = targetDensityDpi;
        } else {
            appDisplayMetrics.density = targetDensity;
            appDisplayMetrics.scaledDensity = targetScaledDensity;
            appDisplayMetrics.densityDpi = targetDensityDpi;
        }
    }
    public static void setDefault(Activity activity){
        setAppOrientation(activity,AppUtils.WIDTH,width);
    }
}