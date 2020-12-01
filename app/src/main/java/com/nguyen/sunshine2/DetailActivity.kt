package com.nguyen.sunshine2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import com.nguyen.sunshine2.DayAdapter.Companion.EXTRA_DAY
import com.nguyen.sunshine2.DayAdapter.Companion.EXTRA_POSITION
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.math.roundToInt

class DetailActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "DetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val position = intent.getIntExtra(EXTRA_POSITION, 0)
        val day = intent.getSerializableExtra(EXTRA_DAY) as Day

        val text = Utility.getDay(position).split(",")
        text_day.text = text[0]
        text_date.text = text[1]
        text_high.text = "${day.temp.max.roundToInt().toString()}\u00B0"
        text_low.text = "${day.temp.min.roundToInt().toString()}\u00B0"
        val resource = Utility.getWeatherResource(day.weather[0].id)
        image.setImageResource(resource)
        text_forecast.text = day.weather[0].main
        text_humidity.text = "Humidity: ${day.humidity.toString()} %"
        text_pressure.text = "Pressure: ${day.pressure.toString()} hPa"
        text_wind.text = "Wind: ${day.speed.roundToInt().toString()} km/h NW"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.action_map -> {
                val provider = MenuItemCompat.getActionProvider(item) as ShareActionProvider
                if (provider != null) {
                    provider.setShareIntent(doShare())
                } else {
                    Log.w(TAG, "Share Action Provider is null")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun doShare() : Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        intent.type = "text/plain"
        // intent.putExtra(Intent.EXTRA_TEXT, forecast)
        return intent
    }
}