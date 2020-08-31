package com.xinzeyijia.houselocks.model.http;

import com.xinzeyijia.houselocks.model.http.apiservice.HomeApiService;
import com.xinzeyijia.houselocks.model.http.net_request.RetrofitClient;
import com.xinzeyijia.houselocks.model.http.net_request.RetrofitClient2;

import static com.xinzeyijia.houselocks.BuildConfig.LOG_DEBUG;

public class Urls {
    //欧比门锁

    private final static String BASE_URL3 = "https://aip.baidubce.com/";//本地环境2
    public static final HomeApiService HOME_API_SERVICE3 = RetrofitClient2.getInstance().create(HomeApiService.class, BASE_URL3);

    private final static String BASE_URL2 = "http://apitest.weixinlock.com/";//线上
    private final static String BASE_URL = "https://www.xinzeyijia.com/ylxtest/";//线下

    public static String getBASE_URL() {
        if (LOG_DEBUG) {
            return BASE_URL;
        } else {
            return BASE_URL2;
        }
    }

    public static HomeApiService getHomeApiService() {
        return RetrofitClient.getInstance().create(HomeApiService.class, getBASE_URL());
    }

    public static final String FORM_URLENCODE_DATA = "application/x-www-form-urlencoded";
    private static final String VERSION_NAME = "api/dyne/";
    public static final String VERSION_QUERYSTATUS = VERSION_NAME + "version/v1/queryVersion";//查询版本更新状态
    public static final String LOGIN = VERSION_NAME + "user/v1/login";//登录
    public static final String sendMsgr = VERSION_NAME + "lock/v1/sendMsg";//发送短信接口
    public static final String getFile = getBASE_URL() + VERSION_NAME + "lock/v1/dynebj/getFile?id=";//图片拼接
}
