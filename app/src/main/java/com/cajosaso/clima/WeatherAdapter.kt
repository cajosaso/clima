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


class WeatherAdapter(private val context: Context, private val weatherList: ArrayList<Weather>, private val day: Boolean): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val TAG: String = WeatherAdapter::class.java.simpleName

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")

        val url = "http://openweathermap.org/img/w/"
        val type = ".png"

        val noonURL = url + weatherList[position].noonIcon + type
        val midnightURL = url + weatherList[position].midnightIcon + type

        if (weatherList[position].noonIcon != "null"){
            Glide.with(context)
                    .load(noonURL)
                    .into(holder.noonIcon)
        }

         Glide.with(context)
                .load(midnightURL)
                .into(holder.midnightIcon)


        holder.midnightDay.text = formatDate(weatherList[position].midnightDay)
        if (weatherList[position].noonTemp != "null"){
            holder.noonTemp.text = weatherList[position].noonTemp.toFloat().toInt().toString() + "°C"
        }
        holder.midnightTemp.text = weatherList[position].midnightTemp.toFloat().toInt().toString() + "°C"

    }

    private fun formatDate(date: String): String{
        Log.d(TAG, "formatDate")
        val weatherDate: Date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date)

        val tomorrowCalendar: Calendar = Calendar.getInstance()
        tomorrowCalendar.add(Calendar.DATE, 1)

        val weatherCalendar = Calendar.getInstance()
        weatherCalendar.time = weatherDate

        if(DateUtils.isToday(weatherDate.time)) {
            Log.d(TAG, "Today")
            return "Hoy"
        }

        if(tomorrowCalendar.get(Calendar.YEAR) == weatherCalendar.get(Calendar.YEAR) && tomorrowCalendar.get(Calendar.DAY_OF_YEAR) == weatherCalendar.get(Calendar.DAY_OF_YEAR)){
            Log.d(TAG, "Tomorrow")
            return "Mañana"
        }

        val formato = SimpleDateFormat("EEEE, dd/MM ", Locale("es", "AR"))
        val newDate = formato.format(weatherDate)
        return newDate.capitalize()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_layout, parent, false)
        val card: CardView = v.findViewById(R.id.card_view)
        val midnightDay: TextView = v.findViewById(R.id.midnightDay)
        val noonTemp: TextView = v.findViewById(R.id.noonTemp)
        val midnightTemp: TextView = v.findViewById(R.id.midnightTemp)

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

        return WeatherViewHolder(v)
    }



    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return weatherList.size
    }

    class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val midnightDay: TextView = itemView.findViewById(R.id.midnightDay)
        val noonTemp: TextView = itemView.findViewById(R.id.noonTemp)
        val noonIcon: ImageView = itemView.findViewById(R.id.noonIcon)
        val midnightTemp: TextView = itemView.findViewById(R.id.midnightTemp)
        val midnightIcon: ImageView = itemView.findViewById(R.id.midnightIcon)

    }

}