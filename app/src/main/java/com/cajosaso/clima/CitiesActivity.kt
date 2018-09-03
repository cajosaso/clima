package com.cajosaso.clima

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_cities.*
import kotlinx.android.synthetic.main.city_item_layout.view.*

class CitiesActivity : AppCompatActivity() {

    private val TAG = "CitiesActivity"
    val cities = ArrayList<City>()
    val displayedCities = ArrayList<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        Log.d(TAG, "onCreate")

        loadData()

        cities_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        cities_recycler_view.adapter = CitiesAdapter(this, displayedCities)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu")
        menuInflater.inflate(R.menu.menu_search_cities,menu)
        val searchItem = menu.findItem(R.id.search_cities)
        if(searchItem != null){
            Log.d(TAG, "searchItem != null")
            val searchView = searchItem.actionView as SearchView
            val editext = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            editext.hint = "Search here..."

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG, "onQueryTextSubmit")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "onQueryTextChange")
                    displayedCities.clear()
                    if(newText!!.isNotEmpty()){
                        Log.d(TAG, "newText!!.isNotEmpty()")
                        val search = newText.toLowerCase()
                        cities.forEach {
                            Log.d(TAG, "cities.forEach")
                            if(it.name.toLowerCase().contains(search)){
                                Log.d(TAG, "it.name.toLowerCase().contains(search)")
                                displayedCities.add(it)
                            }
                        }
                    }else{
                        displayedCities.addAll(cities)
                    }
                    cities_recycler_view.adapter.notifyDataSetChanged()
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun loadData(){
        Log.d(TAG, "loadData")

        cities.add(City("Salta"))
        cities.add(City("Buenos Aires"))
        cities.add(City("Mendoza"))
        cities.add(City("San Martin de los Andes"))

        cities.sort()

        displayedCities.addAll(cities)
    }
}
