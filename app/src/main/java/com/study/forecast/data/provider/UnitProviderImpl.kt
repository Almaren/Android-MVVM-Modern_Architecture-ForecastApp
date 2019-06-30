package com.study.forecast.data.provider

import android.content.Context
import com.study.forecast.internal.UnitSystem

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedUnitName = prefs.getString("prefListUnitSystem", UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedUnitName)
    }

}