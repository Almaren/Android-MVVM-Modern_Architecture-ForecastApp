package com.study.forecast.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.study.forecast.data.db.entity.WeatherLocation
import com.study.forecast.internal.LocationPermissionNotGrantedException
import com.study.forecast.utils.asDeferred
import kotlinx.coroutines.Deferred

const val KEY_USE_DEVICE_LOCATION = "prefUseLocation"
const val KEY_CUSTOM_LOCATION = "prefCustomLocation"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }
        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String {
        return if (isUsingDeviceLocation()) {
            try {
                val location = getLastDeviceLocation().await() ?: return "${getCustomLocationName()}"
                with (location) {
                    "${latitude},${longitude}"
                }
            } catch (e: LocationPermissionNotGrantedException) {
                "${getCustomLocationName()}"
            }
        } else {
            "${getCustomLocationName()}"
        }
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation()) return false

        val location = getLastDeviceLocation().await() ?: return false

        val comparisonThreshold = 0.03
        return Math.abs(location.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(location.longitude - lastWeatherLocation.lon) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun isUsingDeviceLocation() = prefs.getBoolean(KEY_USE_DEVICE_LOCATION, true)

    private fun getCustomLocationName() = prefs.getString(KEY_CUSTOM_LOCATION, null)

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

}