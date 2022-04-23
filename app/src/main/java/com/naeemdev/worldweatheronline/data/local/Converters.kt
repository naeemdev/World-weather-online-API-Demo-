package com.naeemdev.worldweatheronline.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.naeemdev.worldweatheronline.model.WeatherHourlyModel

class Converters {
    @TypeConverter
    fun listToJsonString(value: List<WeatherHourlyModel>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<WeatherHourlyModel>::class.java).toList()
}