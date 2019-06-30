package com.study.forecast.utils

import kotlinx.coroutines.*

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 23-Jun-19.
 * @since 1.0.0
 */

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}