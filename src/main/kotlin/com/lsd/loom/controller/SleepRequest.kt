package com.lsd.loom.controller

data class SleepRequest(
    val sleepCount: Int,
)

data class DispatcherSleepRequest(
    val sleepCount: Int,
    val isBlocking: Boolean,
)
