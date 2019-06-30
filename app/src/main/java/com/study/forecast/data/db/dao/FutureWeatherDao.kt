package com.study.forecast.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.study.forecast.data.db.entity.FutureWeatherEntry
import com.study.forecast.data.db.unitlocalized.future.detail.ImperialDetailFutureWeatherEntry
import com.study.forecast.data.db.unitlocalized.future.detail.MetricDetailFutureWeatherEntry
import com.study.forecast.data.db.unitlocalized.future.list.ImperialSimpleFutureWeatherEntry
import com.study.forecast.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 28-Jun-19.
 * @since 1.0.0
 */
@Dao
interface FutureWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("select * from FutureWeather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastsMetric(startDate: LocalDate): List<MetricSimpleFutureWeatherEntry>

    @Query("select * from FutureWeather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastsImperial(startDate: LocalDate): List<ImperialSimpleFutureWeatherEntry>

    @Query("select * from FutureWeather where date(date) = date(:date)")
    fun getDetailedWeatherByDateMetric(date: LocalDate): MetricDetailFutureWeatherEntry

    @Query("select * from FutureWeather where date(date) = date(:date)")
    fun getDetailedWeatherByDateImperial(date: LocalDate): ImperialDetailFutureWeatherEntry

    @Query("select count(id) from FutureWeather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from FutureWeather where date(date) < date(:firstDateToKeep)")
    fun removeOldEntries(firstDateToKeep: LocalDate)

}