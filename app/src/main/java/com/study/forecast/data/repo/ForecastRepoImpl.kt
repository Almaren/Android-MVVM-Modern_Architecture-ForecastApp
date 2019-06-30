package com.study.forecast.data.repo

import com.almatime.connectmillion.android.utils.Logger
import com.study.forecast.data.db.dao.CurrentWeatherDao
import com.study.forecast.data.db.dao.FutureWeatherDao
import com.study.forecast.data.db.dao.WeatherLocationDao
import com.study.forecast.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.study.forecast.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.study.forecast.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.study.forecast.data.network.WeatherNetworkDataSource
import com.study.forecast.data.network.response.CurrentWeatherResponse
import com.study.forecast.data.network.response.FutureWeatherResponse
import com.study.forecast.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepoImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepo {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                Logger.i { "[ForecastRepoImpl] downloadedCurrentWeather.observeForever" }
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                Logger.i { "[ForecastRepoImpl] downloadedFutureWeather.observeForever" }
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

    override suspend fun getCurrentWeather(isMetric: Boolean): UnitSpecificCurrentWeatherEntry {
        initWeatherData()
        return if (isMetric) currentWeatherDao.getWeatherMetric() else currentWeatherDao.getWeatherImperial()
    }

    override suspend fun getFutureWeatherList(
        isMetric: Boolean, startDate: LocalDate
    ): List<UnitSpecificSimpleFutureWeatherEntry> {
        initWeatherData()
        return if (isMetric) {
            futureWeatherDao.getSimpleWeatherForecastsMetric(startDate)
        } else {
            futureWeatherDao.getSimpleWeatherForecastsImperial(startDate)
        }
    }

    override suspend fun getFutureWeatherByDate(
        isMetric: Boolean, date: LocalDate
    ): UnitSpecificDetailFutureWeatherEntry {
        return if (isMetric) futureWeatherDao.getDetailedWeatherByDateMetric(date) else
            futureWeatherDao.getDetailedWeatherByDateImperial(date)
    }

    override suspend fun getWeatherLocation() = weatherLocationDao.getLocation()

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            futureWeatherDao.removeOldEntries(LocalDate.now())
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation()

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)) {
            fetchCurrentWeather()
        }
        if (isFetchFutureNeeded()) {
            fetchFutureWeather()
        }
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(),
            Locale.getDefault().language)
    }

    private suspend fun fetchFutureWeather() {
        weatherNetworkDataSource.fetchFutureWeather(locationProvider.getPreferredLocationString(), 7,
            Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastFetchData: ZonedDateTime): Boolean {
        val thirtyMinAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchData.isBefore(thirtyMinAgo)
    }

    private fun isFetchFutureNeeded(): Boolean {
        val futureWeatherCount = futureWeatherDao.countFutureWeather(LocalDate.now())
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }

}