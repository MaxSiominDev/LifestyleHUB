package dev.maxsiomin.prodhse.core.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DefaultDateFormatterTest {

    private val formatter = DefaultDateFormatter()

    private val may_21_2024_TimeMillis = 1716301067085

    @Test
    fun `May 21 2024 time millis returns May 21, 2024`() {
        val expected = "May 21, 2024"
        assertThat(formatter.formatDate(may_21_2024_TimeMillis)).isEqualTo(expected)
    }

}