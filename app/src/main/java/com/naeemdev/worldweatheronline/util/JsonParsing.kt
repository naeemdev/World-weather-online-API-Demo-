package com.naeemdev.worldweatheronline.util

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.naeemdev.worldweatheronline.model.CurrentConditionEntityModel
import com.naeemdev.worldweatheronline.model.ResponseMapper
import com.naeemdev.worldweatheronline.model.WeatherHourlyModel
import com.naeemdev.worldweatheronline.model.WeatherModel

object JsonParsing {


    fun parsingList(jsonObject: JsonObject): ResponseMapper {
        Log.e("error",jsonObject.toString())

        val mList: ArrayList<CurrentConditionEntityModel> = ArrayList()
        if (jsonObject.has("data")){

            if ( jsonObject.get("data").asJsonObject.has("area")){
                val mJsonArray=jsonObject.get("data").asJsonObject.getAsJsonArray("area")
                for (i in 0 until mJsonArray.size()) {
                    mList.add(JsonParsing.parsingJsonObj(mJsonArray.get(i).asJsonObject))
                }

            }else{
                mList.add(JsonParsing.parsingJsonObj(jsonObject.get("data").asJsonObject))
            }

        }
        val mTrendingMovieResponse = ResponseMapper(mList);

        return mTrendingMovieResponse;

    }
    //current condition Parsing
    fun parsingJsonObj(jsonObject: JsonObject): CurrentConditionEntityModel {

        Log.e("error_jso",jsonObject.toString())
        var  type=""
        var query=""
        if (jsonObject.has("request")){
            val jsonArray=jsonObject.getAsJsonArray("request")
            if (jsonArray.size()>0) {
                (0 until jsonArray.size()).forEach {
                    val request = jsonArray.get(it).asJsonObject
                    type= request.get("type").asString
                    query= request.get("query").asString
                    //mMovieModel!!.type=type
                    //mMovieModel.query =query

                }

            }

        }
        var observation_time=""
        var temp_C=""
        var temp_F=""
        var windspeedMiles=0
        var windspeedKmph=0
        var winddirDegree=0
        var humidity=0
        var pressure=0
        var pressureInches=0
        var cloudcover=0
        var weatherDesc=""
        var iconUrl=""
        var FeelsLikeC=""
        var FeelsLikeF=""
        if ( jsonObject.has("current_condition")){
            val jsonConditionArray=jsonObject.getAsJsonArray("current_condition")
            if (jsonConditionArray.size()>0) {
                for (i in 0 until jsonConditionArray.size()) {
                    observation_time =
                        jsonConditionArray.get(i).asJsonObject.get("observation_time").asString
                    temp_C =
                        jsonConditionArray.get(i).asJsonObject.get("temp_C").asString
                    temp_F =
                        jsonConditionArray.get(i).asJsonObject.get("temp_F").asString
                    windspeedMiles =                        jsonConditionArray.get(i).asJsonObject.get("windspeedMiles").asInt
                    windspeedKmph =
                        jsonConditionArray.get(i).asJsonObject.get("windspeedKmph").asInt
                    winddirDegree =
                        jsonConditionArray.get(i).asJsonObject.get("winddirDegree").asInt
                    humidity =
                        jsonConditionArray.get(i).asJsonObject.get("humidity").asInt

                    pressure =
                        jsonConditionArray.get(i).asJsonObject.get("pressure").asInt
                    pressureInches =
                        jsonConditionArray.get(i).asJsonObject.get("pressureInches").asInt
                    cloudcover =
                        jsonConditionArray.get(i).asJsonObject.get("cloudcover").asInt
                    weatherDesc =
                        jsonConditionArray.get(i).asJsonObject.get("weatherDesc")
                            .asJsonArray.get(0).asJsonObject.get("value").asString
                    iconUrl =
                        jsonConditionArray.get(i).asJsonObject.get("weatherIconUrl")
                            .asJsonArray.get(0).asJsonObject.get("value").asString

                    FeelsLikeC =
                        jsonConditionArray.get(i).asJsonObject.get("FeelsLikeC").asString
                    FeelsLikeF =
                        jsonConditionArray.get(i).asJsonObject.get("FeelsLikeF").asString


                }
            }

        }
        var mWeatherModel= WeatherModel()
        if ( jsonObject.has("weather")) {
            val jsonWeaterArray=jsonObject.getAsJsonArray("weather")
            mWeatherModel=weatherParsing(jsonWeaterArray)
        }



        return CurrentConditionEntityModel(0,query,type, observation_time,
            temp_C, temp_F, iconUrl, weatherDesc,
            windspeedMiles, windspeedKmph, winddirDegree,
            humidity, pressure, pressureInches, cloudcover,FeelsLikeC,FeelsLikeF,mWeatherModel)


    }

    //current Weather Parsing
    private fun weatherParsing(mJsonArray: JsonArray): WeatherModel {

        val mWeatherModel= WeatherModel()
        if (mJsonArray.size()>0) {
            for (i in 0 until mJsonArray.size()) {

                mWeatherModel.date= mJsonArray.get(i).asJsonObject.get("date").asString
                if (mJsonArray.get(i).asJsonObject.has("astronomy")){
                    val astronomyObj=mJsonArray.get(i).asJsonObject.get("astronomy").asJsonArray
                    mWeatherModel.sunrise=astronomyObj.get(0).asJsonObject.get("sunrise").asString
                    mWeatherModel.sunset=astronomyObj.get(0).asJsonObject.get("sunset").asString
                    mWeatherModel.moonrise=astronomyObj.get(0).asJsonObject.get("moonrise").asString
                    mWeatherModel.moonset=astronomyObj.get(0).asJsonObject.get("moonset").asString
                    mWeatherModel.moon_phase=astronomyObj.get(0).asJsonObject.get("moon_phase").asString
                    mWeatherModel.moon_illumination=astronomyObj.get(0).asJsonObject.get("moon_illumination").asString
                }

                if (mJsonArray.get(i).asJsonObject.has("hourly")){

                    mWeatherModel.mList= hourlyWeatherParsing(mJsonArray.get(i).asJsonObject.get("hourly").asJsonArray)
                }

            }
        }

        return mWeatherModel;

    }
    //current Weather Hourly Parsing
    private fun hourlyWeatherParsing(mJsonArray: JsonArray):ArrayList<WeatherHourlyModel>{
        val mList: ArrayList<WeatherHourlyModel> = ArrayList()

        if (mJsonArray.size()>0) {
            for (i in 0 until mJsonArray.size()) {
                val mWeatherModel= WeatherHourlyModel()
                mWeatherModel.time= mJsonArray.get(i).asJsonObject.get("time").asInt

                mWeatherModel.FeelsLikeC= mJsonArray.get(i).asJsonObject.get("FeelsLikeC").asInt
                mWeatherModel.FeelsLikeF= mJsonArray.get(i).asJsonObject.get("FeelsLikeF").asInt
                if (mJsonArray.get(i).asJsonObject.has("weatherIconUrl")){
                    mWeatherModel.weatherIconUrl= mJsonArray.get(i).asJsonObject.get("weatherIconUrl")
                        .asJsonArray.get(0).asJsonObject.get("value").asString
                }

                if (mJsonArray.get(i).asJsonObject.has("weatherDesc")){
                    mWeatherModel.weatherDesc= mJsonArray.get(i).asJsonObject.get("weatherDesc").asJsonArray.get(0).asJsonObject.get("value").asString
                }



                mList.add(mWeatherModel)

            }
        }

        return mList;

    }

}