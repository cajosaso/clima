package com.cajosaso.clima

data class Weather(
        val midnightDay: String,
        val noonTemp: String,
        val noonIcon: String,
        val midnightTemp: String,
        val midnightIcon: String
): Comparable<Weather> {
    override operator fun compareTo(other: Weather): Int {
        return this.midnightDay.compareTo(other.midnightDay)
    }
}