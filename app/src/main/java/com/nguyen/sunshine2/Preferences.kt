package com.nguyen.sunshine2

import android.content.Context
import androidx.preference.PreferenceManager

object Preferences {
    fun getString(context: Context, resKey: Int, resDefaultValue: Int) : String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val locationKey = context.getString(resKey)
        val locationDefault = context.getString(resDefaultValue)
        return sharedPreferences.getString(locationKey, locationDefault)
    }
}