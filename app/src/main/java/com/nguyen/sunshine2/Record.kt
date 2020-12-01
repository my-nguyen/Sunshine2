package com.nguyen.sunshine2

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

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
) : Serializable {
    fun toString(position: Int) : String {
        val calendar = GregorianCalendar()
        calendar.add(GregorianCalendar.DATE, position)
        val formatter = SimpleDateFormat("EEE, MMM dd")
        val string1 = formatter.format(calendar.time)
        val string2 = weather[0].description
        val string3 = "${temp.max.roundToInt()}/${temp.min.roundToInt()}"
        return "$string1 - $string2 - $string3"
    }

    private fun format(temperature: Double, unit: String, imperial: String) : Int {
        // val unit = Preferences.getString(context, R.string.pref_units_key, R.string.pref_units_label_metric)
        val converted = when {
            (unit == imperial) -> (temperature * 1.8) + 32
            else -> temperature
        }
        return converted.roundToInt()
    }
}

data class Temperature(
    val day: Float,
    val min: Float,
    val max: Float,
    val night: Float,
    val eve: Float,
    val morn: Float
) : Serializable

data class Weather(val id: Int, val main: String, val description: String) : Serializable