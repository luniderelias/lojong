package com.example.mvp_livedata_base_kotlin.base.extensions

import kotlinx.coroutines.*

fun launchIO(block: suspend CoroutineScope.() -> Unit): Job {
    return GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT, block)
}
fun launchMain(block: suspend CoroutineScope.() -> Unit): Job {
    return GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT, block)
}

fun CoroutineDispatcher.launchIO(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launchIO(block)
}

fun CoroutineDispatcher.launchMain(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launchMain(block)
}