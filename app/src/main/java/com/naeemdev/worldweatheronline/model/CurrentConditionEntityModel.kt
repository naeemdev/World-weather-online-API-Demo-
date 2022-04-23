package com.naeemdev.worldweatheronline.model

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class CurrentConditionEntityModel(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var type: String?,
    var query: String?,
    var observation_time: String?,
    var temp_C: String?,

    var temp_F: String?,
    var iconUrl: String?,
    var weatherDesc: String?,
    var windspeedMiles: Int?,
    var windspeedKmph: Int?,
    var winddirDegree: Int?,
    var humidity: Int?,
    var pressure: Int?,
    var pressureInches: Int?,
    var cloudcover: Int?,
    var FeelsLikeC: String?,
    var FeelsLikeF: String?,
    @Embedded val mWeatherModel: WeatherModel


)