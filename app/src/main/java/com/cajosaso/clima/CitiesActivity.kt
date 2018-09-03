package com.cajosaso.clima

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.widget.EditText
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_cities.*

class CitiesActivity : AppCompatActivity() {

    private val TAG: String = CitiesActivity::class.java.simpleName
    val cities = ArrayList<City>()
    val displayedCities = ArrayList<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        Log.d(TAG, "onCreate")

        cities_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        cities_recycler_view.adapter = CitiesAdapter(this, displayedCities, getSharedPreferences("my_preferences", Context.MODE_PRIVATE))

        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu")
        menuInflater.inflate(R.menu.menu_search_cities, menu)
        val searchItem = menu.findItem(R.id.search_cities)
        if (searchItem != null) {
            Log.d(TAG, "searchItem != null")
            val searchView = searchItem.actionView as SearchView
            val editext = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            editext.hint = "Buscar ciudad..."

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG, "onQueryTextSubmit")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "onQueryTextChange")
                    displayedCities.clear()
                    if (newText!!.isNotEmpty()) {
                        Log.d(TAG, "newText!!.isNotEmpty()")
                        val search = newText.toLowerCase()
                        cities.forEach {
                            Log.d(TAG, "cities.forEach")
                            if (it.name.toLowerCase().contains(search)) {
                                Log.d(TAG, "it.name.toLowerCase().contains(search)")
                                displayedCities.add(it)
                            }
                        }
                    } else {
                        displayedCities.addAll(cities)
                    }
                    cities_recycler_view.adapter.notifyDataSetChanged()
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun loadData() {
        Log.d(TAG, "loadData")

        val service = ServiceVolley()
        val apiController = APIController(service)

        val path = "cities"

        apiController.get(path) { response ->
            Log.d(TAG, response.toString())
            if (response != null) {
                val jSONCities = response.getJSONArray("data")
                for (i in 0 until jSONCities.length()) {
                    cities.add(City(jSONCities.getString(i)))
                }
                cities.sort()

                displayedCities.addAll(cities)

                cities_recycler_view.adapter.notifyDataSetChanged()
            }

        }
    }
}
