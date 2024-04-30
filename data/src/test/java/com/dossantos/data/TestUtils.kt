package com.dossantos.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import androidx.lifecycle.LiveData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@get:Rule
val instantTaskExecutorRule = InstantTaskExecutorRule()

object LiveDataTestUtil {
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T? {
        var data: T? = null
        val latch = CountDownLatch(1)

        val observer = androidx.lifecycle.Observer<T> {
            data = it
            latch.countDown()
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        liveData.removeObserver(observer)

        return data
    }
}