package com.study.forecast.ui.weather.future.list

import androidx.lifecycle.MutableLiveData
import com.almatime.connectmillion.android.utils.Logger
import com.study.forecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.study.forecast.data.provider.UnitProvider
import com.study.forecast.data.repo.ForecastRepo
import com.study.forecast.internal.UnitSystem
import com.study.forecast.ui.base.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    forecastRepo: ForecastRepo,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepo, unitProvider) {

    val weatherEntriesData = MutableLiveData<List<UnitSpecificSimpleFutureWeatherEntry>>()

    fun loadFutureWeather() {
        Logger.i { "[FutureListWeatherViewModel] loadFutureWeather before launch" }
        launch {
            val entries = forecastRepo.getFutureWeatherList(unitSystem == UnitSystem.METRIC, LocalDate.now())
            withContext(Dispatchers.Main) {
                weatherEntriesData.value = entries
            }
            Logger.i { "[FutureListWeatherViewModel] loadFutureWeather FINISHED" }
        }
    }


}
