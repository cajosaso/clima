package com.cajosaso.clima

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.widget.LinearLayout
import android.widget.Toast



class WeatherAdapter(val context: Context, val weatherList: ArrayList<Weather>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val TAG = "WeatherAdapter"

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        holder?.dayOfTheWeek?.text = weatherList[position].dayOfTheWeek
        holder?.weather?.text = weatherList[position].weather
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.weather_item_layout, parent, false)
        return WeatherViewHolder(v);
    }



    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return weatherList.size
    }

    class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dayOfTheWeek = itemView.findViewById<TextView>(R.id.dayOfTheWeek)
        val weather = itemView.findViewById<TextView>(R.id.weather)

    }

}