package com.xinzeyijia.houselocks.util.imge.transformation;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import com.xinzeyijia.houselocks.model.http.net_request.HttpsUtils;
import okhttp3.OkHttpClient;

import javax.net.ssl.X509TrustManager;

@GlideModule
public class OkHttpAppGlideModule extends AppGlideModule {
    //    @Override
//    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
//
//    }
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        //手机app路径
        appRootPath = context.getCacheDir().getPath();
        builder.setDiskCache(
                new DiskLruCacheFactory(getStorageDirectory() + "/GlideDisk", diskCacheSizeBytes)
        );
    }

    //外部路径
    private String sdRootPath = Environment.getExternalStorageDirectory().getPath();
    private String appRootPath = null;

    private String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                sdRootPath : appRootPath;
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // 自定义OkHttpClient
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, (X509TrustManager) null);
        OkHttpClient.Builder builder = client.newBuilder()
                .readTimeout(10000, TimeUnit.SECONDS)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS);
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        // 采用自定义的CustomOkHttpUrlLoader
        registry.replace(GlideUrl.class, InputStream.class, new CustomOkHttpUrlLoader.Factory(client));
    }
}
