package com.cajosaso.clima

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout

class CitiesActivity : AppCompatActivity() {

    private val TAG = "CitiesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        Log.d(TAG, "onCreate")

        val rv = findViewById<RecyclerView>(R.id.cities_recycler_view)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val cities = ArrayList<City>()
        cities.add(City("Salta"))
        cities.add(City("Buenos Aires"))
        cities.add(City("Mendoza"))
        cities.add(City("San Martin de los Andes"))

        var adapter = CitiesAdapter(this, cities)
        rv.adapter = adapter

    }
}
