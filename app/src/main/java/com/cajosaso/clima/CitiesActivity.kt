package com.cajosaso.clima

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import org.json.JSONArray
import org.json.JSONObject

class CitiesActivity : AppCompatActivity() {

    private val TAG = "CitiesActivity"
    val citiesUpdater = CitiesUpdater(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        val path = "cities"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        Log.d(TAG, "onCreate")

        val rv = findViewById<RecyclerView>(R.id.cities_recycler_view)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        citiesUpdater.getCities()
    }
}

class CitiesUpdater constructor(anActivity: CitiesActivity) : RestUpdater{
    private val TAG = "CitiesUpdater"
    private val restConnection = RestConnection(this, "cities")
    private val activity: CitiesActivity = anActivity

    fun getCities(){
        restConnection.execute()
    }

    override fun executeUpdate(servResponse: JSONObject){
        val cities = ArrayList<City>()

        val rv = activity.findViewById<RecyclerView>(R.id.cities_recycler_view)
        rv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val obj = servResponse["data"] as JSONArray
        Log.d(TAG, "Obj: " + obj.toString())

        for (i in 0 until obj.length()){
            cities.add(City(obj.getString(i)))
        }

        var adapter = CitiesAdapter(activity, cities)
        rv.adapter = adapter
    }
}
