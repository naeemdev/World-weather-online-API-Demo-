package com.naeemdev.worldweatheronline.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API Service
 */
interface WeatherService {


    @FormUrlEncoded
    @POST("weather.ashx")
    suspend fun getWeatherByCity(
        @Field("key") key: String,
        @Field("q") query: String,
        @Field("format")format: String,
        @Field("num_of_days") days: Int,

    ) : Response<JsonObject>


}