package com.xinzeyijia.houselocks.model.http.interceptor;

import com.xinzeyijia.houselocks.utils.NetUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        boolean netAvailable = NetUtil.isConnected();
        if (netAvailable) {
            request = request.newBuilder()
                    //网络可用 强制从网络获取数据
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        } else {
            request = request.newBuilder()
                    //网络不可用 从缓存获取
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);

        if (netAvailable) {
            int maxAge = 60;
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    // 有网络时 设置缓存超时时间1个小时
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 3;
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    // 无网络时，设置超时为1周7 * 24 * 60 * 60
                    .header("Cache-Control", "public, only-if-cached, max-stale=" +maxStale)
                    .build();
        }
        return response;
    }
}

