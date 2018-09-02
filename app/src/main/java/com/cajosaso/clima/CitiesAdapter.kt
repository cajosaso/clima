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



class CitiesAdapter(val context: Context, val cityList: ArrayList<City>): RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private val TAG = "CitiesAdapter"

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        holder?.name?.text = cityList[position].name
        holder?.parentLayout.setOnClickListener { view ->
            Log.d(TAG, "onClick: clicked on: " + cityList[position].name);
            Toast.makeText(context, cityList[position].name, Toast.LENGTH_SHORT).show()

            val intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra("name", cityList[position].name)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v);
    }



    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return cityList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.name)
        val parentLayout = itemView.findViewById<LinearLayout>(R.id.parent_layout)

    }

}