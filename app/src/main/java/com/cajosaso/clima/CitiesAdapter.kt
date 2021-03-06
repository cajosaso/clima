package com.cajosaso.clima

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Intent
import android.content.SharedPreferences
import android.widget.LinearLayout
import android.widget.Toast

class CitiesAdapter(val context: Context, val cityList: ArrayList<City>, val preferences: SharedPreferences): RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

    private val TAG: String = CitiesAdapter::class.java.simpleName

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        holder.name.text = cityList[position].name
        holder.parentLayout.setOnClickListener {
            Log.d(TAG, "onClick: clicked on: " + cityList[position].name)


            val service = ServiceVolley()
            val apiController = APIController(service)

            val path = "weather/" + cityList[position].name.replace(" ", "%20") + "/forecast"

            apiController.get(path) { response ->
                Log.d(TAG, response.toString())

                if (response != null) {
                    Log.d(TAG, "startActivity: WeatherActivity, City: " + cityList[position].name)

                    val editPreferences = preferences.edit()
                    editPreferences.putString("city", cityList[position].name)
                    editPreferences.apply()

                    val intent = Intent(context, WeatherActivity::class.java)
                    context.startActivity(intent)
                }

                else {
                    Toast.makeText(context, "No fue posible conectarse al servidor, por favor intente más tarde", Toast.LENGTH_SHORT).show()
                }

            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.city_item_layout, parent, false)
        return CitiesViewHolder(v)
    }



    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return cityList.size
    }

    class CitiesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.name)!!
        val parentLayout = itemView.findViewById<LinearLayout>(R.id.city_parent_layout)!!

    }

}