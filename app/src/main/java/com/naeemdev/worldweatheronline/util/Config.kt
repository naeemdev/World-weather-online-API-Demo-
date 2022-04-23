package com.naeemdev.worldweatheronline.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object Config {
    const val BASE_URL = "https://api.worldweatheronline.com/premium/v1/"
     val defaultCities = "Amman;Irbid;Akaba"
    const val API_RESULT_FORMAT = "json"
    const val SELECTCITY ="selectedcity"
    const val DAYS = 1


    @JvmStatic
    @BindingAdapter("weathericon")
    fun loadImage(view: ImageView, weathericon: String) {

        Glide.with(view.context)
            .load(weathericon)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("formatDate")
    fun formatDate(view: TextView,weather_hour: Int) {
        val time= weather_hour/100
        view.text = "$time:00"
    }
}

