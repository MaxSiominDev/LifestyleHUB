package dev.maxsiomin.common.extensions

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SavedStateHandleExtensionsKtTest {

    @Test
    fun `requireArg retrieves arguments successfully`() {
        val key = "key"
        val value = "value"
        val savedStateHandle = SavedStateHandle(mapOf(key to value))
        assertThat(savedStateHandle.requireArg<String>(key)).isEqualTo(value)
    }

    @Test
    /** If somehow requireArg does not throw an exception then test fails*/
    fun `requireArg throws an exception when arg is absent`() {
        val savedStateHandle = SavedStateHandle(mapOf())
        try {
            savedStateHandle.requireArg<String>("key")
            throw RuntimeException()
        } catch (_: IllegalArgumentException) { }
    }

}