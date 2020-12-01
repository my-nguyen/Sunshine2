package com.nguyen.sunshine2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlin.math.roundToInt

class DayAdapter(val context: Context, val days: List<Day>) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    companion object {
        const val EXTRA_DAY = "EXTRA_DAY"
        const val EXTRA_POSITION = "EXTRA_POSITION"
        const val VIEW_TODAY = 0
        const val VIEW_FUTURE = 1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val day = days[adapterPosition]
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_DAY, day)
                    intent.putExtra(EXTRA_POSITION, adapterPosition)
                    context.startActivity(intent)
                }
            }
        }

        fun bind(day: Day) {
            val text = Utility.getDay(adapterPosition)
            val jour = when (adapterPosition) {
                0 -> text
                else -> text.split(",")[0]
            }
            itemView.text_day.text = jour
            val resource = Utility.getWeatherResource(day.weather[0].id)
            itemView.image.setImageResource(resource)
            itemView.text_forecast.text = day.weather[0].main
            itemView.text_high.text = "${day.temp.max.roundToInt().toString()}\u00B0"
            itemView.text_low.text = "${day.temp.min.roundToInt().toString()}\u00B0"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val layout = when (viewType) {
            VIEW_TODAY -> R.layout.item_today
            else -> R.layout.item_future
        }
        val view = inflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day)
    }

    override fun getItemCount() = days.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TODAY else VIEW_FUTURE
    }
}