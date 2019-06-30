package com.study.forecast.data.repo

import com.study.forecast.data.db.entity.WeatherLocation
import com.study.forecast.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.study.forecast.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.study.forecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 22-Jun-19.
 * @since 1.0.0
 */
interface ForecastRepo {

    val FORECAST_DAYS_COUNT: Int
        get() = 7

    suspend fun getCurrentWeather(isMetric: Boolean): UnitSpecificCurrentWeatherEntry
    suspend fun getFutureWeatherList(isMetric: Boolean, startDate: LocalDate): List<UnitSpecificSimpleFutureWeatherEntry>
    suspend fun getFutureWeatherByDate(isMetric: Boolean, date: LocalDate): UnitSpecificDetailFutureWeatherEntry
    suspend fun getWeatherLocation(): WeatherLocation?

}