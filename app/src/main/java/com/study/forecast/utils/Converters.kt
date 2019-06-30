package com.study.forecast.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 27-Jun-19.
 * @since 1.0.0
 */

fun <T> Task<T>.asDeferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    addOnSuccessListener { result ->
        deferred.complete(result)
    }
    addOnFailureListener {
        deferred.completeExceptionally(it)
    }
    return deferred
}