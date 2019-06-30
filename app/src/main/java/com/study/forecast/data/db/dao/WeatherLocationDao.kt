package com.study.forecast.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.study.forecast.data.db.entity.CURRENT_LOCATION_ID
import com.study.forecast.data.db.entity.WeatherLocation

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 27-Jun-19.
 * @since 1.0.0
 */
@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("select * from WeatherLocation where id = $CURRENT_LOCATION_ID")
    fun getLocation(): WeatherLocation?

}