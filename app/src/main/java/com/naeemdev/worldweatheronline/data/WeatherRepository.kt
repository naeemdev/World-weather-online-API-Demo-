package com.naeemdev.worldweatheronline.data

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.naeemdev.worldweatheronline.data.local.WeatherDao
import com.naeemdev.worldweatheronline.data.remote.WeatherRemoteDataSource
import com.naeemdev.worldweatheronline.model.*
import com.naeemdev.worldweatheronline.util.JsonParsing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Repository which fetches data from Remote or Local data sources
 */
class WeatherRepository @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherDao: WeatherDao
) {

    suspend fun fetchWeather(query: String): Flow<Result<ResponseMapper>?> {

        //var resultsList: List<CurrentConditionEntityModel> ?=null
        var resultsList = listOf<CurrentConditionEntityModel>()
        return flow {

            emit(fetchTrendingMoviesCached())
            emit(Result.loading())
            val result=weatherRemoteDataSource.fetchWeaterbyCity(query)
            if (result.status==Result.Status.SUCCESS){
                result.data?.let { it->
                    JsonParsing.parsingList(it).results?.let { it1 -> weatherDao.deleteAllData() }
                    JsonParsing.parsingList(it).results?.let { it1 -> weatherDao.insertAll(it1)
                        resultsList=it1
                    }

                }



            }
            if (!resultsList.isNullOrEmpty()) {
                emit(Result.success(ResponseMapper(resultsList)))
            }else{
                emit(  Result.error("check your Internet", Error(1,"check your Internet")))
            }

        }.flowOn(Dispatchers.IO)
    }


    private fun fetchTrendingMoviesCached(): Result<ResponseMapper>? =
        weatherDao.getAll()?.let {
            Result.success(ResponseMapper(it))
        }

    private fun fetchTrendingMoviesCached(cityname: String): Result<ResponseMapper>? =
        weatherDao.getDetail(cityname)?.let {
            Result.success(ResponseMapper(it))
        }




    suspend fun fetchWeatherDetail(query: String): Flow<Result<ResponseMapper>?> {

        var resultsList: List<CurrentConditionEntityModel> ?=null
        return flow {
            emit(fetchTrendingMoviesCached(query))
            emit(Result.loading())
            val result=weatherRemoteDataSource.fetchWeaterbyCity(query)
            if (result.status==Result.Status.SUCCESS){
                result.data?.let { it->
                    JsonParsing.parsingList(it).results?.let { it1 -> {
                        if (it1.isNotEmpty())
                        weatherDao.deleteSingleItem(query)
                    }
                    }
                    JsonParsing.parsingList(it).results?.let { it1 ->
                        if (it1.isNotEmpty()){
                        weatherDao.insertAll(it1)
                        resultsList=it1}}

                    if (!resultsList!!.isNullOrEmpty()) {
                        emit(Result.success(ResponseMapper(resultsList)))
                    }
                }

            }



        }.flowOn(Dispatchers.IO)
    }








}
