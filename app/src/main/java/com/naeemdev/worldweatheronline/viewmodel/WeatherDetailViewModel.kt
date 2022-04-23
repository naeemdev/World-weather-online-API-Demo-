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
class WeatherDetailViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private var _cityname = MutableLiveData<String>()
    private val _detail: LiveData<Result<ResponseMapper>> =
        _cityname.distinctUntilChanged().switchMap {
        liveData {
            weatherRepository.fetchWeatherDetail(it).onStart {
                emit(Result.loading())
            }.collect {
                emit(it!!)
            }
        }
    }
    val detail = _detail

    fun getDetail(cityname: String) {
        _cityname.value = cityname
    }
}