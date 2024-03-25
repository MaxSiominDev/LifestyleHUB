package dev.maxsiomin.prodhse.feature.auth.data.repository

import dev.maxsiomin.prodhse.core.extensions.asResult
import dev.maxsiomin.prodhse.core.util.Resource
import dev.maxsiomin.prodhse.feature.auth.data.mappers.HolidayDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.auth.data.mappers.RandomActivityDtoToUiModelMapper
import dev.maxsiomin.prodhse.feature.auth.data.remote.bored_api.RandomActivityApi
import dev.maxsiomin.prodhse.feature.auth.data.remote.nager.NagerApi
import dev.maxsiomin.prodhse.feature.auth.domain.HolidayModel
import dev.maxsiomin.prodhse.feature.auth.domain.repository.NagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NagerRepositoryImpl @Inject constructor(
    private val api: NagerApi,
    private val mapper: HolidayDtoToUiModelMapper,
) : NagerRepository {

    override suspend fun getHolidays(
        year: String,
        countryCode: String
    ): Flow<Resource<List<HolidayModel>>> {
        return flow {
            val apiResponse = api.getHolidays(year = year, countryCode = countryCode)
            val remoteData = apiResponse.response?.let(mapper)
            if (remoteData != null) {
                emit(remoteData)
            } else {
                throw (apiResponse.error ?: Exception("Unknown error"))
            }
        }.asResult()
    }

}