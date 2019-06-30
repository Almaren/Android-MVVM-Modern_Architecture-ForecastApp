package com.study.forecast.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.almatime.connectmillion.android.utils.Logger
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.study.forecast.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.koin.android.ext.android.inject

private const val REQ_CODE_PERM_ACCESS_COARSE_LOCATION = 1

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val fusedLocationProviderClient: FusedLocationProviderClient by inject()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            Logger.i { "[MainActivity] onLocationResult" }
            super.onLocationResult(p0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i { "[MainActivity] onCreate" }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.navHostFragment)
        bottomNav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        requestLocationPermission()

        if (hasLocationPermission()) {
            bindLocationManager()
        }
    }

    /**
     * TODO how to implement it with DI
     */
    private fun bindLocationManager() {
        LifecycleBoundLocationManager(this, fusedLocationProviderClient, locationCallback)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null);
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQ_CODE_PERM_ACCESS_COARSE_LOCATION)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            bindLocationManager()
        } else {
            longToast("Please set locationData manually in settings");
        }
    }

    override fun onResume() {
        super.onResume()
        Logger.i { "[MainActivity] onResume" }
    }

    override fun onPause() {
        super.onPause()
        Logger.i { "[MainActivity] onPause" }
    }

    override fun onStop() {
        super.onStop()
        Logger.i { "[MainActivity] onStop" }
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i { "[MainActivity] onDestroy" }
    }

}
