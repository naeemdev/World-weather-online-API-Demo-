package com.naeemdev.worldweatheronline.model


class WeatherModel{

    var date: String?=""
    var sunrise: String?=""
    var sunset: String?=""
    var moonrise: String?=""
    var moonset: String?=""
    var moon_phase: String?=""
    var moon_illumination: String?=""

    var mList: List<WeatherHourlyModel>? = null
}