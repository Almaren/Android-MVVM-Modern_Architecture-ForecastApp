package com.almatime.connectmillion.android.utils

import android.util.Log

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 03-Jun-19.
 * @since 1.0.0
 */
object Logger {

    val DEBUG = true
    val TAG = "app"

    inline fun i(msg: () -> String) {
        if (DEBUG) Log.i(TAG, msg())
    }

    inline fun d(msg: () -> String) {
        if (DEBUG) Log.d(TAG, msg())
    }

    inline fun e(e: Throwable) {
        if (DEBUG) Log.e(TAG, null, e)
    }

}