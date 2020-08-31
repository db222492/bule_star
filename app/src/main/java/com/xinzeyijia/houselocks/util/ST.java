package com.xinzeyijia.houselocks.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xinzeyijia.houselocks.base.Common;
import com.xinzeyijia.houselocks.utils.NetUtil;
import com.xinzeyijia.houselocks.utils.ToastUtil;


/**
 * Created by EDZ on 2018/11/29.
 */

public class ST {

    private static Intent intent;

    public static boolean startActivity(Context context, Class<?> cls, Bundle bundle) {
        if (NetUtil.isConnected()) {
            intent = new Intent(context, cls);
            if (bundle != null)
                intent.putExtra(Common. BUNDLE, bundle);
            context.startActivity(intent);
        } else {
            ToastUtil.show("请先打开网络！");
            return false;
        }

        return true;
    }

    public static boolean startActivity(Context context, Intent intent) {
        if (NetUtil.isConnected()) {
            context.startActivity(intent);
        } else {
            ToastUtil.show("请先打开网络！");
            return false;
        }
        return true;
    }

    public static boolean startActivity(Context context, Class<?> cls) {
        if (NetUtil.isConnected()) {
            intent = new Intent(context, cls);
            context.startActivity(intent);
        } else {
            ToastUtil.show("请先打开网络！");
            return false;
        }

        return true;
    }
}
