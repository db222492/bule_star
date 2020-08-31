package com.xinzeyijia.houselocks.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.xinzeyijia.houselocks.util.LogUtils.logi;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(intent.getAction())) {
            //便携式热点的状态为：10---正在关闭；11---已关闭；12---正在开启；13---已开启
            int state = intent.getIntExtra("wifi_state",  0);
            logi("Receiver","wifi_state:  "+state);

            boolean isApEnabled = state == 13;
            logi("Receiver","isApEnabled : "+isApEnabled);

        }
    }
}