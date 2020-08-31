package com.xinzeyijia.houselocks.model.http.apiservice;

//import retrofit2.http.GET;
//import retrofit2.http.Query;


import com.xinzeyijia.houselocks.model.bean.BaseBean;
import com.xinzeyijia.houselocks.model.bean.CommonBean;
import com.xinzeyijia.houselocks.model.bean.FaceBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;

import static com.xinzeyijia.houselocks.model.http.Urls.*;

/**
 * Created by Lenovo on 2018/11/02.
 */

public interface HomeApiService {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载


    @POST(VERSION_QUERYSTATUS)
    Observable<ResponseBody> versionQuerystatus(@Body Map<String, Object> map);

    //获取验证码
    @POST(sendMsgr)
    Observable<BaseBean> getCode(@Body Map<String, Object> map);

    //密码登陆
    @POST(LOGIN)
    Observable<BaseBean> login(@Body Map<String, Object> map);


}
