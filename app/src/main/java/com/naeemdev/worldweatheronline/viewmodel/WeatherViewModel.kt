package com.naeemdev.worldweatheronline.viewmodel

import androidx.lifecycle.*
import com.naeemdev.worldweatheronline.data.WeatherRepository
import com.naeemdev.worldweatheronline.model.CurrentConditionEntityModel
import com.naeemdev.worldweatheronline.model.ResponseMapper
import com.naeemdev.worldweatheronline.model.Result
import com.naeemdev.worldweatheronline.util.Config
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _weatherList = MutableLiveData<Result<ResponseMapper>>()
    val weatherList = _weatherList

    init {
        fetchWeather(Config.defaultCities)
    }

     private fun fetchWeather(query: String) {
        viewModelScope.launch {
            weatherRepository.fetchWeather(query).collect {
                _weatherList.value = it
            }
        }
    }



}