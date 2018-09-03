package com.cajosaso.clima

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
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


class WeatherAdapter(val context: Context, val weatherList: ArrayList<Weather>, val day: Boolean): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

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
            holder.noonTemp?.text = weatherList[position].noonTemp.toFloat().toInt().toString() + "°C"
        }
        holder.midnightTemp?.text = weatherList[position].midnightTemp.toFloat().toInt().toString() + "°C"

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
        return newDate.capitalize()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_layout, parent, false)
        val card = v.findViewById<CardView>(R.id.card_view)
        val midnightDay = v.findViewById<TextView>(R.id.midnightDay)
        val noonTemp = v.findViewById<TextView>(R.id.noonTemp)
        val midnightTemp = v.findViewById<TextView>(R.id.midnightTemp)

        if (day) {
            card.setCardBackgroundColor(Color.parseColor("#BFFFFFFF"))
            midnightDay.setTextColor(Color.parseColor("#FF000000"))
            noonTemp.setTextColor(Color.parseColor("#FF000000"))
            midnightTemp.setTextColor(Color.parseColor("#FF000000"))
        }
        else {
            card.setCardBackgroundColor(Color.parseColor("#c308052b"))
            midnightDay.setTextColor(Color.parseColor("#FFFFFFFF"))
            noonTemp.setTextColor(Color.parseColor("#FFFFFFFF"))
            midnightTemp.setTextColor(Color.parseColor("#FFFFFFFF"))
        }

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
        val card = itemView.findViewById<CardView>(R.id.card_view)

    }

}