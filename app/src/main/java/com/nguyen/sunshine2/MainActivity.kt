package com.nguyen.sunshine2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/forecast/"
        private const val API_KEY = "a0e4b2727858f8dc3bdc0428ef7e3712"
        private const val NUM_DAYS = 16
        private const val MODE_JSON = "json"
        private const val MODE_XML = "xml"
        private const val UNIT_STANDARD = "standard"
        private const val UNIT_METRIC = "metric"
        private const val UNIT_IMPERIAL = "imperial"
    }

    private lateinit var service: WeatherService
    private lateinit var days: MutableList<Day>
    private lateinit var adapter: DayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = GsonConverterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .build()
        service = retrofit.create(WeatherService::class.java)

        days = mutableListOf<Day>()
        adapter = DayAdapter(this, days)
        recycler_forecast.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recycler_forecast.layoutManager = layoutManager
        val divider = DividerItemDecoration(recycler_forecast.context, layoutManager.orientation)
        recycler_forecast.addItemDecoration(divider)

        fetchWeather("95131")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                /*val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                val locationKey = getString(R.string.pref_location_key)
                val locationDefault = getString(R.string.pref_location_default)
                val location = sharedPreferences.getString(locationKey, locationDefault)?.toInt()*/
                val location = Preferences.getString(this, R.string.pref_location_key, R.string.pref_location_default)
                if (location != null) {
                    fetchWeather(location)
                }
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.action_map -> {
                val location = Preferences.getString(this, R.string.pref_location_key, R.string.pref_location_default)
                val geo = Uri.parse("geo:0,0?")
                    .buildUpon()
                    .appendQueryParameter("q", location)
                    .build()
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = geo
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Log.w(TAG, "Couldn't call $location no receiving apps installed.")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchWeather(zipcode: String) {
        service.fetchWeather(zipcode, UNIT_IMPERIAL, NUM_DAYS, API_KEY).enqueue(object: Callback<Record> {
            override fun onResponse(call: Call<Record>, response: Response<Record>) {
                Log.i(TAG, "onResponse $response")
                if (response.body() == null) {
                    Log.w(TAG, "Did not receive valid response body from OpenWeatherMap API")
                } else {
                    days.clear()
                    days.addAll(response.body()!!.list)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Record>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
            }
        })
    }
}