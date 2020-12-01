package com.nguyen.sunshine2

import android.content.Context
import androidx.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.*

object Utility {
    fun getPreference(context: Context, resKey: Int, resDefaultValue: Int) : String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val locationKey = context.getString(resKey)
        val locationDefault = context.getString(resDefaultValue)
        return sharedPreferences.getString(locationKey, locationDefault)
    }

    fun getWeatherResource(weatherId: Int) : Int {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        return when (weatherId) {
            in 200..232 -> 200
            in 300..321 -> 300
            in 500..504 -> R.drawable.ic_rain
            511 -> R.drawable.ic_snow
            in 520..531 -> R.drawable.ic_rain
            in 600..622 -> R.drawable.ic_snow
            in 701..761 -> R.drawable.ic_fog
            761, 781 -> R.drawable.ic_storm
            800 -> R.drawable.ic_clear
            801 -> R.drawable.ic_light_clouds
            in 802..804 -> R.drawable.ic_cloudy
            else -> -1
        }
    }

    fun getDay(position: Int) : String {
        val calendar = GregorianCalendar()
        calendar.add(GregorianCalendar.DATE, position)
        val day = when (position) {
            0 -> "Today"
            1 -> "Tomorrow"
            else -> {
                val formatter = SimpleDateFormat("EEEE")
                formatter.format(calendar.time)
            }
        }
        val formatter = SimpleDateFormat("MMMM dd")
        val date = formatter.format(calendar.time)
        return "$day, $date"
    }
}