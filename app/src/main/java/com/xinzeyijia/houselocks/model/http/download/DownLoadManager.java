package com.xinzeyijia.houselocks.model.http.download;

import com.xinzeyijia.houselocks.model.http.interceptor.ProgressInterceptor;
import com.xinzeyijia.houselocks.util.NetworkUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import java.util.concurrent.TimeUnit;

/**
 * Created by goldze on 2017/5/11.
 * 文件下载管理，封装一行代码实现下载
 */

public class DownLoadManager {

    private static Retrofit retrofit;

    private DownLoadManager() {
        buildNetWork();
    }
    private static final class SingTon{
        private static final DownLoadManager DOWN_LOAD_MANAGER =new DownLoadManager();
    }
    /**
     * 单例模式
     *
     * @return DownLoadManager
     */
    public static DownLoadManager getInstance() {
        return SingTon.DOWN_LOAD_MANAGER;
    }


    //下载
    public void load(String downUrl, final ProgressCallBack callBack) {
        retrofit.create(ApiService.class)
                .download(downUrl)
                .subscribeOn( Schedulers.io())//请求网络 在调度者的io线程
                .observeOn( Schedulers.io()) //指定线程保存文件
                .doOnNext(callBack::saveFile)
                .observeOn( AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new DownLoadSubscriber<>(callBack));
    }

    private void buildNetWork() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl( NetworkUtil.url)
                .build();
    }

    private interface ApiService {
        @Streaming
        @GET
        Observable<ResponseBody> download(@Url String url);
    }
}
