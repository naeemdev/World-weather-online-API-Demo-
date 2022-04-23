package com.naeemdev.worldweatheronline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.naeemdev.worldweatheronline.R
import com.naeemdev.worldweatheronline.adatpter.HourlyWeatherAdapter
import com.naeemdev.worldweatheronline.adatpter.WeatherAdapter
import com.naeemdev.worldweatheronline.databinding.ActivityDetailsBinding
import com.naeemdev.worldweatheronline.databinding.ActivityDetailsBindingImpl
import com.naeemdev.worldweatheronline.databinding.ActivityMainBinding
import com.naeemdev.worldweatheronline.model.CurrentConditionEntityModel
import com.naeemdev.worldweatheronline.model.Result
import com.naeemdev.worldweatheronline.model.WeatherHourlyModel
import com.naeemdev.worldweatheronline.util.Config.SELECTCITY
import com.naeemdev.worldweatheronline.viewmodel.WeatherDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val viewModel by viewModels<WeatherDetailViewModel>()
    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    private val list = ArrayList<WeatherHourlyModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        init()
    }
    fun init(){

        intent?.getStringExtra(SELECTCITY)?.let { cityname ->
            viewModel.getDetail(cityname)
            observeData()
        } ?: showError("Unknown City")
        hourlyWeatherAdapter = HourlyWeatherAdapter( list,)
        binding.rvData.adapter = hourlyWeatherAdapter

    }
    private fun observeData() {

        viewModel.detail.observe(this) { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {

                    result.data?.results?.let { list ->
                        if (!list.isNullOrEmpty()) {
                            binding.item = list.get(0)
                            if (list.get(0).mWeatherModel != null) {

                                hourlyWeatherAdapter.updateData(list.get(0).mWeatherModel.mList)
                            }
                        }


                    }

                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        showError(it)

                    }

                }

                Result.Status.LOADING -> {

                }
            }

        }
    }

    private fun showError(msg: String) {

        Snackbar.make(binding.clParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}