package com.lsd.loom.controller

import com.lsd.loom.sleeper.ThreadSleeper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * You can make requests to this controller
 * for a comparison between platform threads and virtual threads.
 */
@RestController
class SleepController(
    private val sleeper: ThreadSleeper,
) {
    @PostMapping("/api/sleep-by-platform-threads")
    fun sleepByPlatformThreads(
        @RequestBody request: SleepRequest,
    ): SleepResponse {
        return sleeper.sleepByPlatformThreads(request.threadCount)
            .inWholeMilliseconds
            .let(::SleepResponse)
    }

    @PostMapping("/api/sleep-by-virtual-threads")
    fun sleepByVirtualThreads(
        @RequestBody request: SleepRequest,
    ): SleepResponse {
        return sleeper.sleepByVirtualThreads(request.threadCount)
            .inWholeMilliseconds
            .let(::SleepResponse)
    }
}
