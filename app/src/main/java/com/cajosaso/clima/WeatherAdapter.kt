package com.cajosaso.clima

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.text.format.DateUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter(val context: Context, val weatherList: ArrayList<Weather>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val TAG = "WeatherAdapter"

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")

        val URL = "http://openweathermap.org/img/w/"
        val type = ".png"

        val noonURL = URL + weatherList[position].noonIcon + type
        val midnightURL = URL + weatherList[position].midnightIcon + type

        if (weatherList[position].noonIcon != "null"){
            Glide.with(context)
                    .load(noonURL)
                    .into(holder.noonIcon);
        }

         Glide.with(context)
                .load(midnightURL)
                .into(holder.midnightIcon);


        holder.midnightDay?.text = formatDate(weatherList[position].midnightDay)
        if (weatherList[position].noonTemp != "null"){
            holder.noonTemp?.text = weatherList[position].noonTemp + "°C"
        }
        holder.midnightTemp?.text = weatherList[position].midnightTemp + "°C"

    }

    private fun formatDate(date: String): String{
        Log.d(TAG, "formatDate")
        val date = SimpleDateFormat("yyyy-MM-dd").parse(date)

        val calendar1 = Calendar.getInstance()
        calendar1.add(Calendar.DATE, 1)

        val calendar2 = Calendar.getInstance()
        calendar2.setTime(date)

        if(DateUtils.isToday(date.time)) {
            Log.d(TAG, "Today")
            return "Hoy"
        }

        if(calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)){
            Log.d(TAG, "Tomorrow")
            return "Mañana"
        }

        val formato = SimpleDateFormat("EEEE, dd/MM ", Locale("es", "AR"))
        val newDate = formato.format(date)
        return newDate

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_layout, parent, false)
        return WeatherViewHolder(v);
    }



    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return weatherList.size
    }

    class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val midnightDay = itemView.findViewById<TextView>(R.id.midnightDay)
        val noonTemp = itemView.findViewById<TextView>(R.id.noonTemp)
        val noonIcon = itemView.findViewById<ImageView>(R.id.noonIcon)
        val midnightTemp = itemView.findViewById<TextView>(R.id.midnightTemp)
        val midnightIcon = itemView.findViewById<ImageView>(R.id.midnightIcon)

    }

}