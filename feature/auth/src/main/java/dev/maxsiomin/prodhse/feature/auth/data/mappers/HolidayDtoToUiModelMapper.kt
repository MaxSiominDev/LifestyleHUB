package dev.maxsiomin.prodhse.feature.auth.data.mappers

import android.annotation.SuppressLint
import dev.maxsiomin.prodhse.core.util.DateFormatter
import dev.maxsiomin.prodhse.feature.auth.data.dto.nager.NagerResponseItem
import dev.maxsiomin.prodhse.feature.auth.domain.HolidayModel
import java.text.SimpleDateFormat
import java.util.TimeZone
import javax.inject.Inject

class HolidayDtoToUiModelMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) : (List<NagerResponseItem?>?) -> List<HolidayModel>? {

    @SuppressLint("SimpleDateFormat")
    override fun invoke(response: List<NagerResponseItem?>?): List<HolidayModel>? {
        return response?.mapNotNull {
            val name = it?.name ?: return@mapNotNull null
            it.date ?: return@mapNotNull null
            val dateFormat = SimpleDateFormat("yyyy-MM-dd").apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = dateFormat.parse(it.date)?.time ?: return@mapNotNull null
            HolidayModel(
                name = name,
                date = date,
                formattedDate = dateFormatter.formatDate(date),
            )
        }
    }

}