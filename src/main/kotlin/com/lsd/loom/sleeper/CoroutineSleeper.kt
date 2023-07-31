package com.lsd.loom.sleeper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.util.concurrent.Executors
import kotlin.time.Duration
import kotlin.time.TimeSource

@Service
class CoroutineSleeper {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val ioDispatcher = Dispatchers.IO.limitedParallelism(10_000_000)
    private val loomDispatcher = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()

    fun sleepByIoDispatcher(sleepCount: Int, isBlocking: Boolean): Duration {
        val mark = TimeSource.Monotonic.markNow()
        runBlocking {
            repeat(sleepCount) {
                launch(ioDispatcher) { sleep(isBlocking) }
            }
        }
        return mark.elapsedNow()
    }

    fun sleepByLoomDispatcher(sleepCount: Int, isBlocking: Boolean): Duration {
        val mark = TimeSource.Monotonic.markNow()
        runBlocking {
            repeat(sleepCount) {
                launch(loomDispatcher) { sleep(isBlocking) }
            }
        }
        return mark.elapsedNow()
    }

    private suspend fun sleep(isBlocking: Boolean) {
        if (isBlocking)
            Thread.sleep(1000L)
        else
            delay(1000L)
    }
}
