package com.naeemdev.worldweatheronline.adatpter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.naeemdev.worldweatheronline.R

import com.naeemdev.worldweatheronline.databinding.ItemHourForecastListBinding

import com.naeemdev.worldweatheronline.model.WeatherHourlyModel

class HourlyWeatherAdapter(
    private val mList: ArrayList<WeatherHourlyModel>,

    ) : RecyclerView.Adapter<HourlyWeatherAdapter.MyViewHolder>() {
    var mContext: Context? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemHourForecastListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_hour_forecast_list, viewGroup, false
        )
        mContext = viewGroup.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.item = mList[holder.adapterPosition]

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(var binding: ItemHourForecastListBinding) : RecyclerView.ViewHolder(
        binding.root
    )


    fun updateData(newList: List<WeatherHourlyModel>?) {
        mList.clear()

        mList.addAll(newList!!)
        notifyDataSetChanged()
    }


}