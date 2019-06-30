package com.study.forecast.ui.base

import androidx.lifecycle.ViewModel
import com.almatime.connectmillion.android.utils.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 29-Jun-19.
 * @since 1.0.0
 */
abstract class ScopedViewModel : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    private val loggingException = CoroutineExceptionHandler { _, throwable ->
        Logger.e(throwable)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job + loggingException

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}