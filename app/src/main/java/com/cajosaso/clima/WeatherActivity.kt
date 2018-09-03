package com.cajosaso.clima

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    private val TAG = "WeatherActivity"

    val weatherWeek = ArrayList<Weather>()

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

        fab.setOnClickListener { view ->
            val img = findViewById<ImageView>(R.id.background)
            img.setImageResource(R.drawable.dia)
        }

        loadData()

        weather_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        weather_recycler_view.adapter = WeatherAdapter(this, weatherWeek)

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

    private fun loadData(){
        Log.d(TAG, "loadData")

        //Como puede suceder que no se reciba datos del noonDay (porque es de noche), hay que hacer
        //que si se recibe noonDay == null, ponga noonTemp = midnightTemp y noonIcon = midnightIcon

        weatherWeek.add(Weather("2018-09-01","12.31", "10d","11.50","10d" ))
        weatherWeek.add(Weather("2018-09-02","13.31", "10d","12.5","10d" ))
        weatherWeek.add(Weather("2018-09-03","11.31", "10d","16.50","10d" ))
        weatherWeek.add(Weather("2018-09-04","5.31", "10d","0","10d" ))
        weatherWeek.add(Weather("2018-09-05","11.3", "10d","-5.5","10d" ))

        weatherWeek.sort()

    }

}
