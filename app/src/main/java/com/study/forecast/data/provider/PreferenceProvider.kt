package com.study.forecast.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 27-Jun-19.
 * @since 1.0.0
 */
abstract class PreferenceProvider(context: Context) {

    protected val appContext = context.applicationContext

    protected val prefs: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

}