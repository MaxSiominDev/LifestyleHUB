package dev.maxsiomin.prodhse.testing

import dev.maxsiomin.prodhse.core.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

object TestDispatchers : DispatcherProvider {
    private val testDispatcher = StandardTestDispatcher()
    override val main: CoroutineDispatcher = testDispatcher
    override val io: CoroutineDispatcher = testDispatcher
    override val default: CoroutineDispatcher = testDispatcher
    override val unconfined: CoroutineDispatcher = testDispatcher
}