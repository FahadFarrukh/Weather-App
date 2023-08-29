package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ForecastAdapter(private val forecastList: List<ForecastItem>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val textMaxTemp: TextView = itemView.findViewById(R.id.textMaxTemp)
        val textMinTemp: TextView = itemView.findViewById(R.id.textMinTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentItem = forecastList[position]

        holder.textDate.text = currentItem.date
        holder.textMaxTemp.text = "${currentItem.maxTemp}°"
        holder.textMinTemp.text = "${currentItem.minTemp}°"
    }

    override fun getItemCount() = forecastList.size
}
