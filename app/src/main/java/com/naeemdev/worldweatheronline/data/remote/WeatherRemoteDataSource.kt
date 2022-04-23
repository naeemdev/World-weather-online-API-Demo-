package com.naeemdev.worldweatheronline.data.remote

import com.google.gson.JsonObject
import com.naeemdev.worldweatheronline.model.Result

import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import com.naeemdev.worldweatheronline.util.ErrorUtils
import com.naeemdev.worldweatheronline.BuildConfig
import com.naeemdev.worldweatheronline.network.WeatherService
import com.naeemdev.worldweatheronline.util.Config.API_RESULT_FORMAT
import com.naeemdev.worldweatheronline.util.Config.DAYS


/**
 * fetches data from remote source
 */
class WeatherRemoteDataSource @Inject constructor(private val retrofit: Retrofit) {



    suspend fun fetchWeaterbyCity(
                                       query: String): Result<JsonObject> {
        val weatherService = retrofit.create(WeatherService::class.java);
        return getResponse(
            request = {
                weatherService.getWeatherByCity(BuildConfig.API_KEY,query,API_RESULT_FORMAT, DAYS)


                      },
            defaultErrorMessage = "Error fetching  list")

    }



    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")

            val result = request.invoke()
               if (result.isSuccessful) {


                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error(e.message.toString(), null)
        }
    }





}