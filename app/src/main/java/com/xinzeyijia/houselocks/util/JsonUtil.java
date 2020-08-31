package com.xinzeyijia.houselocks.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Created by zhangzc on 18-6-7.
 */

public class JsonUtil {
    private static JsonUtil ourInstance = new JsonUtil();

    public static JsonUtil getInstance() {
        return ourInstance;
    }

    private Gson gson;

    private JsonUtil() {
        gson = new Gson();
    }

    public Object getBean(String data, Class tClass) {
        Object t = null;
        try {
            t = gson.fromJson(data, tClass);
        } catch (Exception e) {

        }
        return t;
    }

    public String toJSON(Object object) {
        String jsonData = "";
        try {
            jsonData = gson.toJson(object);
        } catch (Exception e) {
            jsonData = "";
            e.printStackTrace();
        }
        return jsonData;
    }


    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public Object jsonToLowerCase(Object obj) {
        String json = toJSON(obj);
        String regex = "\"[a-zA-Z0-9#_]+\":";
        Pattern pattern = Pattern.compile(regex);
        StringBuffer sb = new StringBuffer();
        // 方法二：正则替换
        java.util.regex.Matcher m = pattern.matcher(json);

        while (m.find()) {
            m.appendReplacement(sb, m.group().toLowerCase());
        }
        m.appendTail(sb);
        return getBean(sb.toString(), Object.class);
    }
}
