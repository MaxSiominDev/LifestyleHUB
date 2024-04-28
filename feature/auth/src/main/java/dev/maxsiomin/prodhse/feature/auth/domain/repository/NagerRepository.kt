package dev.maxsiomin.prodhse.feature.auth.domain.repository

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.feature.auth.domain.model.Holiday
import kotlinx.coroutines.flow.Flow

interface NagerRepository {

    suspend fun getHolidays(year: String, countryCode: String): Flow<Resource<List<Holiday>, NetworkError>>

}