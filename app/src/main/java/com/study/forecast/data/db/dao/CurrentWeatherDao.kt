package com.study.forecast.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.study.forecast.data.db.entity.CURRENT_WEATHER_ID
import com.study.forecast.data.db.entity.CurrentWeatherEntry
import com.study.forecast.data.db.unitlocalized.current.ImperialCurrentWeatherEntry
import com.study.forecast.data.db.unitlocalized.current.MetricCurrentWeatherEntry

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 21-Jun-19.
 * @since 1.0.0
 */
@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from currentweather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): MetricCurrentWeatherEntry

    @Query("select * from currentweather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): ImperialCurrentWeatherEntry

}