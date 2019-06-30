package com.study.forecast.base

import android.app.Application
import android.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.study.forecast.R
import com.study.forecast.utils.appKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 22-Jun-19.
 * @since 1.0.0
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication) // should it be NOT used?
            modules(appKoinModules)
        }
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

}