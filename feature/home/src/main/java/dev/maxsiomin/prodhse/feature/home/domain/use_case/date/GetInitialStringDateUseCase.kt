package dev.maxsiomin.prodhse.feature.home.domain.use_case.date

import dev.maxsiomin.prodhse.core.util.DateFormatter
import javax.inject.Inject

internal class GetInitialStringDateUseCase @Inject constructor(private val dateFormatter: DateFormatter) {

    operator fun invoke(): String {
        return dateFormatter.formatDate(System.currentTimeMillis())
    }

}