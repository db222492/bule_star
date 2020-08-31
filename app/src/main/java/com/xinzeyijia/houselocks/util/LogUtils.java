package com.xinzeyijia.houselocks.util;

import com.orhanobut.logger.Logger;
import com.xinzeyijia.houselocks.model.exception.XCrashHandlerUtils;
import com.xinzeyijia.houselocks.util.face_util.GsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * author : dibo
 * e-mail : db20206@163.com
 * date   : 2020/3/1311:38
 * desc   :
 * version: 1.0
 */
public class LogUtils {
    public static void loge(String str) {
        Logger.e(str);
    }

    public static void logi(String str) {
        Logger.i(str);
    }

    public static void logw(String str) {
        Logger.w(str);
    }

    public static void logd(String str) {
        Logger.d(str);
    }

    public static void logjson(String str) {
        Logger.json(str);
    }

    public static void loge(String tag, String str) {
        Logger.e(tag+str );
    }

    public static void logi(String tag, String str) {
        Logger.i(tag+str);
    }

    public static void logiToJson(String tag, Object o) {
        Logger.i(tag);
        String json = GsonUtils.toJson(o);
        Logger.json(json);
    }

    public static void logw(String tag, String str) {
        Logger.w(tag+str);
    }

    public static void logd(String tag, String str) {
        Logger.d(tag+str);
    }
    public static void writeLog( String str) {
        Logger.i(str);
        XCrashHandlerUtils.getInstance().writeLogToSDCard(str);
    }

    public static String json(String json) {
        StringBuilder str = new StringBuilder();
        if (!Tools.NotNull(json)) {
            logd("Empty/Null json content");
            return "";
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = null;

                jsonObject = new JSONObject(json);
                String message = jsonObject.toString(2);
                str.append(message);
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(2);
                str.append(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str.toString();


    }

    public static String logiObj2json(Object o) {
        String json = GsonUtils.toJson(o);
        StringBuilder str = new StringBuilder();
        if (!Tools.NotNull(json)) {
            logd("Empty/Null json content");
            return "";
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = null;

                jsonObject = new JSONObject(json);
                String message = jsonObject.toString(2);
                str.append(message);
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(2);
                str.append(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str.toString();


    }
//
//    public static void main(String[] args) {
//        System.out.println("main: "+ Crc16Util.getCrc16Sky(("08000000002079C31BF4")));
//    }
}
