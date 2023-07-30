package com.lsd.loom.controller

import kotlin.time.Duration

data class SleepResponse(
    val durationInMs: Long,
) {
    companion object {
        fun from(duration: Duration): SleepResponse = SleepResponse(duration.inWholeMilliseconds)
    }
}
