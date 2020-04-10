package com.example.mvp_livedata_base_kotlin.base.extensions

import kotlinx.coroutines.*

fun launchIO(block: suspend CoroutineScope.() -> Unit): Job {
    return GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT, block)
}