package dev.maxsiomin.common.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate

class DateConvertersTest {

    @Test
    fun `epochMillisToLocalDate should convert epoch milliseconds to LocalDate`() {
        val epochMillis = 1684656000000L // May 21, 2023

        val result = DateConverters.epochMillisToLocalDate(epochMillis)

        assertThat(result).isEqualTo(LocalDate.of(2023, 5, 21))
    }

    @Test
    fun `localDateToEpochMillis should convert LocalDate to epoch milliseconds`() {
        val localDate = LocalDate.of(2023, 5, 21)

        val result = DateConverters.localDateToEpochMillis(localDate)

        assertThat(result).isEqualTo(1684616400000L) // May 21, 2023
    }

}