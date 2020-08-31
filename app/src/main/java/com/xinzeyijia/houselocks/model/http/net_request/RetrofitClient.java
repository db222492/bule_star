package com.xinzeyijia.houselocks.model.http.net_request;

import android.content.Context;
import android.text.TextUtils;

import com.xinzeyijia.houselocks.base.Common;
import com.xinzeyijia.houselocks.model.http.interceptor.BaseInterceptor;
import com.xinzeyijia.houselocks.model.http.interceptor.CacheInterceptor;
import com.xinzeyijia.houselocks.model.http.interceptor.logging.LogInterceptor;
import com.xinzeyijia.houselocks.utils.SUtils;
import com.xinzeyijia.houselocks.utils.io.SPUtil;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.xinzeyijia.houselocks.util.LogUtils.loge;


/**
 * Created by goldze on 2017/5/10.
 * RetrofitClient封装单例类, 实现网络请求
 */
public class RetrofitClient {
    //超时时间
    private static final long DEFAULT_TIMEOUT = 10;
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024 * 1024;

    private static Context mContext = SUtils.getApp();


    private Cache cache = null;
    private File httpCacheDirectory;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() {

    }

    /**
     * 返回用于描述网络请求的接口
     * create you HomeApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service, String baseurl, Map<String, String> headers) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return getRetrofit(baseurl, headers, DEFAULT_TIMEOUT).create(service);
    }

    public <T> T create(final Class<T> service, String baseurl, long timeout) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return getRetrofit(baseurl, null, timeout).create(service);
    }

    public <T> T create(final Class<T> service, String baseurl) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return getRetrofit(baseurl, null, DEFAULT_TIMEOUT).create(service);
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return getRetrofit(null, null, DEFAULT_TIMEOUT).create(service);
    }

    /**
     * 获取Retrofit
     *
     * @param baseurl
     * @param headers
     * @return
     */
    private Retrofit getRetrofit(String baseurl, Map<String, String> headers, long timeout) {
        if (TextUtils.isEmpty(baseurl)) {//如果为空就把默认的baseUrl赋给他
            baseurl = "";
        }
        return new Retrofit.Builder()
                .client(getOkHttp(headers, timeout))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseurl)
                .build();
    }

    /**
     * 获取OKHTTP
     *
     * @param headers
     * @return
     */
    private OkHttpClient getOkHttp(Map<String, String> headers, long timeout) {

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "goldze_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            loge("Could not create http cache", e.getMessage());
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();

        OkHttpClient.Builder authorization = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
//                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
//                        .addHeader("token", (String) SPUtil.getInstance().get(Common.TOKEN, ""))
//                        .addHeader("Authorization","Bearer "+ (String) SPUtil.getInstance().get(Common.TOKEN, ""))
                        .addHeader("Authorization", (String) SPUtil.getInstance().get(Common.TOKEN, ""))
                        .build()))
                .addInterceptor(new CacheInterceptor())
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new LogInterceptor())
                .connectTimeout(timeout, TimeUnit.SECONDS)//40秒
                .readTimeout(timeout, TimeUnit.SECONDS)//40秒
//                .writeTimeout(timeout, TimeUnit.SECONDS)
//                .callTimeout(timeout, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS));
        authorization.build();
        return authorization.build();

        // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s

    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(NFCActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(NFCActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }
}
