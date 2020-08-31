package com.xinzeyijia.houselocks;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import com.clj.fastble.BleManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.xinzeyijia.houselocks.base.Common;
import com.xinzeyijia.houselocks.model.bean.BaseBean;
import com.xinzeyijia.houselocks.model.bean.DataBean;
import com.xinzeyijia.houselocks.model.exception.XCrashHandlerUtils;
import com.xinzeyijia.houselocks.util.chicun.Density;
import com.xinzeyijia.houselocks.util.sdutil.Path;
import com.xinzeyijia.houselocks.util.sdutil.SDCardUtils;
import com.xinzeyijia.houselocks.utils.SUtils;
import com.xinzeyijia.houselocks.utils.ToastUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

//友盟Appkey:5d47beb83fc1953682000a83
//Aq0zwuQoYEge-YPqjXPP27FOw1AnrHSS866I1D0EKiJz    DeviceTOKEN

/**
 * Created by EDZ on 2018/11/13.
 */
//UMConfigure.init(Context context, String appkey, String channel, int deviceType, String pushSecret);
@SuppressLint({"TrustAllX509TrustManager", "StaticFieldLeak"})
public class App extends Application {
    private static Context context;
    private static App mInstance;
    private BaseBean basebean;
    private DataBean dataBean = new DataBean();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    public static Context getAppContext() {
        return context;
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
//            layout.setEnableAutoLoadMore(true);
//            layout.setEnableOverScrollDrag(false);
//            layout.setEnableOverScrollBounce(true);
//            layout.setEnableLoadMoreWhenContentNotFull(true);
//            layout.setEnableScrollContentWhenRefreshed(true);
            layout.setEnableLoadMoreWhenContentNotFull(false);
            //在刷新时候禁止操作内容视图
            layout.setDisableContentWhenLoading(true);
            layout.setDisableContentWhenRefresh(true);
//            layout.setNoMoreData(true);
            layout.setPrimaryColorsId(R.color.white, R.color.hui);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
//            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });

    }

    public static Context getContext() {
        return context;
    }

    private void initImageLoader() {
        SDCardUtils.getInstance().createFolder(Path.DEFAULT_IMAGE_CACHE_PATH);
    }


    @Override
    public void onCreate() {
        super.onCreate();//
        CrashReport.initCrashReport(getApplicationContext(), "8830bc3d68", true);
        context = this;
        mInstance = this;
        //全局捕获异常
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
        XCrashHandlerUtils.getInstance().init(this);
        /*
         * 初始化尺寸工具类
         */
        Density.setDensity(this);
        handleSSLHandshake();
        //设置LOG开关，默认为false
        initImageLoader();//初始化本地储存文件夹
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(0)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
//                .logStrategy( )  //（可选）更改要打印的日志策略。 默认LogCat
                .tag(Common.TAG)                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            // 是否开启打印功能，返回true则打印，否则不打印
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.LOG_DEBUG;
            }
        });


        SUtils.initialize(this);
        ToastUtil.setBgColor(ContextCompat.getColor(this, R.color.hui3));

        ToastUtil.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 320);

        BleManager.getInstance().init(this);
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(Common.CONNECT_COUNT, Common.CONNECT_COUNT_TIME)
                .setSplitWriteNum(20)
                .setConnectOverTime(Common.CONNECT_OVER_TIME)
                .setOperateTimeout(Common.OPERATE_TIME_OUT);

    }

    public static App getmInstance() {
        return mInstance;
    }

    public BaseBean getBasebean() {
        return basebean;
    }

    public void setBasebean(BaseBean basebean) {
        this.basebean = basebean;
    }


    public void saveDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception ignored) {
        }
    }

}
