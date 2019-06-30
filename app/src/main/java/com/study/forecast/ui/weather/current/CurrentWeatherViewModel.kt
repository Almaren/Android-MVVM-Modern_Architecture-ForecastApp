package com.study.forecast.ui.weather.current

import androidx.lifecycle.MutableLiveData
import com.almatime.connectmillion.android.utils.Logger
import com.study.forecast.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.study.forecast.data.provider.UnitProvider
import com.study.forecast.data.repo.ForecastRepo
import com.study.forecast.internal.UnitSystem
import com.study.forecast.ui.base.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentWeatherViewModel(
    forecastRepo: ForecastRepo,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepo, unitProvider) {

    val weatherData = MutableLiveData<UnitSpecificCurrentWeatherEntry>()

    fun loadCurrWeather() {
        Logger.i { "[CurrWeatherViewModel] loadCurrWeather before launch" }
        launch {
            val weatherEntry = forecastRepo.getCurrentWeather(unitSystem == UnitSystem.METRIC)
            withContext(Dispatchers.Main) {
                weatherData.value = weatherEntry
            }
            Logger.i { "[CurrWeatherViewModel] loadCurrWeather launch FINISHED" }
        }
    }

}
