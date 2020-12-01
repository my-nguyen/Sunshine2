package com.nguyen.sunshine2

import java.io.Serializable

data class Record(val city: City, val list: List<Day>)

data class City(val name: String, val country: String)

data class Day(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temperature,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>,
    val speed: Float
) : Serializable

data class Temperature(
    val day: Float,
    val min: Float,
    val max: Float,
    val night: Float,
    val eve: Float,
    val morn: Float
) : Serializable

data class Weather(val id: Int, val main: String, val description: String) : Serializable