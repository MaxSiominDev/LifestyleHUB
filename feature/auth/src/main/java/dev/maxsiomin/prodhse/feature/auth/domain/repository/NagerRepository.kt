package dev.maxsiomin.prodhse.feature.auth.domain.repository

import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.HolidayModel
import kotlinx.coroutines.flow.Flow

interface NagerRepository {

    suspend fun getHolidays(year: String, countryCode: String): Flow<Resource<List<HolidayModel>>>

}