package com.lsd.loom.sleeper

import org.springframework.stereotype.Component
import kotlin.time.Duration
import kotlin.time.TimeSource

@Component
class ThreadSleeper {
    fun sleepByPlatformThreads(threadCount: Int): Duration {
        val threads = List(threadCount) {
            Thread.ofPlatform().unstarted { sleep() }
        }
        return threads.execute()
    }

    fun sleepByVirtualThreads(threadCount: Int): Duration {
        val threads = List(threadCount) {
            Thread.ofVirtual().unstarted { sleep() }
        }
        return threads.execute()
    }

    private fun sleep() {
        Thread.sleep(5000)
        print(".")
    }

    private fun List<Thread>.execute(): Duration {
        val mark = TimeSource.Monotonic.markNow()
        forEach { it.start() }
        forEach { it.join() }
        return mark.elapsedNow()
    }
}
