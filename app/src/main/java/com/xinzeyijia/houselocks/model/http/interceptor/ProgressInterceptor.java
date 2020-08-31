package com.xinzeyijia.houselocks.model.http.interceptor;

import android.support.annotation.NonNull;
import com.xinzeyijia.houselocks.model.http.download.ProgressResponseBody;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by air on 2016/12/5.
 */
public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }
}
