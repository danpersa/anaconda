package com.danix.anaconda.controller

import rx.Observable
import java.util.concurrent.CompletableFuture

fun <T> Observable<T>.toFuture(): CompletableFuture<T> {
    val future = CompletableFuture<T>()
    this
            .doOnError { future.completeExceptionally(it) }
            .single()
            .forEach { future.complete(it) }
    return future
}
