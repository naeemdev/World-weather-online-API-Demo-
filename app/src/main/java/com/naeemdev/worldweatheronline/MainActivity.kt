package com.naeemdev.worldweatheronline

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.naeemdev.worldweatheronline.adatpter.WeatherAdapter
import com.naeemdev.worldweatheronline.adatpter.WeatherAdapter.ItemClickListener
import com.naeemdev.worldweatheronline.databinding.ActivityMainBinding
import com.naeemdev.worldweatheronline.model.CurrentConditionEntityModel
import com.naeemdev.worldweatheronline.model.Result
import com.naeemdev.worldweatheronline.util.Config.SELECTCITY
import com.naeemdev.worldweatheronline.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
@AndroidEntryPoint
class MainActivity : AppCompatActivity(),ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<CurrentConditionEntityModel>()
    private val viewModel by viewModels<WeatherViewModel>()

    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        init()
        observeData()
    }

    private fun init(){
        binding.loading.visibility = View.GONE
        weatherAdapter = WeatherAdapter( list, this)
        binding.rvData.adapter = weatherAdapter

    }


    private fun observeData() {

        viewModel.weatherList.observe(this) { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {

                    result.data?.results?.let { list ->

                        binding.loading.visibility = View.GONE
                        weatherAdapter.updateData(list)

                    }

                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        showError(it)

                    }
                    binding.loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    Log.e("size","loading")
                    binding.loading.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun showError(msg: String) {

        Snackbar.make(binding.clParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
    override fun onItemClick(pos: Int, mModel: CurrentConditionEntityModel?, tag: String?) {
        startActivity(mModel!!.type.toString())

    }


    private fun startActivity(cityname:String){
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra(SELECTCITY, cityname)
        startActivity(intent)
    }

}