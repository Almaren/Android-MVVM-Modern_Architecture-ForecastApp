package com.study.forecast.utils

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.study.forecast.data.db.ForecastDatabase
import com.study.forecast.data.network.*
import com.study.forecast.data.provider.LocationProvider
import com.study.forecast.data.provider.LocationProviderImpl
import com.study.forecast.data.provider.UnitProvider
import com.study.forecast.data.provider.UnitProviderImpl
import com.study.forecast.data.repo.ForecastRepo
import com.study.forecast.data.repo.ForecastRepoImpl
import com.study.forecast.ui.weather.current.CurrentWeatherViewModel
import com.study.forecast.ui.weather.future.detail.FutureDetailWeatherViewModel
import com.study.forecast.ui.weather.future.list.FutureListWeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 23-Jun-19.
 * @since 1.0.0
 */
private val dbModule = module {

    single { ForecastDatabase(androidApplication()) }
    single { get<ForecastDatabase>().currentWeatherDao() }
    single { get<ForecastDatabase>().weatherLocationDao() }
    single { get<ForecastDatabase>().futureWeatherDao() }

}

private val commonModule = module {

    single<UnitProvider> { UnitProviderImpl(androidContext()) }
    single<LocationProvider> { LocationProviderImpl(get(), androidContext()) }

}

private val serviceModule = module {

    single<ConnectivityInterceptor> { ConnectivityInterceptorImpl(androidApplication()) }
    single { ApixuWeatherApiService(get()) }
    single<WeatherNetworkDataSource> { WeatherNetworkDataSourceImpl(get()) }

    single<ForecastRepo> { ForecastRepoImpl(get(), get(), get(), get(), get()) }

    factory<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(androidContext()) }

}

private val viewModelModule = module {

    viewModel { CurrentWeatherViewModel(get(), get()) }
    viewModel { FutureListWeatherViewModel(get(), get()) }
    viewModel { FutureDetailWeatherViewModel(get(), get()) }

}

val appKoinModules = listOf(dbModule, commonModule, serviceModule, viewModelModule)