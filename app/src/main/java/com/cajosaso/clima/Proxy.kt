package com.cajosaso.clima

class Proxy {
    val service = ServiceVolley()
    val apiController = APIController(service)

    /*fun getCities(): ArrayList<City> {
        val path = "cities"
        apiController.get(path) { response ->

        }
    }

    fun getForecast(city: String) {
        val path = "weather/" + city.replace(" ", "%20") + "/forecast"
        apiController.get(path) { response ->

        }
    }*/
}