package com.cajosaso.clima

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

class WeatherActivity : AppCompatActivity() {

    private val TAG = "WeatherActivity"

    val weatherWeek = ArrayList<Weather>()

    var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        val preferences = this.getPreferences(0)
        val initialCity = preferences.getString("city", getString(R.string.weather_activity))
        val intent = intent
        val cityName = intent.getStringExtra("name") ?: initialCity

        setContentView(R.layout.activity_weather)
        setSupportActionBar(toolbar)
        supportActionBar?.title = cityName

        var edit = preferences.edit()
        edit.putString("city", cityName)
        edit.apply()

        path = "weather/" + cityName.replace(" ", "%20") + "/forecast"

        fab.setOnClickListener { view ->
            loadData(path, "No fue posible conectarse al servidor, por favor intente más tarde")

        }

        loadData(path, "No fue posible conectarse al servidor, por favor reintente más tarde")
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
                val intent = Intent(this, CitiesActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadData(path: String, toastMessage: String) {
        Log.d(TAG, "loadData")

        Log.d(TAG, "Path: " + path)

        //Como puede suceder que no se reciba datos del noonDay (porque es de noche), hay que hacer
        //que si se recibe noonDay == null, ponga noonTemp = midnightTemp y noonIcon = midnightIcon

        val service = ServiceVolley()
        val apiController = APIController(service)

        apiController.get(path) { response ->
            Log.d(TAG, response.toString())

            if (response != null) {
                weatherWeek.clear()
                val currentHour = response.getString("now").substring(0, 2).toInt()
                val img = findViewById<ImageView>(R.id.background)
                var day = true

                if ((currentHour >= 6) and (currentHour < 20)) {
                    img.setImageResource(R.drawable.dia)

                }
                else {
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

            }

            else {
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
            }

        }

    }
}
