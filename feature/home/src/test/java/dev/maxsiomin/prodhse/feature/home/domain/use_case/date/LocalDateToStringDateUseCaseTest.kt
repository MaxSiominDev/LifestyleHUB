package dev.maxsiomin.prodhse.feature.home.domain.use_case.date

import com.google.common.truth.Truth.assertThat
import dev.maxsiomin.prodhse.core.util.DateFormatter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class LocalDateToStringDateUseCaseTest {

    private lateinit var dateFormatter: DateFormatter
    private lateinit var useCase: LocalDateToStringDateUseCase

    @Before
    fun setup() {
        dateFormatter = mockk<DateFormatter>()
        useCase = LocalDateToStringDateUseCase(dateFormatter)
    }

    @Test
    fun `invoke should return formatted date string`() {
        val localDate = LocalDate.of(2024, 5, 21)
        val millis = 1716238800000L
        val formattedDate = "May 21, 2024"

        every { dateFormatter.formatDate(millis) } returns formattedDate

        val result = useCase(localDate)
        assertThat(result).isEqualTo(formattedDate)

        verify { dateFormatter.formatDate(millis) }
    }

}