package com.nguyen.sunshine2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("daily")
    fun fetchWeather(@Query("q") zipcode: String, @Query("units") units: String, @Query("cnt") numDays: Int, @Query("appid") apiKey: String) : Call<Record>
}