package com.naeemdev.worldweatheronline.adatpter

import android.content.Context
import com.naeemdev.worldweatheronline.model.CurrentConditionEntityModel
import com.naeemdev.worldweatheronline.adatpter.WeatherAdapter.ItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.naeemdev.worldweatheronline.adatpter.WeatherAdapter.MyViewHolder
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import com.naeemdev.worldweatheronline.R
import com.naeemdev.worldweatheronline.databinding.ItemForecastListBinding

class WeatherAdapter(
    private val mList: ArrayList<CurrentConditionEntityModel>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<MyViewHolder>() {
    var mContext: Context? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemForecastListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_forecast_list, viewGroup, false
        )
        mContext = viewGroup.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.item = mList[holder.adapterPosition]
        holder.binding.mainlayout.setOnClickListener { view: View? ->
            listener.onItemClick(
                holder.adapterPosition,
                mList[holder.adapterPosition],
                "detail"
            )
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(var binding: ItemForecastListBinding) : RecyclerView.ViewHolder(
        binding.root
    )


    fun updateData(newList: List<CurrentConditionEntityModel>) {
        mList.clear()
        val sortedList = newList.sortedBy { CurrentConditionEntityModel -> CurrentConditionEntityModel.type }
        mList.addAll(sortedList)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(pos: Int, mModel: CurrentConditionEntityModel?, tag: String?)
    }
}