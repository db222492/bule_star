package com.xinzeyijia.houselocks.util.socketutil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by ye on 2016/12/11.
 */

public class GsonHelp {
    private static Gson gson;

    public static <t> t getJsonData(String json, Class<t> tClass) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(json, tClass);
    }

    public static <t> t getJsonData(String json, Type tClass) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(json, tClass);
    }

    public static <T> List<T> getJsonListData(String json, TypeToken<List<T>> token) {
        try {
            if (gson == null) {
                gson = new Gson();
            }

            return gson.fromJson(json, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Map<String, String> map) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(map);
    }

    public static String toJson(Object o) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.toJson(o);
    }
}
