package com.cajosaso.clima

data class City(val name: String): Comparable<City> {
    override operator fun compareTo(other: City): Int {
        return this.name.compareTo(other.name)
    }
}