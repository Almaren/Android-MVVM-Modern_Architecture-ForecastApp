package com.study.forecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.study.forecast.data.db.dao.CurrentWeatherDao
import com.study.forecast.data.db.dao.FutureWeatherDao
import com.study.forecast.data.db.dao.WeatherLocationDao
import com.study.forecast.data.db.entity.CurrentWeatherEntry
import com.study.forecast.data.db.entity.FutureWeatherEntry
import com.study.forecast.data.db.entity.WeatherLocation

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 21-Jun-19.
 * @since 1.0.0
 */
@Database(entities = [CurrentWeatherEntry::class, WeatherLocation::class, FutureWeatherEntry::class], version = 6)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao
    abstract fun futureWeatherDao(): FutureWeatherDao

    companion object {
        @Volatile
        private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(ctx: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(ctx)
        }

        private fun buildDatabase(ctx: Context) =
            Room.databaseBuilder(ctx.applicationContext, ForecastDatabase::class.java, "forecast.db")
                .fallbackToDestructiveMigration().build()
    }

}