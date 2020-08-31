package com.xinzeyijia.houselocks.model.http.apiservice;

import com.xinzeyijia.houselocks.base.BaseWeatherResponse;
import com.xinzeyijia.houselocks.model.bean.HeWeather;
import com.xinzeyijia.houselocks.model.bean.HeWeatherAir;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 天气查询接口
 * Created by liyu on 2016/10/31.
 */

public interface WeatherController {

    @GET("https://free-api.heweather.com/s6/weather")
    Observable<BaseWeatherResponse<HeWeather>> getWeather(@Query("key") String key, @Query("location") String city);

    @GET("https://free-api.heweather.com/s6/air/now")
    Observable<BaseWeatherResponse<HeWeatherAir>> getAir(@Query("key") String key, @Query("location") String city);

    @GET("https://free-api.heweather.com/s6/air/now")
    Call<BaseWeatherResponse<HeWeatherAir>> getAirSync(@Query("key") String key, @Query("location") String city);

}
