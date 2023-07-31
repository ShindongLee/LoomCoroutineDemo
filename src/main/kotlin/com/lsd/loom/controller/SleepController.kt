package com.lsd.loom.controller

import com.lsd.loom.sleeper.CoroutineSleeper
import com.lsd.loom.sleeper.ThreadSleeper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * You can make requests to this controller
 * for a comparison between platform threads and virtual threads,
 * and between Dispatchers.IO and virtual thread per task dispatcher.
 */
@RestController
class SleepController(
    private val threadSleeper: ThreadSleeper,
    private val coroutineSleeper: CoroutineSleeper,
) {
    @PostMapping("/api/sleep-by-platform-threads")
    fun sleepByPlatformThreads(
        @RequestBody request: SleepRequest,
    ): SleepResponse {
        return threadSleeper.sleepByPlatformThreads(request.threadCount)
            .let(SleepResponse::from)
    }

    @PostMapping("/api/sleep-by-virtual-threads")
    fun sleepByVirtualThreads(
        @RequestBody request: SleepRequest,
    ): SleepResponse {
        return threadSleeper.sleepByVirtualThreads(request.threadCount)
            .let(SleepResponse::from)
    }

    @PostMapping("/api/sleep-by-io-dispatcher")
    fun sleepByIoDispatcher(
        @RequestBody request: DispatcherSleepRequest,
    ): SleepResponse {
        return coroutineSleeper.sleepByIoDispatcher(request.sleepCount, request.isBlocking)
            .let(SleepResponse::from)
    }

    @PostMapping("/api/sleep-by-loom-dispatcher")
    fun sleepByLoomDispatcher(
        @RequestBody request: DispatcherSleepRequest,
    ): SleepResponse {
        return coroutineSleeper.sleepByLoomDispatcher(request.sleepCount, request.isBlocking)
            .let(SleepResponse::from)
    }
}
