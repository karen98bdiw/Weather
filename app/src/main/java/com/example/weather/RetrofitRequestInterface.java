package com.example.weather;

import com.example.weather.FiveDayForecastWeatherAPIResponseInterface.Response;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitRequestInterface {

    @GET("forecast?APPID=9328aa88a8e3e7336e7ca0df235a226a&id=524901")
     Single<Response> getApiResponse(@Query("id") String cityId,@Query("APPID") String APPID);

}
