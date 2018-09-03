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
import android.os.Build
import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class WeatherAdapter(val context: Context, val weatherList: ArrayList<Weather>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val TAG = "WeatherAdapter"

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")

        val URL = "http://openweathermap.org/img/w/"
        val type = ".png"

        val dayURL = URL + weatherList[position].dayIcon + type
        val nightURL = URL + weatherList[position].nightIcon + type

        Glide.with(context)
                .load(dayURL)
                .into(holder.dayIcon);

         Glide.with(context)
                .load(nightURL)
                .into(holder.nightIcon);


        holder.day?.text = formatDate(weatherList[position].day)
        holder.dayTemp?.text = weatherList[position].dayTemp + "°C"
        //holder?.dayHumidity?.text = weatherList[position].dayHumidity
        holder.nightTemp?.text = weatherList[position].nightTemp + "°C"
        //holder?.nightHumidity?.text = weatherList[position].nightHumidity

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
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE)
            return newDay
        } else {
            return day
        }
*/

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
        val day = itemView.findViewById<TextView>(R.id.day)
        val dayTemp = itemView.findViewById<TextView>(R.id.dayTemp)
        //val dayHumidity = itemView.findViewById<TextView>(R.id.dayHumidity)
        val dayIcon = itemView.findViewById<ImageView>(R.id.dayIcon)
        val nightTemp = itemView.findViewById<TextView>(R.id.nightTemp)
        //val nightHumidity = itemView.findViewById<TextView>(R.id.nightHumidity)
        val nightIcon = itemView.findViewById<ImageView>(R.id.nightIcon)

    }

}