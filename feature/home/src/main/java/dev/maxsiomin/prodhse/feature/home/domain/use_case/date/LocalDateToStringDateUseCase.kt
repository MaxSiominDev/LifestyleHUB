package dev.maxsiomin.prodhse.feature.home.domain.use_case.date

import dev.maxsiomin.common.util.DateConverters
import dev.maxsiomin.prodhse.core.util.DateFormatter
import java.time.LocalDate
import javax.inject.Inject

internal class LocalDateToStringDateUseCase @Inject constructor(private val dateFormatter: DateFormatter) {

    operator fun invoke(localDate: LocalDate): String {
        val epochMillis = DateConverters.localDateToEpochMillis(localDate)
        return dateFormatter.formatDate(epochMillis)
    }

}