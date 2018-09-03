package com.cajosaso.clima

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cities.*
import kotlinx.android.synthetic.main.activity_weather.*
import org.json.JSONObject
import java.util.*

class WeatherActivity : AppCompatActivity() {

    private val TAG = "WeatherActivity"

    val weatherWeek = ArrayList<Weather>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        Log.d(TAG, "onCreate")

        val preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val cityName = preferences.getString("city", getString(R.string.weather_activity))

        setSupportActionBar(toolbar)
        supportActionBar?.title = cityName



        fab.setOnClickListener { view ->
            loadData("No fue posible conectarse al servidor, por favor intente más tarde")

        }
        loadData("No fue posible conectarse al servidor, por favor reintente más tarde")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu")
        menuInflater.inflate(R.menu.menu_change_city, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemSelected")
        return when (item.itemId) {
            R.id.action_change_city -> {
                Log.d(TAG, "onOptionsItemSelected: pressed action_change_city")

                val path = "cities"

                val service = ServiceVolley()
                val apiController = APIController(service)

                apiController.get(path) { response ->
                    Log.d(TAG, response.toString())

                    if (response != null) {
                        Log.d(TAG, "startActivity: CitiesActivity");
                        val intent = Intent(this, CitiesActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "No fue posible conectarse al servidor, por favor intente más tarde", Toast.LENGTH_SHORT).show()
                    }

                }
                return super.onOptionsItemSelected(item)
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadData(toastMessage: String) {
        Log.d(TAG, "loadData")

        val preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val cityName = preferences.getString("city", getString(R.string.weather_activity))
        val path = "weather/" + cityName.replace(" ", "%20") + "/forecast"

        val service = ServiceVolley()
        val apiController = APIController(service)

        apiController.get(path) { response ->
            Log.d(TAG, response.toString())

            if (response != null) {
                weatherWeek.clear()
                val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                val img = findViewById<ImageView>(R.id.background)
                var day = true

                if ((currentHour >= 6) and (currentHour < 20)) {
                    img.setImageResource(R.drawable.dia)

                } else {
                    day = false
                    img.setImageResource(R.drawable.noche)
                }

                val jSONForecast = response.getJSONArray("forecast")
                for (i in 0 until jSONForecast.length()) {
                    val midnightDay= jSONForecast.getJSONObject(i).getString("midnightDay")
                    val noonTemp= jSONForecast.getJSONObject(i).getString("noonTemp")
                    val noonIcon= jSONForecast.getJSONObject(i).getString("noonIcon")
                    val midnightTemp= jSONForecast.getJSONObject(i).getString("midnightTemp")
                    val midnightIcon= jSONForecast.getJSONObject(i).getString("midnightIcon")
                    weatherWeek.add(Weather(midnightDay, noonTemp, noonIcon, midnightTemp, midnightIcon))
                }

                weatherWeek.sort()

                weather_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                weather_recycler_view.adapter = WeatherAdapter(this, weatherWeek, day)

            } else {
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
