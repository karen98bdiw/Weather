package com.example.weather;

import com.example.weather.FiveDayForecastWeatherAPIResponseInterface.Response;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitRequestInterface {

    @GET("forecast")
     Single<Response> getApiResponse(@Query("id") String cityId,@Query("APPID") String APPID);

}
