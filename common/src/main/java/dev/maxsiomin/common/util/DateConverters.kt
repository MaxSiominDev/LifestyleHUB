package dev.maxsiomin.common.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object DateConverters {

    fun epochMillisToLocalDate(epochMillis: Long): LocalDate {
        val instant = Instant.ofEpochMilli(epochMillis)
        val zoneId = ZoneId.systemDefault()
        return instant.atZone(zoneId).toLocalDate()
    }

    fun localDateToEpochMillis(localDate: LocalDate): Long {
        val zoneId: ZoneId = ZoneId.systemDefault()
        val startOfDay = localDate.atStartOfDay(zoneId)
        val epochMillis = startOfDay.toInstant().toEpochMilli()
        return epochMillis
    }

}
