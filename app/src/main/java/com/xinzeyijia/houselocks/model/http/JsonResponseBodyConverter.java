package com.xinzeyijia.houselocks.model.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by Lenovo on 2018/08/24.
 */

final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //获取服务器返回的加密字符串
//        String resultData = value.string();
//        //解密字符串
//        String result = DesUtil.decryptThreeDESECB(resultData);
//        //把字符串转换成reader对象
        String result = value.string();
        // Log.d("JsonResponseBodyConvert", result);
        Reader stringReader = new StringReader(result);
        JsonReader jsonReader = gson.newJsonReader(stringReader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
