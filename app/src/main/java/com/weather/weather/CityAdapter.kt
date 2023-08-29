package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class CityAdapter(private val cities: List<City>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewFlag: ImageView = itemView.findViewById(R.id.imageViewFlag)
        val textViewCityName: TextView = itemView.findViewById(R.id.textViewCityName)
    }

    interface OnItemClickListener {
        fun onItemClick(city: City)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]

        // Set city name and flag
        holder.textViewCityName.text = city.name
        holder.imageViewFlag.setImageResource(city.flagResourceId)

        // Set click listener for each item view
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(city)
        }
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}

